package com.fadil.aksarakeun.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadil.aksarakeun.data.prefrences.UserPreferences
import com.fadil.aksarakeun.models.error.LoginErrorResponse
import com.fadil.aksarakeun.models.error.RegisterErrorResponse
import com.fadil.aksarakeun.models.request.LoginRequest
import com.fadil.aksarakeun.models.request.RegisterRequest
import com.fadil.aksarakeun.models.response.BaseApiResponse
import com.fadil.aksarakeun.models.response.UserModel
import com.fadil.aksarakeun.models.response.RegisterResponse
import com.fadil.aksarakeun.repository.AuthRepository
import com.fadil.aksarakeun.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferences: UserPreferences
) : ViewModel() {
    private val _register =
        MutableLiveData<DataState<BaseApiResponse<RegisterResponse, RegisterErrorResponse>>>()
    val observerRegister: LiveData<DataState<BaseApiResponse<RegisterResponse, RegisterErrorResponse>>>
        get() = _register

    private val _login =
        MutableLiveData<DataState<BaseApiResponse<UserModel, LoginErrorResponse>>>()
    val observerLogin: LiveData<DataState<BaseApiResponse<UserModel, LoginErrorResponse>>>
        get() = _login

    fun register(name: String, email: String, password: String) {
        val registerRequest = RegisterRequest(name, email, password)
        viewModelScope.launch(Dispatchers.Main) {
            authRepository.register(registerRequest).collect {
                _register.postValue(it)
            }
        }
    }

    fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        viewModelScope.launch(Dispatchers.Main) {
            authRepository.login(loginRequest).collect {
                _login.postValue(it)
            }
        }
    }

    fun saveToken(token: String, refreshToken: String) {
        viewModelScope.launch {
            preferences.saveAccessToken(accessToken = token)
            preferences.saveRefreshToken(refreshToken)
        }
    }
}