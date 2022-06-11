package com.example.projectilemotion;

import android.content.Intent
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
        setContentView(R.layout.activity_intent_test)

        //数値計算関数
        fun euler(v0: Double, y0: Double, tmax: Int, g: Double, n: Int = 12): DoubleArray {
            //tの刻み幅⊿t
            val dt = tmax.toDouble() / (2.0.pow(n)+1.0)

            //tと同じ形状の未初期化配列を生成
            val v = DoubleArray((2.0.pow(n)+1.0).toInt())
            val y = DoubleArray((2.0.pow(n)+1.0).toInt())

            //v[0]に初期値y0を代入
            v[0] = v0
            y[0] = y0

            //t毎の数値解を計算
            for (i in 0 until (y.size-1)) {
                v[i+1] = v[i] + g * dt
                y[i+1] = y[i] + v[i] * dt
            }
            return y
        }

        val deg = intent.getDoubleExtra("DEGREE", 20.0)
        val v0 = intent.getDoubleExtra("VELOCITY", 45.0)
        val x = euler(v0 * cos((deg/180.0)*PI), 0.0, 10, 0.0)
        val y = euler(v0 * sin((deg/180.0)*PI), 0.0, 10,  -9.8)
        val degView = findViewById<TextView>(R.id.degtext)
        val veloView = findViewById<TextView>(R.id.velotext)
        val xmaxView = findViewById<TextView>(R.id.xmaxtext)
        val ymaxView = findViewById<TextView>(R.id.ymaxtext)

        val chart: LineChart = findViewById(R.id.line_chart);


        val value1: ArrayList<Entry> = ArrayList()
        var xmax = 0.toFloat()
        var ymax = 0.toFloat()
        for ((i) in x.withIndex()) {
            if(y[i].toFloat() > 0.0 || i==0){
                value1.add(Entry(x[i].toFloat(),y[i].toFloat()))
                if(xmax < x[i].toFloat()){
                    xmax = x[i].toFloat()
                }
                if(ymax < y[i].toFloat()){
                    ymax = y[i].toFloat()
                }
            } else {
                break;
            }
        }
        if(xmax >= ymax){
            chart.xAxis.axisMaximum = xmax + 5
            chart.axisLeft.axisMaximum = xmax + 5
        }else{
            chart.xAxis.axisMaximum = ymax + 5
            chart.axisLeft.axisMaximum = ymax + 5
        }
        chart.axisRight.setDrawLabels(false)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.description.text = ""

        //chartに設定
        val dataSet1 = LineDataSet(value1, "Projectile Motion")
        dataSet1.color = Color.RED

        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(dataSet1)

        chart.data = LineData(dataSets)
        chart.invalidate() // refresh

        degView.text = deg.toString()
        veloView.text = v0.toString()
        val xmaxstr = xmax.toString()
        val ymaxstr = ymax.toString()
        xmaxView.text = "$xmaxstr m"
        ymaxView.text = "$ymaxstr m"

        val back = findViewById<Button>(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }
}