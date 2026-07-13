# Genius Pay Android SDK (`genius-pay-sdk-android`)

An Android SDK wrapper library for the Genius Pay APIs, designed for integration with the Android Point of Sale (`AndroidPointOfSale`) application.

This SDK provides a clean, modern, and type-safe interface for managing crypto wallets, processing transactions, querying balances, and more. It is built using **Kotlin**, **Retrofit2**, **Coroutines**, and **Kotlinx Serialization**.

---

## Installation

Add the library to your Android project's `build.gradle` or `build.gradle.kts` file:

### Gradle (Kotlin DSL)
```kotlin
dependencies {
    implementation(project(":genius-pay-sdk"))
}
```

### Gradle (Groovy)
```groovy
dependencies {
    implementation project(':genius-pay-sdk')
}
```

---

## Usage Example

### 1. Initializing the SDK

Use `WalletSdk.Builder` to build and customize your SDK instance. You can pass a custom base URL and a custom `OkHttpClient` if you need custom headers, security/SSL pinning, or custom timeouts.

```kotlin
import com.ethpar.pos.sdk.WalletSdk
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

// Create a custom OkHttpClient (Optional)
val customOkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

// Build the SDK instance
val walletSdk = WalletSdk.Builder()
    .baseUrl("https://api.dev.rampatm.net/ramp/") // Set target environment URL
    .okHttpClient(customOkHttpClient)             // Optional: Pass custom OkHttpClient
    .build()
```

### 2. Calling API Methods

Once initialized, all methods defined in `WalletApi` are exposed directly on the `walletSdk` instance. Since these are standard Kotlin coroutine `suspend` functions, they must be called from within a coroutine scope.

```kotlin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.ethpar.pos.sdk.models.GenerateLoginCodeRequest

// Example coroutine scope (e.g. inside a ViewModel or Activity)
val scope = CoroutineScope(Dispatchers.Main)

scope.launch {
    try {
        // 1. Generate a login code
        val loginRequest = GenerateLoginCodeRequest(email = "user@example.com")
        walletSdk.generateLoginCode(loginRequest)
        println("Login code sent successfully!")

        // 2. Fetch the current logged-in user details
        val currentUser = walletSdk.getCurrentUser()
        println("Logged-in user: ${currentUser.name}")

        // 3. Get balances for a public address
        val balances = walletSdk.getBalances(
            address = currentUser.publicAddress,
            tokens = GetBalancesRequest(tokenAddresses = listOf("0x..."))
        )
        println("Balances: $balances")

    } catch (e: Exception) {
        // Handle network errors, serialization errors, or API-specific failures
        e.printStackTrace()
    }
}
```

---

## GeniusPayClient

`GeniusPayClient` wraps the GeniusPay POS terminal API — terminal enrollment, fee calculation, card transaction preflight/confirmation/cancellation, stablecoin sweep/redeem, and merchant wallet/payment status lookups. It follows the same builder pattern as `WalletSdk`.

### 1. Initializing the client

```kotlin
import com.ethpar.pos.sdk.geniuspay.GeniusPayClient

val geniusPayClient = GeniusPayClient.Builder()
    .baseUrl("https://api.dev.rampatm.net/ramp/") // Set target environment URL
    .build()
```

### 2. Calling API methods

```kotlin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.ethpar.pos.sdk.geniuspay.models.EnrollmentRequest
import com.ethpar.pos.sdk.geniuspay.models.AddFeesRequest
import com.ethpar.pos.sdk.geniuspay.models.PreflightRequest

val scope = CoroutineScope(Dispatchers.Main)

scope.launch {
    try {
        // 1. Enroll the terminal to obtain signing keys
        val enrollment = geniusPayClient.enroll(
            EnrollmentRequest(
                terminalSerialNumber = "NEX82-00123456",
                merchantId = "merchant_abc123",
                activationCode = "ACT-98765"
            )
        )
        println("Signing key: ${enrollment.signingKeyId}")

        // 2. Calculate fees for a sale
        val fees = geniusPayClient.addFees(AddFeesRequest(totalSale = "100.00"))
        println("Total with fees: ${fees.totalWithFees}")

        // 3. Pre-authorize the transaction
        val preflight = geniusPayClient.preflight(
            PreflightRequest(
                totalWithFees = fees.totalWithFees,
                totalSale = fees.totalSale,
                merchantJwt = "<signed-jwt>",
                terminalId = "NEX82-00123456"
            )
        )
        println("Preflight status: ${preflight.status}")

    } catch (e: Exception) {
        // Handle network errors, serialization errors, or API-specific failures
        e.printStackTrace()
    }
}
```

---

## Project Structure

- `WalletSdk`: The single, main entry point of the SDK. Built with a `Builder` class.
- `WalletApi`: The Retrofit HTTP service interface detailing all REST endpoints.
- `models/`: High-quality Kotlinx-serialization data transfer objects (DTOs) for requests and responses.
- `geniuspay/GeniusPayClient`: Entry point for the GeniusPay POS terminal API. Built with a `Builder` class.
- `geniuspay/GeniusPayApi`: The Retrofit HTTP service interface for the GeniusPay endpoints.
- `geniuspay/models/`: Kotlinx-serialization DTOs for GeniusPay requests and responses, one file per endpoint.
