package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var username: String
    private val userDetailViewModel by viewModels<UserDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        username = intent.getStringExtra(USERNAME).toString()
        userDetailViewModel.getUserDetail(username)

        userDetailViewModel.isLoading.observe(this@UserDetailActivity){
            showLoading(it)
        }

        userDetailViewModel.userDetail.observe(this@UserDetailActivity){
            showUserDetail(it)
        }

        userDetailViewModel.isError.observe(this@UserDetailActivity) {
            showError(it)
        }
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

    private fun showLoading(isLoading: Boolean){
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.userDetail.clUserDetail.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.userDetail.clUserDetail.visibility = View.VISIBLE
        }
    }

    private fun showUserDetail(user: UserDetailResponse) {
        Glide.with(this@UserDetailActivity)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.userDetail.cardUserInfo.ivAvatar)
        binding.userDetail.cardUserInfo.tvName.text = user.name
        binding.userDetail.cardUserInfo.tvUsername.text = user.login
        binding.userDetail.cardUserDetail.tvCompany.text = user.company
        binding.userDetail.cardUserDetail.tvLocation.text = user.location
        binding.userDetail.cardUserDetail.tvRepository.text = StringBuilder().append(user.publicRepos.toString())
            .append(this@UserDetailActivity.getString(R.string.repositories))
        binding.userDetail.cardUserDetail.tvFollowers.text = StringBuilder().append(user.followers.toString())
            .append(this@UserDetailActivity.getString(R.string.followers))
            .append(user.following.toString())
            .append(this@UserDetailActivity.getString(R.string.following))
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            binding.error.clError.visibility = View.VISIBLE
        } else {
            binding.error.clError.visibility = View.GONE
        }
    }

    companion object {
        const val USERNAME = "username"
    }
}