package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra<User>(USER) as User

        setupUserDetail()
        setupToolbar()
    }

    private fun setupUserDetail() {
        val tvName: TextView = binding.cardUserInfo.tvName
        val tvUsername: TextView = binding.cardUserInfo.tvUsername
        val ivAvatar: ImageView = binding.cardUserInfo.ivAvatar
        val tvCompany: TextView = binding.cardUserDetail.tvCompany
        val tvRepository: TextView = binding.cardUserDetail.tvRepository
        val tvFollower: TextView = binding.cardUserDetail.tvFollowers
        val tvLocation: TextView = binding.cardUserDetail.tvLocation

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
        title = resources.getString(R.string.title_detail_user)
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