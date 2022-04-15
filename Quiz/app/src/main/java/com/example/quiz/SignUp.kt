package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUp : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_sign_up)
        LogInOnclick()
        SignUpOnclick()

    }
     fun LogInOnclick(){
        buttonLogin_in_signup.setOnClickListener{
            startActivity(Intent(applicationContext,logIn::class.java))
        }
    }
    fun SignUpOnclick(){
        buttonSignUp.setOnClickListener {
            if (TextUtils.isEmpty(emailSignup.text.toString())) {
                emailSignup.setError("Email is Required ")
            } else if (!isValidEmail(emailSignup.text.toString())) {
                emailSignup.setError("Email is not valide ")
            } else if (TextUtils.isEmpty(passwordSignup.text.toString())) {
                passwordSignup.setError("password is Required ")
            } else if (passwordSignup.text.toString().length < 6) {
                passwordSignup.setError("Password Must be >= 6 characters ")
            } else {
                firebaseSingUp()
            }
        }
    }
    // verfication de l'email
    fun isValidEmail(email: String): Boolean {
        var isValid = true
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        if (!matcher.matches()) {
            isValid = false
        }
        return isValid
    }
    fun firebaseSingUp(){
        firebaseAuth.createUserWithEmailAndPassword(emailSignup.text.toString(),passwordSignup.text.toString()).addOnSuccessListener {
            val firebaseUser = firebaseAuth.currentUser
            val email = firebaseUser!!.email
            Toast.makeText(this,"Account Created with email $email",Toast.LENGTH_LONG).show()
            startActivity(Intent(applicationContext,logIn::class.java))
            finish()
        }.addOnFailureListener {e->
            Toast.makeText(this,"Sign Up faild due to ${e.message}",Toast.LENGTH_LONG).show()
        }
    }
}