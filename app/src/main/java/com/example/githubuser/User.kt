package com.example.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String,
    var company: String,
    var avatar: Int
): Parcelable