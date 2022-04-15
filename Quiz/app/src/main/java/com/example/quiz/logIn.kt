package com.example.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*
import java.util.regex.Pattern

class logIn : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_log_in)
        SignUponclick()
        LogInOnclick()

    }
    fun SignUponclick(){
        buttonSignUp_in_login.setOnClickListener {
            startActivity(Intent(applicationContext,SignUp::class.java))

        }
    }
    fun LogInOnclick(){
        buttonLogin.setOnClickListener {
            if (TextUtils.isEmpty(emailLogin.text.toString())) {
                emailLogin.setError("Email is Required ")
            }else if(!isValidEmail(emailLogin.text.toString())){
                emailLogin.setError("Email is not valide")
            } else if (TextUtils.isEmpty(emailLogin.text.toString())) {
                emailLogin.setError("password is Required ")
            } else if (passwordlogin.text.toString().length < 6) {
                passwordlogin.setError("Password Must be >= 6 characters ")
            } else {
                firebaseLogIn()
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
    //login
    fun firebaseLogIn(){
        firebaseAuth.signInWithEmailAndPassword(emailLogin.text.toString(),passwordlogin.text.toString()).addOnSuccessListener {
            val firebaseUser = firebaseAuth.currentUser
            val email = firebaseUser!!.email
            Toast.makeText(this,"LoggedIn as $email",Toast.LENGTH_LONG).show()
            startActivity(Intent(applicationContext,QuizzGame::class.java))
            finish()
        }.addOnFailureListener {e->
            Toast.makeText(this,"LogIn faild due to ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}