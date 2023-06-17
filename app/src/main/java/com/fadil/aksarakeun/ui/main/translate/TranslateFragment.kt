package com.fadil.aksarakeun.ui.main.translate

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.fadil.aksarakeun.R
import com.fadil.aksarakeun.base.BaseFragment
import com.fadil.aksarakeun.data.abstraction.Status
import com.fadil.aksarakeun.databinding.FragmentTranslateBinding
import com.fadil.aksarakeun.ui.auth.AuthActivity
import com.fadil.aksarakeun.ui.main.MainViewModel
import com.fadil.aksarakeun.utils.createCustomTempFile
import java.io.File


class TranslateFragment : BaseFragment() {
    private var _binding: FragmentTranslateBinding? = null
    private val binding get() = _binding!!

    private lateinit var photo: File

    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTranslateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        binding.apply {
            layoutHistory.setOnClickListener {
                findNavController().navigate(TranslateFragmentDirections.actionTranslateFragmentToHistoryFragment())
            }
            layoutProfile.setOnClickListener {
                findNavController().navigate(TranslateFragmentDirections.actionTranslateFragmentToProfileFragment())
            }
            cardPreview.setOnClickListener {
                takePhoto()
            }

            buttonTranslate.setOnClickListener{
                if(this@TranslateFragment::photo.isInitialized){
                    viewModel.scanImage(photo)
                } else {
                    snackBarError("Take picture first!")
                }

            }
        }

    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireActivity().application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "scan_image_provide",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            photo = File(currentPhotoPath)
            photo.let { file ->
                binding.imagePreview.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                snackBarError("Permission Denied")
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun initObserve() {
        viewModel.observerScan.observe(requireActivity()){
            when(it.status){
                Status.SUCCESS -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        buttonTranslate.visibility = View.VISIBLE
                        textResult.text = it.data?.data ?: ""
                    }
                }
                Status.ERROR -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        buttonTranslate.visibility = View.VISIBLE
                        (it.data?.message ?: it.message)?.let { it2-> snackBarError(it2) }
                    }

                    if(it.code == 401){
                        AuthActivity.start(requireContext())
                        requireActivity().finishAffinity()
                    }
                }
                Status.LOADING -> {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        buttonTranslate.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS =
            arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 1
    }
}