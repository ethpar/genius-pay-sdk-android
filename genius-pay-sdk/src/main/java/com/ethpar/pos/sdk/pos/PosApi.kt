package com.ethpar.pos.sdk.pos

import com.ethpar.pos.sdk.pos.models.AddFeesRequest
import com.ethpar.pos.sdk.pos.models.AddFeesResponse
import com.ethpar.pos.sdk.pos.models.CancelRequest
import com.ethpar.pos.sdk.pos.models.CancelResponse
import com.ethpar.pos.sdk.pos.models.ConfirmationRequest
import com.ethpar.pos.sdk.pos.models.ConfirmationResponse
import com.ethpar.pos.sdk.pos.models.EnrollmentRequest
import com.ethpar.pos.sdk.pos.models.EnrollmentResponse
import com.ethpar.pos.sdk.pos.models.MerchantAddressResponse
import com.ethpar.pos.sdk.pos.models.PaymentStatusResponse
import com.ethpar.pos.sdk.pos.models.PreflightRequest
import com.ethpar.pos.sdk.pos.models.PreflightResponse
import com.ethpar.pos.sdk.pos.models.RedeemRequest
import com.ethpar.pos.sdk.pos.models.RedeemResponse
import com.ethpar.pos.sdk.pos.models.SweepRequest
import com.ethpar.pos.sdk.pos.models.SweepResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PosApi {
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
