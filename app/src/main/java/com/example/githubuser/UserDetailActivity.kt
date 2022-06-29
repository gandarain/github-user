package com.example.githubuser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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
        setupToolbar()
    }

    @SuppressLint("SetTextI18n")
    private fun setupUserDetail() {
        val tvName: TextView = findViewById(R.id.tv_name)
        val tvUsername: TextView = findViewById(R.id.tv_username)
        val ivAvatar: ImageView = findViewById(R.id.iv_avatar)
        val tvCompany: TextView = findViewById(R.id.tv_company)
        val tvRepository: TextView = findViewById(R.id.tv_repository)
        val tvFollower: TextView = findViewById(R.id.tv_followers)
        val tvLocation: TextView = findViewById(R.id.tv_location)

        tvUsername.text = user.username
        tvName.text = user.name
        tvCompany.text = user.company
        tvRepository.text = "${user.repository} repository"
        tvFollower.text = "${user.followers} followers - ${user.following} following"
        tvLocation.text = user.location
        Glide.with(this)
            .load(user.avatar)
            .circleCrop()
            .into(ivAvatar)
    }

    private fun setupToolbar() {
        title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}