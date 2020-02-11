package com.kyawt.firebaseauthentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : AppCompatActivity() {

    private val email:String?=null
    private var mAuth:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        btnReset.setOnClickListener {

            sendEmail()
        }
    }

    fun sendEmail(){
       val email = forgotEmail?.text.toString()

        mAuth!!.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val message = "Email Sent"
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"No user Found", Toast.LENGTH_LONG).show()
                }
            }
    }
}
