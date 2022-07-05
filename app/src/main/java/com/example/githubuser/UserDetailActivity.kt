package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class UserDetailActivity : AppCompatActivity() {
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        user = intent.getParcelableExtra<User>(USER) as User

        setupUserDetail()
        setupToolbar()
    }

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
        tvRepository.text = StringBuilder().append(user.repository).append(" repository")
        tvFollower.text = StringBuilder().append(user.followers).append(" followers").append("-").append(user.following).append(" following")
        tvLocation.text = user.location
        Glide.with(this)
            .load(user.avatar)
            .circleCrop()
            .into(ivAvatar)
    }

    private fun setupToolbar() {
        title = R.string.title_detail_user.toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val USER = "user"
    }
}