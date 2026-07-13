package com.ethpar.pos.sdk.geniuspay

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class GeniusPayClient private constructor(
    private val geniusPayApi: GeniusPayApi
) : GeniusPayApi by geniusPayApi {

    class Builder {
        private var baseUrl = "https://api.dev.rampatm.net/ramp/"
        private var okHttpClient: OkHttpClient? = null

        fun baseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun okHttpClient(client: OkHttpClient) = apply { this.okHttpClient = client }

        fun build(): GeniusPayClient {
            val json = Json {
                ignoreUnknownKeys = true
                prettyPrint = false
                encodeDefaults = true
            }

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

            val geniusPayApi = retrofit.create(GeniusPayApi::class.java)
            return GeniusPayClient(geniusPayApi)
        }
    }
}
