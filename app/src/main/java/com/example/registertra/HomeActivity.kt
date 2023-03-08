package com.example.registertra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnrgsuser = findViewById<Button>(R.id.buttonrgsuser)
        val btnrgstr = findViewById<Button>(R.id.buttonrgstr)
        val btnLogin = findViewById<Button>(R.id.button3)
        btnrgstr.setOnClickListener {
            val intentgohomestr = Intent(this,MainActivity::class.java)
            startActivity(intentgohomestr)
        }

        btnrgsuser.setOnClickListener {
            val intentgohomeuser = Intent(this,RegisterUser::class.java)
            startActivity(intentgohomeuser)
        }

        btnLogin.setOnClickListener {
            val intentgologin = Intent(this,Login_Activity::class.java)
            startActivity(intentgologin)
        }

    }
}