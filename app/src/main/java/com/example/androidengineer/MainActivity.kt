package com.example.androidengineer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createButton: RelativeLayout = findViewById(R.id.btn_container)

        createButton.setOnClickListener{
            val intent = Intent(it.context, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }
}