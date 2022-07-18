package com.example.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    init {
        searchUsers()
    }

    fun searchUsers(username: String = EMPTY_USERNAME) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(username.ifEmpty { EMPTY_USERNAME })
        client.enqueue(object : Callback<UserListResponse> {
            override fun onResponse(
                call: Call<UserListResponse>,
                response: Response<UserListResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isError.value = false
                    _users.value = response.body()?.items
                } else {
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }

    companion object {
        private const val EMPTY_USERNAME = "\"\""
    }
}