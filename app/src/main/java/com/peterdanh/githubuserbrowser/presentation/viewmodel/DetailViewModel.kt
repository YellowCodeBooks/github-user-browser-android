package com.peterdanh.githubuserbrowser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterdanh.githubuserbrowser.domain.model.UserDetail
import com.peterdanh.githubuserbrowser.domain.usecase.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {
    private val _userDetail = MutableStateFlow<UserDetail?>(null)
    val userDetail: StateFlow<UserDetail?> = _userDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadUserDetail(username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _userDetail.value = getUserDetailUseCase(username)
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            } finally {
                _isLoading.value = false
            }
        }
    }
}