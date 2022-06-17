package com.example.projectilemotion

// View Binding
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val deg = findViewById<EditText>(R.id.deg)
        val velocity = findViewById<EditText>(R.id.velocity)
        val next = findViewById<Button>(R.id.button)

        val error1 = findViewById<TextView>(R.id.textView4)
        error1.visibility= View.INVISIBLE
        val error2 = findViewById<TextView>(R.id.textView7)
        error2.visibility= View.INVISIBLE
        var err1 =0
        var err2 =0
        next.setOnClickListener {
            //Intentオブジェクト生成、遷移画面定義
            if(deg.text.toString().toInt()<0 || deg.text.toString().toInt()>90){
                err1=1
                error1.visibility= View.VISIBLE
            }else{
                err1=0
            }


            if(velocity.text.toString().toInt()<0 || velocity.text.toString().toInt()>100 ){
                err2=1
                error2.visibility= View.VISIBLE
            }else{
                err2=0
            }

            if(err1==0 && err2==0) {
                error1.visibility= View.INVISIBLE
                error2.visibility= View.INVISIBLE
                val nextIntent = Intent(this, ResultsActivity::class.java)
                //Intentオブジェクトに入力内容をプットする
                nextIntent.putExtra("DEGREE", deg.text.toString().toDouble())
                nextIntent.putExtra("VELOCITY", velocity.text.toString().toDouble())
                nextIntent.putExtra("LANGUAGE", "ja")
                //次のActivity実行
                startActivity(nextIntent)
            }
         }
    }
}