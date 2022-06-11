package com.example.projectilemotion

// View Binding
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val num = findViewById<EditText>(R.id.number)        //画面のテキストヴュー
        val next = findViewById<Button>(R.id.button)

         next.setOnClickListener {
             //Intentオブジェクト生成、遷移画面定義
             val nextIntent = Intent(this, ResultsActivity::class.java)
             //Intentオブジェクトにテキストの内容をプットする
             nextIntent.putExtra("DEGREE", num.text.toString())
             //次のActivity実行
             startActivity(nextIntent)
         }
    }
}