package com.fadil.aksarakeun.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.fadil.aksarakeun.R
import com.fadil.aksarakeun.base.BaseFragment
import com.fadil.aksarakeun.data.abstraction.Status
import com.fadil.aksarakeun.databinding.FragmentRegisterBinding
import com.fadil.aksarakeun.ui.auth.AuthViewModel
import com.fadil.aksarakeun.utils.DataState

class RegisterFragment : BaseFragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by hiltNavGraphViewModels(R.id.auth_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.apply {
            buttonRegister.setOnClickListener {
                if (binding.inputName.editText?.text.toString()
                        .isNotEmpty() && binding.inputEmail.editText?.text.toString()
                        .isNotEmpty() && binding.inputPassword.editText?.text.toString()
                        .isNotEmpty()
                ) {
                    viewModel.register(
                        binding.inputName.editText?.text.toString(),
                        binding.inputEmail.editText?.text.toString(),
                        binding.inputPassword.editText?.text.toString()
                    )
                } else {
                    snackBarError("Forms are required!")
                }
            }
            buttonLogin.setOnClickListener {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            }
        }
    }

    override fun initObserve() {
        viewModel.observerRegister.observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    //it.data?.let { it1 -> snackBarSuccess(it1.message) }
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                    binding.apply {
                        progressBar.visibility = View.GONE
                        buttonRegister.visibility = View.VISIBLE
                    }
                }

                Status.ERROR -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        buttonRegister.visibility = View.VISIBLE
                        val errorMessage = it.data?.errorResponse
                        if (errorMessage != null) {
                            errorMessage.name?.let { errorName ->
                                if (errorName.isNotEmpty()) {
                                    binding.inputName.helperText = errorName[0]
                                }
                            }

                            errorMessage.email?.let { errorEmail ->
                                if (errorEmail.isNotEmpty()) {
                                    binding.inputEmail.helperText = errorEmail[0]
                                }
                            }

                            errorMessage.password?.let { errorPassword ->
                                if (errorPassword.isNotEmpty()) {
                                    binding.inputPassword.helperText = errorPassword[0]
                                }
                            }
                        }
                    }
                }

                Status.LOADING -> {
                    binding.apply {
                        inputName.helperText = null
                        inputEmail.helperText = null
                        inputPassword.helperText = null
                        progressBar.visibility = View.VISIBLE
                        buttonRegister.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}