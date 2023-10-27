package com.dev.frequenc.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dev.frequenc.databinding.FragmentVerifyOtpBinding
import com.dev.frequenc.util.Constant
import com.dev.frequenc.util.DataChangeListener

class VerifyOtpFragment : Fragment() {
    private lateinit var verifyOtpBinding: FragmentVerifyOtpBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dataChangeListener: DataChangeListener
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = activity?.getSharedPreferences(Constant.SharedPreference, Context.MODE_PRIVATE)!!
        verifyOtpBinding = FragmentVerifyOtpBinding.inflate(layoutInflater, container, false)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.currentFragmentTag.observe(viewLifecycleOwner) {
            it.let {
                if (!loginViewModel._receivedToken.isNullOrEmpty() && loginViewModel._receivedToken != "--1") {
                    sharedPreferences.edit().putBoolean(Constant.isUserTypeRegistered, loginViewModel.isUserTypeRegistered).apply()
                    sharedPreferences.edit().putString(Constant.Authorization, loginViewModel._receivedToken).apply()
                    dataChangeListener.onDataChange(it, "move")
                }
            }
        }
        loginViewModel.isApiCalled.observe(viewLifecycleOwner) {
            it.let { dataChangeListener.onDataChange(it, "api") }
        }
        loginViewModel._toastMessage.observe(viewLifecycleOwner) {
            it.let { dataChangeListener.onDataChange(it, "message") }
        }
        return verifyOtpBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataChangeListener) {
            dataChangeListener = context
        } else {
            throw IllegalArgumentException("Activity must implement  DataChangeListener")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        verifyOtpBinding.otp1.addTextChangedListener(onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
//            run {
//                verifyOtpBinding.otp2.text = null
//                verifyOtpBinding.otp3.text = null
//                verifyOtpBinding.otp4.text = null
//                verifyOtpBinding.otp5.text = null
//                verifyOtpBinding.otp6.text = null
//                if (requireNotNull(charSequence).length == 1) {
//                    loginViewModel._otp.value = verifyOtpBinding.otp1.text.toString()
//                    verifyOtpBinding.otp2.requestFocus()
//                }
//            }
//        },
//            beforeTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int -> },
//            afterTextChanged = {})
//        verifyOtpBinding.otp2.addTextChangedListener(onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
//            run {
//                verifyOtpBinding.otp3.text = null
//                verifyOtpBinding.otp4.text = null
//                verifyOtpBinding.otp5.text = null
//                verifyOtpBinding.otp6.text = null
//                if (requireNotNull(charSequence).length == 1) {
//                    loginViewModel._otp.value = verifyOtpBinding.otp1.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp2.text.toString()
//                    verifyOtpBinding.otp3.requestFocus()
//                }
//                else { verifyOtpBinding.otp1.requestFocus() }
//            }
//        },
//            beforeTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int -> },
//            afterTextChanged = {})
//        verifyOtpBinding.otp3.addTextChangedListener(onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
//            run {
//                verifyOtpBinding.otp4.text = null
//                verifyOtpBinding.otp5.text = null
//                verifyOtpBinding.otp6.text = null
//                if (requireNotNull(charSequence).length == 1) {
//                    loginViewModel._otp.value = verifyOtpBinding.otp1.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp2.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp3.text.toString()
//                    verifyOtpBinding.otp4.requestFocus()
//                }
//                else { verifyOtpBinding.otp2.requestFocus() }
//            }
//        },
//            beforeTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int -> },
//            afterTextChanged = {})
//        verifyOtpBinding.otp4.addTextChangedListener(onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
//            run {
//                verifyOtpBinding.otp5.text = null
//                verifyOtpBinding.otp6.text = null
//                if (requireNotNull(charSequence).length == 1) {
//                    loginViewModel._otp.value = verifyOtpBinding.otp1.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp2.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp3.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp4.text.toString()
//                    verifyOtpBinding.otp5.requestFocus()
//                }
//                else { verifyOtpBinding.otp3.requestFocus() }
//            }
//        },
//            beforeTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int -> },
//            afterTextChanged = {})
//        verifyOtpBinding.otp5.addTextChangedListener(onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
//            run {
//                verifyOtpBinding.otp6.text = null
//                if (requireNotNull(charSequence).length == 1) {
//                    loginViewModel._otp.value = verifyOtpBinding.otp1.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp2.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp3.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp4.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp5.text.toString()
//                    verifyOtpBinding.otp6.requestFocus()
//                }
//                else { verifyOtpBinding.otp4.requestFocus() }
//            }
//        },
//            beforeTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int -> },
//            afterTextChanged = {})
//        verifyOtpBinding.otp6.addTextChangedListener(onTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
//            run {
//                verifyOtpBinding.otp6.text = null
//                if (requireNotNull(charSequence).length == 1) {
//                    loginViewModel._otp.value = verifyOtpBinding.otp1.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp2.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp3.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp4.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp5.text.toString()
//                    loginViewModel._otp.value += verifyOtpBinding.otp6.text.toString()
//                    verifyOtpBinding.btnSubmit.requestFocus()
//                }
//                else { verifyOtpBinding.otp5.requestFocus() }
//            }
//        },
//            beforeTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int -> },
//            afterTextChanged = {})

        verifyOtpBinding.btnSubmit.setOnClickListener {
            val otps = verifyOtpBinding.otpLays.value
            if (otps.isEmpty() || otps.length != 6) {
                verifyOtpBinding.otpLays.requestFocus()
            } else {
                loginViewModel.callVerifyOtpApi(
                    sharedPreferences?.getString(
                        Constant.PhoneNo,
                        null
                    ), otps
                )
            }
        }

        verifyOtpBinding.btnBack.setOnClickListener {
            dataChangeListener.onDataChange("0", "move")
        }

    }
}