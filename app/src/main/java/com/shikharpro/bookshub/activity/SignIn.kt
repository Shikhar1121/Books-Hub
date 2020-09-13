package com.shikharpro.bookshub.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.shikharpro.bookshub.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)

        btnRegisterYourself.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
            finish()
        }

        btnSignIn.setOnClickListener {
            doLogin()
        }
    }

     private fun updateUI(currentUser: FirebaseUser?){
        if(currentUser!=null){
            if(currentUser.isEmailVerified){ startActivity(Intent(this,MainActivity::class.java))
            }else{
                Toast.makeText(baseContext, "Please Verify your Email Address!!",
                    Toast.LENGTH_SHORT).show()
                updateUI(null)
            }

        }else{
            Toast.makeText(baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT).show()
            updateUI(null)

        }

    }
    fun doLogin(){
        if(editSignUpEmail.text.toString().isEmpty()){
            editSignUpEmail.error="Please  Email"
            editSignUpEmail.requestFocus()
            return
        }


        if(editSignUpPassword.text.toString().isEmpty()){
            editSignUpEmail.error="Please enter Password!!"
            editSignUpEmail.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(editSignUpEmail.text.toString(), editSignUpPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }


            }
    }
}