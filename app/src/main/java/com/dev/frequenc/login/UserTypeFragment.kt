package com.dev.frequenc.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dev.frequenc.databinding.FragmentUserTypeBinding
import com.dev.frequenc.extensions.showAlertDialog
import com.dev.frequenc.util.Constant
import com.dev.frequenc.util.DataChangeListener


class UserTypeFragment : Fragment() {
    private lateinit var binding: FragmentUserTypeBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dataChangeListener: DataChangeListener
    private lateinit var phoneNo: String
    private lateinit var tokenMsg: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserTypeBinding.inflate(layoutInflater, container, false)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.currentFragmentTag.observe(viewLifecycleOwner) {
            it?.let {
                dataChangeListener.onDataChange(it, "move")
            }
        }
        loginViewModel.isApiCalled.observe(viewLifecycleOwner) {
            it?.let { dataChangeListener.onDataChange(it, "api") }
        }
        loginViewModel._toastMessage.observe(viewLifecycleOwner) {
            it?.let { dataChangeListener.onDataChange(it, "message") }
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

        val sharedPreferences =
            activity?.getSharedPreferences(Constant.SharedPreference, Context.MODE_PRIVATE)
        phoneNo = sharedPreferences?.getString(Constant.PhoneNo, null)!!
        tokenMsg = sharedPreferences?.getString(Constant.Authorization, null)!!
        phoneNo.let {
            binding.btnUser.setOnClickListener { showConfirmationDialog(1) }
            binding.btnAttendee.setOnClickListener { showConfirmationDialog(2) }
            binding.btnVenue.setOnClickListener { showConfirmationDialog(3) }
            binding.btnVendor.setOnClickListener { showConfirmationDialog(4) }
        }

//        binding.ser
    }

    private fun showConfirmationDialog(new_pos: Int) {
        when (new_pos) {
            1 -> {
                view?.showAlertDialog("Select User Type",
                    "Do you want to select => User : ",
                    "Yes",
                    "Edit",
                    positiveButtonClick = {
                        loginViewModel.callUpdateUserTypeApi(
                            tokenMsg,
                            phoneNo,
                            "user"
                        )
                    },
                    negativeButtonClick = {}
                )
            }

            2 -> {
                view?.showAlertDialog("Select User Type",
                    "Do you want to select => Artist : ",
                    "Yes",
                    "Edit",
                    positiveButtonClick = {
                        loginViewModel.callUpdateUserTypeApi(
                            tokenMsg,
                            phoneNo,
                            "artist"
                        )
                    },
                    negativeButtonClick = {}
                )
            }

            3 -> {
                view?.showAlertDialog("Select User Type",
                    "Do you want to select => Venue : ",
                    "Yes",
                    "Edit",
                    positiveButtonClick = {
                        loginViewModel.callUpdateUserTypeApi(
                            tokenMsg,
                            phoneNo,
                            "venue"
                        )
                    },
                    negativeButtonClick = {}
                )
            }

            4 -> {
                view?.showAlertDialog("Select User Type",
                    "Do you want to select => Vendor : ",
                    "Yes",
                    "Edit",
                    positiveButtonClick = {
                        loginViewModel.callUpdateUserTypeApi(
                            tokenMsg,
                            phoneNo,
                            "vendor"
                        )
                    },
                    negativeButtonClick = {}
                )
            }
        }
    }


}