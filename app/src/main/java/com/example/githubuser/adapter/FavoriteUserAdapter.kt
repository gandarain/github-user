package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.databinding.UserDetailBinding
import com.example.githubuser.helper.NoteDiffCallback
import com.example.githubuser.ui.detail.UserDetailActivity

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserHolder>() {
    private val listNotes = ArrayList<FavoriteUser>()

    fun setFavoriteUserList(listNotes: List<FavoriteUser>) {
        val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    inner class FavoriteUserHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            with(binding) {
                Glide.with(binding.root.context)
                    .load(favoriteUser.avatarUrl)
                    .circleCrop()
                    .into(imgItemPhoto)
                tvItemName.text = favoriteUser.name
                tvItemType.text = favoriteUser.email
                itemView.setOnClickListener {
                    val intent = Intent(it.context, UserDetailActivity::class.java)
                    intent.putExtra(UserDetailActivity.USERNAME, favoriteUser.login)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}