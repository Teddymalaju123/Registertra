package com.example.registertra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.makeText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login_Activity : AppCompatActivity() {

    lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    lateinit var btnLogin: Button

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btnLogin)
        etEmail = findViewById(R.id.edtEmailAddress)
        etPass = findViewById(R.id.etPassword)

        auth = Firebase.auth

        btnLogin.setOnClickListener {
            login()
        }
    }


    private fun login() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        // calling signInWithEmailAndPassword(email, pass)
        // function using Firebase auth object
        // On successful response Display a Toast
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val intentgohomepage = Intent(this,Main_After_log_Activity::class.java)
                startActivity(intentgohomepage)
            } else {
                makeText(baseContext, "Log In failed ", Toast.LENGTH_SHORT).show()
            }
        }
            .addOnFailureListener {
                makeText(baseContext,"Authentication failed.",Toast.LENGTH_SHORT).show()
            }
    }

}