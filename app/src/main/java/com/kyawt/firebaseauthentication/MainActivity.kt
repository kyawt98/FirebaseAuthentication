package com.kyawt.firebaseauthentication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.common.internal.Objects
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null //...1
    private var mDatabase: FirebaseDatabase? = null //...2
    private var mDatabaseReference: DatabaseReference? = null

    private var firstName: String? = null //...3
    private var lastName: String? = null //...4

    private var email: String? = null //...5
    private var password: String? = null //...6
    private var mProgress: ProgressDialog? = null //...7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()
    }

    private fun initFirebase() {
        mProgress = ProgressDialog(this) //...8
        mDatabase = FirebaseDatabase.getInstance() //...9
        mAuth = FirebaseAuth.getInstance() //...10

        mDatabaseReference = mDatabase!!.reference!!.child("Users")

        btnRegister.setOnClickListener {
            // Onclick action for Register btn
            firstName = etfirstName.text.toString()
            lastName = etlastName.text.toString()
            email = etemail.text.toString()
            password = etpassword.text.toString()

            createAccount() //crete account function
        }
    }

    //    create account after pressing register btn
    private fun createAccount() {
        mProgress!!.setMessage("Register user....")
        mProgress!!.show()

        mAuth!!.createUserWithEmailAndPassword(email!!,password!!)
            .addOnCompleteListener{ task ->

            if (task.isSuccessful){
                Toast.makeText(this,"Success Sign Up", Toast.LENGTH_LONG).show()
                val userId = mAuth!!.currentUser!!.uid

                verifyEmail() // verify function

                val currentUserDb = mDatabaseReference!!.child(userId)
                currentUserDb.child("firstName").setValue(firstName)
                currentUserDb.child("lastName").setValue(lastName)

                updateUI() // update funtion
            }else{
                Toast.makeText(this,"Fail Sign Up", Toast.LENGTH_LONG).show()

            }
        }

    }

    private fun updateUI(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun verifyEmail(){
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this){task->
                if (task.isSuccessful){
                    Toast.makeText(this,"Verification Success!!", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Verification Fail", Toast.LENGTH_LONG).show()
                }
            }
    }
}
