package com.example.registertra

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class Cal_BMI_Activity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cal_bmi)

        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnRecalculate = findViewById<Button>(R.id.btnReCalculate)
        val edtHeight = findViewById<EditText>(R.id.edtHeight)
        val edtWeight = findViewById<EditText>(R.id.edtWeight)
        val txtvshowbmi = findViewById<TextView>(R.id.showbmi)
        val showstatus = findViewById<TextView>(R.id.txtstatus)
        val bmishow = findViewById<TextView>(R.id.bmishow)
        val back = findViewById<ImageButton>(R.id.btnbackbmi)

        back.setOnClickListener{
            var intentgoback = Intent(this, HomeActivity::class.java)
            startActivity(intentgoback)
        }

        btnCalculate.setOnClickListener {

            if (edtHeight.text.isNotEmpty() && edtWeight.text.isNotEmpty()) {
                val height = (edtHeight.text.toString()).toInt()
                val weight = (edtWeight.text.toString()).toInt()

                val BMI = calculateBMI(height, weight)

                txtvshowbmi.text = BMI.toString()
                txtvshowbmi.visibility = View.VISIBLE

                if (BMI < 18.5) {
                    showstatus.text = "ท่านอยู่ในเกณฑ์ : ผอมเกินไป"
                    val intentgoHome = Intent(this,Main_After_log_Activity::class.java)
                    intentgoHome.putExtra("ShowBMI",bmishow.text.toString())
                    intentgoHome.putExtra("BMI",txtvshowbmi.text.toString())
                    intentgoHome.putExtra("Status",showstatus.text.toString())
                    startActivity(intentgoHome)
                } else if (BMI >= 18.5 && BMI < 22.90) {
                    showstatus.text = "ท่านอยู่ในเกณฑ์ : ปกติ"
                    val intentgoHome = Intent(this,Main_After_log_Activity::class.java)
                    intentgoHome.putExtra("ShowBMI",bmishow.text.toString())
                    intentgoHome.putExtra("BMI",txtvshowbmi.text.toString())
                    intentgoHome.putExtra("Status",showstatus.text.toString())
                    startActivity(intentgoHome)
                } else if (BMI >= 23 && BMI < 24.90) {
                    showstatus.text = "ท่านอยู่ในเกณฑ์ : น้ำหนักเกิน(ท้วม)"
                    val intentgoHome = Intent(this,Main_After_log_Activity::class.java)
                    intentgoHome.putExtra("ShowBMI",bmishow.text.toString())
                    intentgoHome.putExtra("BMI",txtvshowbmi.text.toString())
                    intentgoHome.putExtra("Status",showstatus.text.toString())
                    startActivity(intentgoHome)
                } else if (BMI >= 25 && BMI < 29.90) {
                    showstatus.text = "ท่านอยู่ในเกณฑ์ : อ้วน"
                    val intentgoHome = Intent(this,Main_After_log_Activity::class.java)
                    intentgoHome.putExtra("ShowBMI",bmishow.text.toString())
                    intentgoHome.putExtra("BMI",txtvshowbmi.text.toString())
                    intentgoHome.putExtra("Status",showstatus.text.toString())
                    startActivity(intentgoHome)
                } else if (BMI > 30) {
                    showstatus.text = "ท่านอยู่ในเกณฑ์ : อ้วนมาก"
                    val intentgoHome = Intent(this,Main_After_log_Activity::class.java)
                    intentgoHome.putExtra("ShowBMI",bmishow.text.toString())
                    intentgoHome.putExtra("BMI",txtvshowbmi.text.toString())
                    intentgoHome.putExtra("Status",showstatus.text.toString())
                    startActivity(intentgoHome)
                }

                bmishow.visibility = View.VISIBLE
                showstatus.visibility = View.VISIBLE
                btnRecalculate.visibility = View.VISIBLE
                btnCalculate.visibility = View.GONE

            } else {
                Toast.makeText(this, "กรุณากรอกน้ำหนักและส่วนสูงให้ถูกต้องและครบถ้วน", Toast.LENGTH_SHORT).show()
            }
        }
        btnRecalculate.setOnClickListener {
            ResetEverything()
        }
    }

    private fun ResetEverything() {

        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnRecalculate = findViewById<Button>(R.id.btnReCalculate)
        val edtHeight = findViewById<EditText>(R.id.edtHeight)
        val edtWeight = findViewById<EditText>(R.id.edtWeight)
        val txtvshowbmi = findViewById<TextView>(R.id.showbmi)
        val showstatus = findViewById<TextView>(R.id.txtstatus)
        val bmishow = findViewById<TextView>(R.id.bmishow)

        btnCalculate.visibility = View.VISIBLE
        btnRecalculate.visibility = View.GONE
        edtHeight.text.clear()
        edtWeight.text.clear()
        showstatus.text = ""
        bmishow.visibility = View.GONE
        txtvshowbmi.visibility = View.GONE

    }

    private fun calculateBMI(height: Int, weight: Int): Float {

        val Heightdot = height.toFloat() / 100
        val BMI = weight.toFloat() / (Heightdot * Heightdot)

        return BMI
    }

}