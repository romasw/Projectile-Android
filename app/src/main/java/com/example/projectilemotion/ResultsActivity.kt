package com.example.projectilemotion;

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlin.math.*

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(ResultsActivity: Bundle?) {
        super.onCreate(ResultsActivity)
        setContentView(R.layout.activity_results)

        //言語設定
        val lang = intent.getStringExtra("LANGUAGE")
        val xmaxView = findViewById<TextView>(R.id.xmaxView)
        val ymaxView = findViewById<TextView>(R.id.ymaxView)
        val degView = findViewById<TextView>(R.id.degView)
        val veloView = findViewById<TextView>(R.id.veloView)
        val restext = findViewById<TextView>(R.id.res)

        if(lang == "ja"){
            xmaxView.text = "飛距離"
            ymaxView.text = "最高到達点"
            degView.text = "　　投射角度"
            veloView.text = "初速度　　"
            restext.text = "結果"
        }

        //オイラー法での数値計算のための関数
        fun euler(v0: Double, y0: Double, tmax: Int, g: Double, n: Int = 12): DoubleArray {
            //tの刻み幅⊿t
            val dt = tmax.toDouble() / (2.0.pow(n)+1.0)

            //速度と位置の未初期化配列を生成
            val v = DoubleArray((2.0.pow(n)+1.0).toInt())
            val y = DoubleArray((2.0.pow(n)+1.0).toInt())

            //初速度、初期位置を代入
            v[0] = v0
            y[0] = y0

            //t毎の数値解を計算
            for (i in 0 until (y.size-1)) {
                v[i+1] = v[i] + g * dt
                y[i+1] = y[i] + v[i] * dt
            }
            return y
        }

        //メインアクティビティからの値を取得
        val deg = intent.getDoubleExtra("DEGREE", 20.0)
        val v0 = intent.getDoubleExtra("VELOCITY", 45.0)

        //取得した値を初期値としてオイラー法でx座標、y座標を計算
        val x = euler(v0 * cos((deg/180.0)*PI), 0.0, 10, 0.0)
        val y = euler(v0 * sin((deg/180.0)*PI), 0.0, 10,  -9.8)

        //以下、図の描画関係
        val chart: LineChart = findViewById(R.id.line_chart);
        val value1: ArrayList<Entry> = ArrayList()
        //x軸とy軸の最高到達点を探す
        var xmax = 0.toFloat()
        var ymax = 0.toFloat()
        for ((i) in x.withIndex()) {
            //y座標が0以上なら、その時のx座標、y座標を図に追加し、最大値かどうかを調べる
            if(y[i].toFloat() >= 0.0){
                value1.add(Entry(x[i].toFloat(),y[i].toFloat()))
                if(xmax < x[i].toFloat()){
                    xmax = x[i].toFloat()
                }
                if(ymax < y[i].toFloat()){
                    ymax = y[i].toFloat()
                }
            } else {
                //y座標が負ならループを抜ける（斜方投射において、負になってから正に戻ることはないため）
                break;
            }
        }
        //x軸とy軸を同じスケールで表示。目盛りは最大値＋5mまで。
        if(xmax >= ymax){
            chart.xAxis.axisMaximum = xmax + 5
            chart.axisLeft.axisMaximum = xmax + 5
        }else{
            chart.xAxis.axisMaximum = ymax + 5
            chart.axisLeft.axisMaximum = ymax + 5
        }
        chart.axisRight.setDrawLabels(false) //右側の目盛りを無効化
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM //x軸の目盛りを図の下側に表示
        chart.description.text = "" //図の説明文を消去

        val dataSet1 = LineDataSet(value1, "Projectile Motion")
        dataSet1.color = Color.CYAN

        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(dataSet1)

        chart.data = LineData(dataSets)
        chart.invalidate() // refresh
        //図の描画ここまで

        //テキストの書き換え
        val degtext = findViewById<TextView>(R.id.degtext) //投射角度
        val velotext = findViewById<TextView>(R.id.velotext) //初速度
        val xmaxtext = findViewById<TextView>(R.id.xmaxtext) //飛距離
        val ymaxtext = findViewById<TextView>(R.id.ymaxtext) //最高高度
        val degstr = deg.toString()
        val velostr = v0.toString()
        degtext.text = "$degstr °"
        velotext.text = "$velostr m/s"
        val xmaxstr = xmax.toString()
        val ymaxstr = ymax.toString()
        xmaxtext.text = "$xmaxstr m"
        ymaxtext.text = "$ymaxstr m"

        //MainActivityに戻る（ResultsActivityの終了）
        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }
}