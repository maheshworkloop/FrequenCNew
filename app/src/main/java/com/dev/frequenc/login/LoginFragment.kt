package com.dev.frequenc.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dev.frequenc.R
import com.dev.frequenc.databinding.FragmentLoginBinding
import com.dev.frequenc.extensions.textChanged
import com.dev.frequenc.util.Constant
import com.dev.frequenc.util.DataChangeListener


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dataChangeListener: DataChangeListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.currentFragmentTag.observe(viewLifecycleOwner) {
            if (it != null){ dataChangeListener.onDataChange(it, "move") }
        }
        loginViewModel.isApiCalled.observe(viewLifecycleOwner) {
            if (it!= null) { dataChangeListener.onDataChange(it, "api") }
        }
        loginViewModel._toastMessage.observe(viewLifecycleOwner) {
            if (it!= null) { dataChangeListener.onDataChange(it, "message") }
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DataChangeListener) {
            dataChangeListener = context
        } else {
            throw IllegalArgumentException("Activity must implement DataChangeListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etPhoneNumber.textChanged(liveData = loginViewModel._mob_no)
        requireNotNull(binding).btnSubmit.setOnClickListener {

            val phone_no = loginViewModel._mob_no.value
            if (!phone_no.isNullOrBlank() && !phone_no.isNullOrEmpty() && phone_no.length == 10) {

                val sharedPreferencesEditor =
                    activity?.getSharedPreferences(Constant.SharedPreference, Context.MODE_PRIVATE)?.edit()
                sharedPreferencesEditor?.putString(Constant.PhoneNo, binding.btnCcp.selectedCountryCodeWithPlus + phone_no)
                sharedPreferencesEditor?.apply()
                loginViewModel.callRegisterApi( binding.btnCcp.selectedCountryCodeWithPlus + phone_no)
                return@setOnClickListener
            }
            binding.etPhoneNumber.setError(getString(R.string.mob_no_ph))
            binding.etPhoneNumber.requestFocus()
        }

    }

}