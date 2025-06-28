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

/**
 * ViewModel responsible for managing the state and logic of the home screen,
 * including loading and exposing a list of GitHub users.
 *
 * @property getUsersUseCase The use case for retrieving a list of users.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    /**
     * StateFlow holding the current list of users.
     */
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    /**
     * StateFlow indicating whether a loading operation is in progress.
     */
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    /**
     * StateFlow holding the current error message, if any.
     */
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var lastSince = 0
    private var reachedEnd = false

    /**
     * Loads users from the repository, handling loading state and errors.
     * Prevents duplicate loads and tracks pagination.
     */
    fun loadUsers() {
        if (_isLoading.value || reachedEnd) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                getUsersUseCase(lastSince).collectLatest { users ->
                    if (users.isNotEmpty()) {
                        _users.value = users
                        lastSince = users.size
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