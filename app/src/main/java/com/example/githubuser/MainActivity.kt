package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val img: ImageView = findViewById(R.id.img_item_photo)
        Glide.with(this)
            .load(R.drawable.user1)
            .circleCrop()
            .into(img)
    }
}