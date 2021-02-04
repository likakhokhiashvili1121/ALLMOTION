package com.example.allmotion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainPageActivity : AppCompatActivity() {

    private lateinit var profileButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        supportActionBar?.hide()

        profileButton = findViewById(R.id.profileButton)
        profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

    }
}