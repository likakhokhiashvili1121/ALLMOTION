package com.example.allmotion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()

        if(auth.currentUser != null) {
            startActivity(Intent(this, MainPageActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_login)

        inputEmail = findViewById(R.id.emailEditText)
        inputPassword = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        signupButton = findViewById(R.id.signUpButton)


        loginButton.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "შეავსეთ ყველა ველი", Toast.LENGTH_LONG).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainPageActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "შეცდომა", Toast.LENGTH_LONG).show()
                        }
                    }
            }

        }

        signupButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

}





