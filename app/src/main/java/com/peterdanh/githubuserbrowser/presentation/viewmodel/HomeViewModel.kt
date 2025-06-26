package com.peterdanh.githubuserbrowser.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterdanh.githubuserbrowser.domain.model.User
import com.peterdanh.githubuserbrowser.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
                val result = getUsersUseCase(lastSince)
                if (result.isNotEmpty()) {
                    lastSince += result.size
                    _users.value = _users.value + result
                } else {
                    reachedEnd = true
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}