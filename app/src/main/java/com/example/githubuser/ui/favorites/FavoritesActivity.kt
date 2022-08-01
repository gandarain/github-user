package com.example.githubuser.ui.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.FavoriteUserAdapter
import com.example.githubuser.databinding.ActivityFavoritesBinding
import com.example.githubuser.viewModel.ViewModelFactory

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val favoriteUserViewModel = obtainViewModel(this@FavoritesActivity)
        favoriteUserViewModel.getAllFavoriteUser().observe(this) { favoriteUserList ->
            if (favoriteUserList != null && favoriteUserList.isNotEmpty()) {
                adapter.setFavoriteUserList(favoriteUserList)
                showFavoriteUserList()
            } else {
                showEmptyFavorite()
            }
        }

        adapter = FavoriteUserAdapter()
    }

    private fun showEmptyFavorite() {
        binding.emptyUser.clEmptyUser.visibility = View.VISIBLE
        binding.rvFavoritesUsers.visibility = View.GONE
    }

    private fun showFavoriteUserList() {
        binding.rvFavoritesUsers.layoutManager = LinearLayoutManager(this@FavoritesActivity)
        binding.rvFavoritesUsers.setHasFixedSize(true)
        binding.rvFavoritesUsers.adapter = adapter
        binding.rvFavoritesUsers.visibility = View.VISIBLE
        binding.emptyUser.clEmptyUser.visibility = View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoritesViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoritesViewModel::class.java]
    }

    private fun setupToolbar() {
        title = resources.getString(R.string.title_favorite_user)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }
}