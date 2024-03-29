package com.example.githubuser.api

import com.example.githubuser.BuildConfig
import com.example.githubuser.model.User
import com.example.githubuser.model.UserDetailResponse
import com.example.githubuser.model.UserListResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun searchUser(
        @Query("q") username: String
    ): Call<UserListResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getFollowers(@Path("username") username: String): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getFollowing(@Path("username") username: String): Call<ArrayList<User>>
}