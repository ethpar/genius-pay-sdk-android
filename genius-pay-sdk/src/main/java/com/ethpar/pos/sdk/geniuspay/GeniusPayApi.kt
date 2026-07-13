package com.ethpar.pos.sdk.geniuspay

import com.ethpar.pos.sdk.geniuspay.models.AddFeesRequest
import com.ethpar.pos.sdk.geniuspay.models.AddFeesResponse
import com.ethpar.pos.sdk.geniuspay.models.CancelRequest
import com.ethpar.pos.sdk.geniuspay.models.CancelResponse
import com.ethpar.pos.sdk.geniuspay.models.ConfirmationRequest
import com.ethpar.pos.sdk.geniuspay.models.ConfirmationResponse
import com.ethpar.pos.sdk.geniuspay.models.EnrollmentRequest
import com.ethpar.pos.sdk.geniuspay.models.EnrollmentResponse
import com.ethpar.pos.sdk.geniuspay.models.MerchantAddressResponse
import com.ethpar.pos.sdk.geniuspay.models.PaymentStatusResponse
import com.ethpar.pos.sdk.geniuspay.models.PreflightRequest
import com.ethpar.pos.sdk.geniuspay.models.PreflightResponse
import com.ethpar.pos.sdk.geniuspay.models.RedeemRequest
import com.ethpar.pos.sdk.geniuspay.models.RedeemResponse
import com.ethpar.pos.sdk.geniuspay.models.SweepRequest
import com.ethpar.pos.sdk.geniuspay.models.SweepResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GeniusPayApi {
    @POST("api/v1/enrollment")
    suspend fun enroll(@Body params: EnrollmentRequest): EnrollmentResponse

    @POST("api/v1/addFees")
    suspend fun addFees(@Body params: AddFeesRequest): AddFeesResponse

    @POST("api/v1/preflight")
    suspend fun preflight(@Body params: PreflightRequest): PreflightResponse

    @POST("api/v1/confirmation")
    suspend fun confirm(@Body params: ConfirmationRequest): ConfirmationResponse

    @POST("api/v1/cancel")
    suspend fun cancel(@Body params: CancelRequest): CancelResponse

    @POST("api/v1/sweep")
    suspend fun sweep(@Body params: SweepRequest): SweepResponse

    @POST("api/v1/redeem")
    suspend fun redeem(@Body params: RedeemRequest): RedeemResponse

    @GET("api/v1/merchantAddress")
    suspend fun getMerchantAddress(@Query("merchantId") merchantId: String): MerchantAddressResponse

    @GET("api/v1/paymentStatus")
    suspend fun getPaymentStatus(
        @Query("merchantId") merchantId: String,
        @Query("txHash") txHash: String? = null
    ): PaymentStatusResponse
}
