package com.ethpar.pos.sdk.pos

import com.ethpar.pos.sdk.pos.models.EnrollmentRequest
import com.ethpar.pos.sdk.pos.models.TokenResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class PosClient private constructor(
    private val posApi: PosApi,
    private val okHttpClient: OkHttpClient
) : PosApi by posApi {

    companion object {
        private val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = false
            encodeDefaults = true
        }
    }

    class Builder {
        private var baseUrl = "https://api.dev.rampatm.net/ramp/"
        private var okHttpClient: OkHttpClient? = null

        fun baseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun okHttpClient(client: OkHttpClient) = apply { this.okHttpClient = client }

        fun build(): PosClient {
            val client = okHttpClient ?: OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .callTimeout(90, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .client(client)
                .build()

            val posApi = retrofit.create(PosApi::class.java)
            return PosClient(posApi, client)
        }
    }

    suspend fun getAccessToken(params: EnrollmentRequest): TokenResponse = withContext(Dispatchers.IO) {
        val enrollment = posApi.enroll(params)
        val clientAssertion = buildClientAssertion(enrollment)

        val body = FormBody.Builder()
            .add("grant_type", "client_credentials")
            .add("client_id", enrollment.signingKeyId)
            .add("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
            .add("client_assertion", clientAssertion)
            .build()

        val request = Request.Builder()
            .url(enrollment.tokenEndpoint)
            .post(body)
            .build()

        okHttpClient.newCall(request).execute().use { response ->
            json.decodeFromString(TokenResponse.serializer(), checkNotNull(response.body).string())
        }
    }
}
