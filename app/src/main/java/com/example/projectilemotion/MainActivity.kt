package com.example.projectilemotion

// View Binding
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val deg = findViewById<EditText>(R.id.deg)
        val velocity = findViewById<EditText>(R.id.velocity)
        val next = findViewById<Button>(R.id.button)
        val lang = findViewById<ToggleButton>(R.id.lang)
        var language = "en"
        val desc = findViewById<TextView>(R.id.desc)
        if(lang.text.toString() == "EN"){
            language = "en"
        }else{
            language = "ja"
            desc.text = "投射角度と初速度を入力！"
            deg.hint = "投射角度"
            velocity.hint = "初速度"
        }

        val error1 = findViewById<TextView>(R.id.textView4)
        error1.visibility= View.INVISIBLE
        val error2 = findViewById<TextView>(R.id.textView7)
        error2.visibility= View.INVISIBLE
        var err1 =0
        var err2 =0

        lang.setOnClickListener {
            if(lang.text.toString() == "EN"){
                language = "en"
                desc.text = "Set the parameters!"
                deg.hint = "Projection Angle"
                velocity.hint = "Initial Velocity"
                error1.text = "Angle must be between 0 and 90"
                error2.text = "Velocity must be between 0 and 100"
            }else{
                language = "ja"
                desc.text = "投射角度と初速度を入力！"
                deg.hint = "投射角度"
                velocity.hint = "初速度"
                error1.text = "角度は０°から９０°まで！"
                error2.text = "速度は０m/sから１００m/sまで！"
            }
        }
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
                nextIntent.putExtra("LANGUAGE", language)
                //次のActivity実行
                startActivity(nextIntent)
            }
         }
    }
}