package com.fadil.aksarakeun.ui.main.profile

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
import com.fadil.aksarakeun.databinding.FragmentProfileBinding
import com.fadil.aksarakeun.ui.auth.AuthActivity
import com.fadil.aksarakeun.ui.main.MainViewModel

class ProfileFragment : BaseFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.main_navigation)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.getProfile()
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonLogout.setOnClickListener {
            viewModel.logout()
            AuthActivity.start(requireContext())
            requireActivity().finishAffinity()
        }
    }

    override fun initObserve() {
        viewModel.observerProfile.observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data?.data != null) {
                        binding.apply {
                            textName.text = it.data.data.name
                            textEmail.text = it.data.data.email
                        }
                    }
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    (it.data?.message ?: it.message)?.let { it2 ->
                        snackBarError(it2)
                    }
                }

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}