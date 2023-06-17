package com.fadil.aksarakeun.ui.main.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.fadil.aksarakeun.R
import com.fadil.aksarakeun.base.BaseFragment
import com.fadil.aksarakeun.data.abstraction.Status
import com.fadil.aksarakeun.databinding.FragmentHistoryBinding
import com.fadil.aksarakeun.ui.main.MainViewModel

class HistoryFragment : BaseFragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    private val adapter by lazy { HistoryAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.rvHistory.adapter = adapter

        viewModel.getHistory()
    }

    override fun initObserve() {
        viewModel.observerHistory.observe(requireActivity()){
            when(it.status){
                Status.SUCCESS -> {
                    binding.apply {
                        rvHistory.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                    it.data?.data?.let { it1 -> adapter.setItem(it1.toMutableList()) }
                }
                Status.ERROR -> {
                    binding.apply {
                        rvHistory.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }
                Status.LOADING -> {
                    binding.apply {
                        rvHistory.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}