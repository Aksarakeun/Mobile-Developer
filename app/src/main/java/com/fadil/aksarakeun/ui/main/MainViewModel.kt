package com.fadil.aksarakeun.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadil.aksarakeun.data.prefrences.UserPreferences
import com.fadil.aksarakeun.models.response.BaseApiResponse
import com.fadil.aksarakeun.models.response.HistoryResponse
import com.fadil.aksarakeun.models.response.UserModel
import com.fadil.aksarakeun.repository.MainRepository
import com.fadil.aksarakeun.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _profile =
        MutableLiveData<DataState<BaseApiResponse<UserModel, String>>>()
    val observerProfile: LiveData<DataState<BaseApiResponse<UserModel, String>>>
        get() = _profile

    private val _scan =
        MutableLiveData<DataState<BaseApiResponse<String, String>>>()
    val observerScan: LiveData<DataState<BaseApiResponse<String, String>>>
        get() = _scan

    private val _history =
        MutableLiveData<DataState<BaseApiResponse<List<HistoryResponse>, String>>>()
    val observerHistory: LiveData<DataState<BaseApiResponse<List<HistoryResponse>, String>>>
        get() = _history

    fun getProfile() {
        userPreferences.getAccessToken().let {
            viewModelScope.launch(Dispatchers.Main) {
                mainRepository.profile("Bearer $it").collect { res ->
                    _profile.postValue(res)
                }
            }
        }
    }


    fun scanImage(image: File){
        val requestImageFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = requestImageFile.let { it1 ->
            MultipartBody.Part.createFormData(
                "image",
                image.name,
                it1
            )
        }

        userPreferences.getAccessToken().let {
            viewModelScope.launch(Dispatchers.Main) {
                mainRepository.scan("Bearer $it", imageMultipart).collect { res ->
                    _scan.postValue(res)
                }
            }
        }
    }

    fun getHistory(){
        userPreferences.getAccessToken().let {
            viewModelScope.launch(Dispatchers.Main) {
                mainRepository.history("Bearer $it").collect { res ->
                    _history.postValue(res)
                }
            }
        }
    }

    fun logout() {
        userPreferences.clearData()
    }
}