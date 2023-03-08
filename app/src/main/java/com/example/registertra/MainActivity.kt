package com.example.registertra

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var idcard: String
    private lateinit var firstname: String
    private lateinit var lastname: String
    private lateinit var phonenumber: String
    private val PICK_IMAGE_REQUEST = 111
    private var filepath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var imagePreview: ImageView
    private lateinit var database: DatabaseReference

    private lateinit var auth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        database =
            FirebaseDatabase.getInstance("https://registertra-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("UserTrianer")
        auth = Firebase.auth

        val btnUpLoadImage = findViewById<Button>(R.id.buttonupload)


        val btnSignup = findViewById<Button>(R.id.buttonSignup)




        btnUpLoadImage.setOnClickListener {
            startFileChooser()
        }

        btnSignup.setOnClickListener {
            uploadFile()
            saveUserTrianerData()
            val intentgohome = Intent(this,HomeActivity::class.java)
            startActivity(intentgohome)
        }

    }


    private fun saveUserTrianerData() {
        val edtUsername = findViewById<EditText>(R.id.textViewuserName)
        val edtPassWord = findViewById<EditText>(R.id.editTextTextPassword)
        val edtemailaddress = findViewById<EditText>(R.id.editTextTextEmailAddress5)
        val edtIDcard = findViewById<TextView>(R.id.textViewIDCard)
        val edtFirstName = findViewById<EditText>(R.id.textViewFName)
        val edtLastName = findViewById<EditText>(R.id.textViewLastname)
        val edtphonenumber = findViewById<EditText>(R.id.editTextPhone)

        username = edtUsername.text.toString()
        password = edtPassWord.text.toString()
        email = edtemailaddress.text.toString()
        idcard = edtIDcard.text.toString()
        firstname = edtFirstName.text.toString()
        lastname = edtLastName.text.toString()
        phonenumber = edtphonenumber.text.toString()


        if (username.isEmpty()) {
            makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show()
        }
        if (password.isEmpty()) {
            makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
        }
        if (email.isEmpty()) {
            makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
        }
        if (idcard.isEmpty()) {
            makeText(this, "Please enter your idcard", Toast.LENGTH_SHORT).show()
        }
        if (firstname.isEmpty()) {
            makeText(this, "Please enter your firstname", Toast.LENGTH_SHORT).show()
        }
        if (lastname.isEmpty()) {
            makeText(this, "Please enter your lastname", Toast.LENGTH_SHORT).show()
        }
        if (phonenumber.isEmpty()) {
            makeText(this, "Please enter your PhoneNumber", Toast.LENGTH_SHORT).show()
        } else if (phonenumber.length != 10) {
            makeText(this, "Please enter your correct PhoneNumber", Toast.LENGTH_SHORT).show()
        } else if (idcard.length != 13) {
            makeText(this, "Please enter your IDCard", Toast.LENGTH_SHORT).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
            .addOnFailureListener {
                makeText(this,"Error ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
            }
            val trianerID = database.push().key
            val Trianer =
                UserTrianer(username, password, email, idcard, firstname, lastname, phonenumber,)

            if (trianerID != null) {
                database.child(trianerID).setValue(Trianer)
                    .addOnCompleteListener {
                        makeText(this, "Data insert Success", Toast.LENGTH_SHORT).show()
                        val intentgohome = Intent(this,HomeActivity::class.java)
                        startActivity(intentgohome)
                    }.addOnFailureListener { err ->
                        makeText(this, "ERROR ${err.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun uploadFile() {
        if (filepath != null) {
            val ref = storageReference?.child("myImages/" + UUID.randomUUID().toString())
            ref?.putFile(filepath!!)

        } else {
            makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }


    private fun startFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_REQUEST
        )
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePreview = findViewById(R.id.imageView)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filepath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
                imagePreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            return
        }
    }
}

