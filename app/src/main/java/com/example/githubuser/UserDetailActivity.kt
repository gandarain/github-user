package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class UserDetailActivity : AppCompatActivity() {
    companion object {
        const val USER = "user"
    }
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        user = intent.getParcelableExtra<User>(USER) as User

        setupUserDetail()
    }

    private fun setupUserDetail() {
        val tvName: TextView = findViewById(R.id.tv_name)
        val tvUsername: TextView = findViewById(R.id.tv_username)
        val ivAvatar: ImageView = findViewById(R.id.iv_avatar)

        tvUsername.text = user.username
        tvName.text = user.name
        Glide.with(this)
            .load(user.avatar)
            .circleCrop()
            .into(ivAvatar)
    }
}