package com.peterdanh.githubuserbrowser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var lastSince = 0
    private var reachedEnd = false

    fun loadUsers() {
        if (_isLoading.value || reachedEnd) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                getUsersUseCase(lastSince).collectLatest { result ->
                    if (result.users.isNotEmpty()) {
                        if (result.apiUserCount != 0) {
                            lastSince += result.apiUserCount
                        }
                        _users.value = result.users
                    } else {
                        reachedEnd = true
                    }
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                _isLoading.value = false
                _users.value = emptyList()
            }
        }
    }
}