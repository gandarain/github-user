package com.example.githubuser.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.example.githubuser.model.UserDetailResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

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

        tabLayoutHandler()
    }

    private fun tabLayoutHandler() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this@UserDetailActivity)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_user_detail, menu)

        return true
    }

    private fun setupToolbar() {
        title = resources.getString(R.string.title_detail_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareButton -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Github User : \n${userDetailViewModel.userDetail.value?.htmlUrl} ")
                    type = "text/html"
                }
                val shareIntent = Intent.createChooser(sendIntent, "Github User")
                startActivity(shareIntent)
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return true
        }
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.userDetail.clUserDetail.visibility = View.GONE
            binding.tabs.visibility = View.GONE
            binding.viewPager.visibility = View.GONE
            binding.viewDivider.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.userDetail.clUserDetail.visibility = View.VISIBLE
            binding.tabs.visibility = View.VISIBLE
            binding.viewPager.visibility = View.VISIBLE
            binding.viewDivider.visibility = View.VISIBLE
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
            binding.userDetail.clUserDetail.visibility = View.GONE
            binding.tabs.visibility = View.GONE
            binding.viewPager.visibility = View.GONE
            binding.viewDivider.visibility = View.GONE
        } else {
            binding.error.clError.visibility = View.GONE
            binding.userDetail.clUserDetail.visibility = View.VISIBLE
            binding.tabs.visibility = View.VISIBLE
            binding.viewPager.visibility = View.VISIBLE
            binding.viewDivider.visibility = View.VISIBLE
        }
    }

    companion object {
        const val USERNAME = "username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}