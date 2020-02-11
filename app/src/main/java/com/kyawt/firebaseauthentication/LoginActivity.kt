package com.kyawt.firebaseauthentication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var email : String?= null
    private var password: String?=null
    private var mProgress : ProgressDialog?=null

    private var mAuth: FirebaseAuth?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initLogin()
    }

    private fun initLogin(){
        mProgress = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser(){
        email = loginEmail.text.toString()
        password = loginPassword.text.toString()

        mProgress!!.setMessage("Login User....")
        mProgress!!.show()

        mAuth!!.signInWithEmailAndPassword(email!!,password!!)
            .addOnCompleteListener(this){task ->
                mProgress!!.hide()
                if (task.isSuccessful){
                    mProgress!!.hide()
                    val intent=Intent(this, HomeActivity::class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(this,"Login Fail", Toast.LENGTH_LONG).show()
                }
            }

    }
}
