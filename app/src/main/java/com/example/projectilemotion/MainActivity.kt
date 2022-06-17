package com.example.projectilemotion

// View Binding
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val deg = findViewById<EditText>(R.id.deg)
        val velocity = findViewById<EditText>(R.id.velocity)
        val next = findViewById<Button>(R.id.button)

         next.setOnClickListener {
             //Intentオブジェクト生成、遷移画面定義
             if(deg.text.toString().toInt()>=0 && deg.text.toString().toInt()<=90){

             }
             else{

             }
             val nextIntent = Intent(this, ResultsActivity::class.java)
             //Intentオブジェクトに入力内容をプットする
             nextIntent.putExtra("DEGREE", deg.text.toString().toDouble())
             nextIntent.putExtra("VELOCITY", velocity.text.toString().toDouble())
             //次のActivity実行
             startActivity(nextIntent)
         }
    }
}