package com.ethpar.pos.sdk

import com.ethpar.pos.sdk.models.Balance
import com.ethpar.pos.sdk.models.ConfirmLoginCodeRequest
import com.ethpar.pos.sdk.models.ConfirmLoginCodeResponse
import com.ethpar.pos.sdk.models.CreateCCWallet
import com.ethpar.pos.sdk.models.CreateMultisigTransactionRequest
import com.ethpar.pos.sdk.models.CreateMultisigWalletRequest
import com.ethpar.pos.sdk.models.CreditCardMultisigWallet
import com.ethpar.pos.sdk.models.FeatureFlags
import com.ethpar.pos.sdk.models.GeneralMultisigWallet
import com.ethpar.pos.sdk.models.GenerateLoginCodeRequest
import com.ethpar.pos.sdk.models.GetBalancesRequest
import com.ethpar.pos.sdk.models.Transaction
import com.ethpar.pos.sdk.models.PosTransaction
import com.ethpar.pos.sdk.models.SetUserPublicAddressRequest
import com.ethpar.pos.sdk.models.SignatureData
import com.ethpar.pos.sdk.models.TokenInfo
import com.ethpar.pos.sdk.models.User
import kotlinx.serialization.json.JsonObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WalletApi {
    @POST("wallet/login/code")
    suspend fun generateLoginCode(@Body params: GenerateLoginCodeRequest)

    @POST("wallet/login/confirm")
    suspend fun confirmLoginCode(@Body params: ConfirmLoginCodeRequest): ConfirmLoginCodeResponse

    @GET("wallet/users/me")
    suspend fun getCurrentUser(): User

    @POST("wallet/blockchain/{address}/get-balance")
    suspend fun getBalances(
        @Path("address") address: String,
        @Body tokens: GetBalancesRequest
    ): List<Balance>

    @POST("wallet/users/set-address")
    suspend fun setUserPublicAddress(@Body params: SetUserPublicAddressRequest)

    @GET("wallet/blockchain/tokens")
    suspend fun getDefaultTokens(): List<TokenInfo>

    @POST("wallet/wallets")
    suspend fun createMultisigWallet(@Body params: CreateMultisigWalletRequest): GeneralMultisigWallet

    @POST("wallet/wallets/{walletId}/accept")
    suspend fun acceptMultisigWallet(@Path("walletId") walletId: String)

    @GET("wallet/wallets")
    suspend fun getMultisigWallets(): List<GeneralMultisigWallet>

    @POST("wallet/tx")
    suspend fun createMultisigTransaction(@Body params: CreateMultisigTransactionRequest): Transaction

    @GET("wallet/wallets/{walletId}/tx")
    suspend fun getMultisigWalletTransactions(@Path("walletId") walletId: String): List<Transaction>

    @GET("wallet/tx/{txId}")
    suspend fun getMultisigWalletTransaction(@Path("txId") txId: String): Transaction

    @POST("wallet/tx/{txId}/signature")
    suspend fun addMultisigTxSignature(
        @Path("txId") txId: String,
        @Body data: SignatureData
    ): Transaction

    @POST("wallet/tx/{txId}/execute")
    suspend fun executeTransaction(@Path("txId") txId: String): Transaction

    @GET("wallet/ccwallet")
    suspend fun getCCWallet(): CreditCardMultisigWallet?

    @GET("wallet/ccwallet/{walletId}/tx")
    suspend fun getCCWalletTransactions(@Path("walletId") walletId: String): List<Transaction>

    @POST("wallet/ccwallet")
    suspend fun createCCWallet(@Body params: CreateCCWallet): CreditCardMultisigWallet

    @POST("wallet/tx/pos")
    suspend fun executePosTransaction(
        @Body transaction: PosTransaction
    ): JsonObject

    @GET("wallet/blockchain/{tokenAddress}/token-info")
    suspend fun getTokenInfo(@Path("tokenAddress") tokenAddress: String): TokenInfo?

    @GET("wallet/feature-flags")
    suspend fun getFeatureFlags(): FeatureFlags
}
