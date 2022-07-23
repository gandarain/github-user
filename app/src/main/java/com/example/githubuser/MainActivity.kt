package com.example.githubuser

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.users.observe(this@MainActivity) {
            showListUser(it)
        }

        mainViewModel.isLoading.observe(this@MainActivity) {
            showLoading(it)
        }

        mainViewModel.isError.observe(this@MainActivity) {
            if (it == true) {
                showError(it)
            }
        }

        searchInputHandler()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    private fun showListUser(user: List<User>) {
        val rvUser: RecyclerView = binding.rvUsers
        val isEmptyUser = user.isEmpty()

        handlingEmptyUser(isEmptyUser)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvUser.layoutManager = GridLayoutManager(this@MainActivity, 2)
        } else {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
        }
        val listHeroAdapter = ListUserAdapter(user)
        rvUser.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                if (binding.progressBar.visibility == View.GONE) {
                    showSelectedUser(data)
                }
            }
        })
    }

    private fun handlingEmptyUser(isEmptyUser: Boolean) {
        if (isEmptyUser) {
            binding.emptyUser.clEmptyUser.visibility = View.VISIBLE
        } else {
            binding.emptyUser.clEmptyUser.visibility = View.GONE
        }
    }

    private fun showSelectedUser(user: User) {
        val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.USERNAME, user.login)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun searchInputHandler() {
        binding.searchInput.edReview.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val username = binding.searchInput.edReview.text.toString()
                mainViewModel.searchUsers(username)
                binding.searchInput.edReview.clearFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
            false
        }
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            binding.error.clError.visibility = View.VISIBLE
        } else {
            binding.error.clError.visibility = View.GONE
        }
    }
}