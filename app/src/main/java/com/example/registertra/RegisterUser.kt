package com.example.registertra

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Toast.makeText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterUser : AppCompatActivity() {

    private lateinit var rgsusernameedt: String
    private lateinit var rgspasswordedt: String
    private lateinit var rgsemailedt: String
    private lateinit var rgsfnameedt: String
    private lateinit var rgslnameedt: String
    private lateinit var rgsphonenumedt: String
    private lateinit var datebirth:String
    private lateinit var weight:String
    private lateinit var height:String
    private lateinit var goal:String
    private lateinit var targetweight:String
    private lateinit var typehealthme:String
    private lateinit var genderD:String
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth;


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        database =
            FirebaseDatabase.getInstance("https://registertra-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("UserRegister")
        auth = Firebase.auth
        val spinergender = findViewById<Spinner>(R.id.gender_spinner)
        val radiotypegoal = findViewById<RadioGroup>(R.id.goaloption)
        val optiontypehealth = findViewById<RadioGroup>(R.id.wayoption)
        val gender = arrayOf("ชาย","หญิง","LGBTQ")
        val spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,gender)
        spinergender.adapter = spinnerAdapter
        spinergender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {
                //makeText(this@Regis_Profile,gender[index],Toast.LENGTH_LONG).show()
                genderD = gender[index]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        if (radiotypegoal.checkedRadioButtonId ==-1 && optiontypehealth.checkedRadioButtonId == -1 ){
            goal = radiotypegoal.toString()
            typehealthme = optiontypehealth.toString()
        }
        val buttontnrgsU = findViewById<Button>(R.id.btnrgs)
        buttontnrgsU.setOnClickListener {
            saveUserRgs()
        }

    }

    private fun saveUserRgs() {
        val rgsUsername = findViewById<EditText>(R.id.rgsuser)
        val rgsPassWord = findViewById<EditText>(R.id.rgspass)
        val rgsemailaddress = findViewById<EditText>(R.id.rgsemailuser)
        val rgsFirstName = findViewById<EditText>(R.id.rgsfname)
        val rgsLastName = findViewById<EditText>(R.id.rgslname)
        val rgsphonenumber = findViewById<EditText>(R.id.rgsphone)
        val edtdatebirth = findViewById<EditText>(R.id.edtdate)
        val edtweight = findViewById<EditText>(R.id.edtweight)
        val edtheight = findViewById<EditText>(R.id.edtheight)
        val edttarget = findViewById<EditText>(R.id.edttargetweight)
        rgsusernameedt = rgsUsername.text.toString()
        rgspasswordedt = rgsPassWord.text.toString()
        rgsemailedt = rgsemailaddress.text.toString()
        rgsfnameedt = rgsFirstName.text.toString()
        rgslnameedt = rgsLastName.text.toString()
        rgsphonenumedt = rgsphonenumber.text.toString()
        datebirth = edtdatebirth.text.toString()
        weight = edtweight.text.toString()
        height = edtheight.text.toString()
        targetweight = edttarget.text.toString()

        if (rgsusernameedt.isEmpty()) {
            //username
            makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show()
        }
        if (rgspasswordedt.isEmpty()) {
            //password
            makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
        }
        if (rgsemailedt.isEmpty()) {
            //email
            makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
        }
        if (rgsfnameedt.isEmpty()) {
            //firstname
            makeText(this, "Please enter your firstname", Toast.LENGTH_SHORT).show()
        }
        if (rgslnameedt.isEmpty()) {
            //lastname
            makeText(this, "Please enter your lastname", Toast.LENGTH_SHORT).show()
        }
        if (rgsphonenumedt.isEmpty()) {
            //phonenum
            makeText(this, "Please enter your PhoneNumber", Toast.LENGTH_SHORT).show()
        }
        if (datebirth.isEmpty()){
            //datebirth
            makeText(this, "Please enter your datebirth", Toast.LENGTH_SHORT).show()
        }
        if (weight.isEmpty()){
            //weight
            makeText(this, "Please enter your weight", Toast.LENGTH_SHORT).show()
        }
        if (height.isEmpty()){
            //height
            makeText(this, "Please enter your datebirth", Toast.LENGTH_SHORT).show()
        }
        else if (rgsphonenumedt.length != 10) {
            makeText(this, "Please enter your correct PhoneNumber", Toast.LENGTH_SHORT).show()
        } else {
            auth.createUserWithEmailAndPassword(rgsemailedt, rgspasswordedt)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("MyApp", "create new User Success!")
                        val userrgsID = database.push().key
                        val UserRGS =
                            UserReGis(rgsusernameedt, rgspasswordedt, rgsemailedt,  rgsfnameedt, rgslnameedt, rgsphonenumedt, genderD, datebirth, weight, height, goal, targetweight, typehealthme)
                        if (userrgsID != null) {
                            database.child(userrgsID).setValue(UserRGS)
                                .addOnCompleteListener {
                                    makeText(this, "Data insert Success", Toast.LENGTH_SHORT).show()
                                    val intentrgugobackhome = Intent(this, HomeActivity::class.java)
                                    startActivity(intentrgugobackhome)
                                }.addOnFailureListener { err ->
                                    makeText(this, "ERROR ${err.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                        val userhealthme = auth.currentUser
                        updateUI(userhealthme)
                    } else {
                        Log.w("MyApp", "Failure Signup", task.exception)
                        makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
                .addOnFailureListener {
                    makeText(baseContext, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updateUI(userhealthme: FirebaseUser?) {
        if (userhealthme != null) {
            return
        }
    }
}