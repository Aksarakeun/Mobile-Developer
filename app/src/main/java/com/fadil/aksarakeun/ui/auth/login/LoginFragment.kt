package com.fadil.aksarakeun.ui.auth.login

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
import com.fadil.aksarakeun.databinding.FragmentLoginBinding
import com.fadil.aksarakeun.ui.auth.AuthViewModel
import com.fadil.aksarakeun.ui.auth.register.RegisterFragmentDirections
import com.fadil.aksarakeun.ui.main.MainActivity

class LoginFragment : BaseFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by hiltNavGraphViewModels(R.id.auth_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.buttonLogin.setOnClickListener {
            if (binding.inputEmail.editText?.text.toString()
                    .isNotEmpty() && binding.inputPassword.editText?.text.toString()
                    .isNotEmpty()
            ) {
                viewModel.login(
                    binding.inputEmail.editText?.text.toString(),
                    binding.inputPassword.editText?.text.toString()
                )
            } else {
                snackBarError("Forms are required!")
            }
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    override fun initObserve() {
        viewModel.observerLogin.observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data?.data != null) {
                        viewModel.saveToken(it.data.data.token, it.data.data.refreshToken)
                        binding.apply {
                            progressBar.visibility = View.GONE
                            buttonLogin.visibility = View.VISIBLE
                        }
                        MainActivity.start(requireContext())
                        requireActivity().finishAffinity()
                    }
                }

                Status.ERROR -> {
                    binding.apply {
                        (it.data?.message ?: it.message)?.let { it2 -> snackBarError(it2) }

                        progressBar.visibility = View.GONE
                        buttonLogin.visibility = View.VISIBLE
                        val errorMessage = it.data?.errorResponse
                        if (errorMessage != null) {
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
                        inputEmail.helperText = null
                        inputPassword.helperText = null
                        progressBar.visibility = View.VISIBLE
                        buttonLogin.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}