package com.dev.frequenc.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.frequenc.data.RegisterUserResponse
import com.dev.frequenc.data.confirmuserotp.ConfirmUserOtpResponse
import com.dev.frequenc.data.confirmuserotp.UpdateUserTypeResponse
import com.dev.frequenc.util.Constant
import com.events.frequency.ui.SplashScreen.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    var _mob_no = MutableLiveData<String>(null)
    var _receivedToken = "--1"
    var isUserTypeRegistered = false
//    private val mobile_no: LiveData<String> get() =  _mob_no
//    private val otp : MutableLiveData<String> get() = _otp

    val _toastMessage = MutableLiveData<String>("")

    private val _currentFragmentTag = MutableLiveData<String>().apply { value = "0" }
    val currentFragmentTag: LiveData<String>
        get() = _currentFragmentTag
    fun setCurrentFragmentTag (currentFragment : String) {
        _currentFragmentTag.value = currentFragment
    }
    private val __isApiCalled = MutableLiveData<Boolean>().apply { value = false }
    val isApiCalled: LiveData<Boolean>
        get() = __isApiCalled
//
//    var isRegisterApiCalled = MutableLiveData<Boolean>(false)
//    var isVerifyOtpApiHit = MutableLiveData<Boolean>(false)
//    var isUserTypeApiHit = MutableLiveData<Boolean>(false)
//
//    fun login() {
//        viewModelScope.launch {
//            moveToHome()
//        }
//    }
//
//    private suspend fun moveToHome() {
//        if (_loginResult.value == true) {
//
//        }
//    }

    fun callRegisterApi(phone_no: String) {
        __isApiCalled.value = true
        ApiClient.getInstance()!!.register(registerUserReq(phone_no))!!
            .enqueue(object : Callback<RegisterUserResponse> {
                override fun onResponse(
                    call: Call<RegisterUserResponse>,
                    response: Response<RegisterUserResponse>
                ) {
                    __isApiCalled.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _toastMessage.value = response.body()?.message.toString()
                        moveToOtpVerification()
                    } else {
                        if (response.body() != null) Log.d(
                            Constant.Error,
                            "onResponse:callRegisterApi() " + response.body()
                        )
                        _toastMessage.value = response?.errorBody()?.string().toString()
                    }
                }

                override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                    _toastMessage.value = t.localizedMessage
                    __isApiCalled.value = false
                    Log.e(Constant.Error, "onFailure: ", t)
                }

            })
    }

    data class registerUserReq(var phone_no: String)

    fun moveToOtpVerification() {
        _currentFragmentTag.value = "1"
    }

    fun moveToLogin() {
        _currentFragmentTag.value = "0"
    }

    fun moveToUserType() {
        _currentFragmentTag.value = "2"
    }

    fun moveToHome() {
        _currentFragmentTag.value = "-1"
    }

    fun callVerifyOtpApi(phone_no: String?, otp: String) {
        __isApiCalled.value = true
        otp.let {
            phone_no?.let { it1 ->
                ApiClient.getInstance()!!
                    .confirmUserOtp( VerifyOtpReq(phone_no = phone_no, otp = otp.toInt()))!!
                    .enqueue(object : Callback<ConfirmUserOtpResponse> {
                        override fun onResponse(
                            call: Call<ConfirmUserOtpResponse>,
                            response: Response<ConfirmUserOtpResponse>
                        ) =
                            run {
                                __isApiCalled.value = false
                                if (response.isSuccessful) {
                                    _receivedToken = response.headers().get(Constant.Authorization).toString()
                                    if (response.body()!!.data.user.user_type.isNullOrEmpty()) {
                                        isUserTypeRegistered = false
                                        moveToUserType()
                                    } else {
                                        isUserTypeRegistered = true
                                        moveToHome()
                                    }
                                } else {
                                    Log.d(
                                        Constant.Error,
                                        "onResponse:callVerifyOtpApi() code:${response.code()} &&  message= ${
                                            response.errorBody()?.byteStream()?.read()
                                        }"
                                    )
                                    _toastMessage.value = response.errorBody()?.string().toString()
                                }
                                if (_toastMessage.value == null) {
                                    _toastMessage.value = response.message().toString()
                                }
                            }

                        override fun onFailure(call: Call<ConfirmUserOtpResponse>, t: Throwable) {
                            _toastMessage.value = t.localizedMessage
                            __isApiCalled.value = false
                            Log.e(Constant.Error, "onFailure: ", t)
                        }
                    })
            }
        }
    }

    data class VerifyOtpReq(val phone_no: String, val otp: Int)

    fun callUpdateUserTypeApi(tokens:  String,phone_no: String, userTypeKey: String) {
        __isApiCalled.value = true
        tokens.let {
        phone_no.let {
            userTypeKey.let {
                ApiClient.getInstance()!!
                    .updateUserType(tokens,UpdateUserReq(phone_no = phone_no, user_type = userTypeKey))!!
                    .enqueue(object : Callback<UpdateUserTypeResponse> {
                        override fun onResponse(
                            call: Call<UpdateUserTypeResponse>,
                            response: Response<UpdateUserTypeResponse>
                        ): Unit = run {
                            __isApiCalled.value = false
                            if (response.isSuccessful) {
                                isUserTypeRegistered = true
        //                    if (!response.body()!!.data.isUserType.toString().isNullOrEmpty()) {
                                moveToHome()
        //                    }
                            } else {
                                Log.d(
                                    Constant.Error,
                                    "onResponse:callUpdateUserTypeApi() " + response.body()
                                )
                                _toastMessage.value = response?.errorBody()?.string().toString()
                            }
                        }

                        override fun onFailure(call: Call<UpdateUserTypeResponse>, t: Throwable) {
                            __isApiCalled.value = false
                            _toastMessage.value = t.localizedMessage
                            Log.e(Constant.Error, "onFailure:callUpdateUserTypeApi ", t)
                        }
                    })
            }
        }
    }
    }
}

data class UpdateUserReq( val phone_no: String, val user_type: String)
class LoginViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")

            return LoginViewModel() as T

        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }

}

