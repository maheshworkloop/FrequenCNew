package com.events.frequency.ui.SplashScreen.utils

import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.lifecycle.MutableLiveData
import com.dev.frequenc.data.RegisterUserResponse
import com.dev.frequenc.data.confirmuserotp.ConfirmUserOtpResponse
import com.dev.frequenc.data.confirmuserotp.UpdateUserTypeResponse
import com.dev.frequenc.login.LoginViewModel
import com.dev.frequenc.login.UpdateUserReq
import com.dev.frequenc.util.Constant
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

//
//    @GET(KeysConstant.BROWSE_BY_CAT)
//    fun browseByCat(): Call<List<BrowseByCatResponse?>>?
//
//    @GET(KeysConstant.TRENDING_EVENTS)
//    fun trendingEvents(): Call<List<TrendingEventsResponse>>?
//
//    @GET(KeysConstant.TRENDING_ARTIST)
//    fun trendingArtist(): Call<List<TrendingArtistResponse>>?
//
//    @GET(KeysConstant.ALL_DATA)
//    fun allData(): Call<List<AllDataResponse>>?

    @POST(KeysConstant.Register_User)
    fun register(@Body phone_no: LoginViewModel.registerUserReq ): Call<RegisterUserResponse>?

    @POST(KeysConstant.ConfirmUserOtp)
    fun confirmUserOtp(@Body verifyOtpReq: LoginViewModel.VerifyOtpReq) : Call<ConfirmUserOtpResponse>?

    @POST(KeysConstant.UpdateUserType)

    fun updateUserType(@Header("Authorization") tokenMsg: String, @Body updateUserReq : UpdateUserReq): Call<UpdateUserTypeResponse>?
}