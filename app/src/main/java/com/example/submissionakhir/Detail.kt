package com.example.submissionakhir

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.submissionakhir.databinding.ActivityDetailBinding


class Detail : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_STRAWHAT = "extra_strawhat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val strawhat = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_STRAWHAT, Strawhat::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Strawhat>(EXTRA_STRAWHAT)
        }

        if (strawhat != null) {
            setTitle(strawhat.name)
            binding.tvDetailName.text = strawhat.name
            binding.tvDetailPosition.text = strawhat.position
            binding.tvDetailDescription.text = strawhat.description
            binding.tvDetailMoreDescription.text = strawhat.description
            binding.tvDetailBounty.text = strawhat.bounty
            Glide.with(this)
                .load(strawhat.photo) // URL Gambar
                .into(binding.imgDetailPhoto) // imageView mana yang akan diterapkan
        }

        binding.buttonShare.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.setType("text/plain")

            val shareBody = "Do you know that?\n${strawhat?.description}"
            val shareSubject = "Cool ${strawhat?.name} Facts"

            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)

            startActivity(Intent.createChooser(sharingIntent, "Share using"))
        }
    }
}