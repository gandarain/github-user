package com.example.githubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun searchUser(
        @Query("q") username: String
    ): Call<UserListResponse>
}