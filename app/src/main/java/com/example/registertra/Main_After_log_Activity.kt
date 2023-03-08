package com.example.registertra

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Main_After_log_Activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_after_log)
        val getIntentshowBMI = intent.extras
        val showBMI = findViewById<TextView>(R.id.bmi_txt)
        val showStatus = findViewById<TextView>(R.id.txt_showrangeBmi)
        val showBMISt = findViewById<TextView>(R.id.showbmi_txt)


        if(getIntentshowBMI != null){
            val getShowbmistatus = getIntentshowBMI.getString("ShowBMI")
            showBMISt.setText(getShowbmistatus)
            val getBMI = getIntentshowBMI.getString("BMI")
            showBMI.setText(getBMI)
            val getStatus = getIntentshowBMI.getString("Status")
            showStatus.setText(getStatus)
        }
        val txtbmi = findViewById<TextView>(R.id.txtgocal)
        txtbmi.setOnClickListener{
            val intentgocal = Intent(this,Cal_BMI_Activity::class.java)
            startActivity(intentgocal)
        }
    }
}