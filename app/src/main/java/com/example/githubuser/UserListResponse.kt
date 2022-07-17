package com.example.githubuser

import com.google.gson.annotations.SerializedName

data class UserListResponse(
	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: ArrayList<User>
)
