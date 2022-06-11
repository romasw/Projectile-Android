package com.example.projectilemotion;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(ResultsActivity: Bundle?) {
        super.onCreate(ResultsActivity)
        setContentView(R.layout.activity_intent_test)
        val test = findViewById<TextView>(R.id.test)
        val fromMain = intent.getStringExtra("DEGREE")
        test.text = fromMain

        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }
}