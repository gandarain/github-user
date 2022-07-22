package com.example.githubuser

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private lateinit var username: String
    private lateinit var binding: FragmentFollowerBinding
    private val followerViewModel by viewModels<FollowerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = activity?.intent?.getStringExtra(UserDetailActivity.USERNAME).toString()

        followerViewModel.getFollowers(username)

        followerViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }

        followerViewModel.followers.observe(requireActivity()){
            showListFollower(it)
        }
    }

    private fun showListFollower(user: List<User>) {
        Log.e("follower ", user.toString())
        val rvUser: RecyclerView = binding.rvFollowers
        rvUser.layoutManager = LinearLayoutManager(requireActivity())
        val listHeroAdapter = ListUserAdapter(user)
        rvUser.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        val intent = Intent(requireActivity(), UserDetailActivity::class.java)
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
}