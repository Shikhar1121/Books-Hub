package com.shikharpro.bookshub.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.shikharpro.bookshub.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            signUpUser()
        }

    }
   private fun signUpUser(){
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
       auth.createUserWithEmailAndPassword(editSignUpEmail.text.toString(), editSignUpPassword.text.toString())
           .addOnCompleteListener(this) { task ->
               if (task.isSuccessful) {
                   val user = auth.currentUser
                   user!!.sendEmailVerification()
                       .addOnCompleteListener { task ->
                           if (task.isSuccessful) {
                               startActivity(Intent(this,SignIn::class.java))
                               finish()
                           }
                       }


               } else {
                   Toast.makeText(baseContext, "Authentication failed!!Try Again After Some Time",
                       Toast.LENGTH_SHORT).show()

               }


           }

    }
}
