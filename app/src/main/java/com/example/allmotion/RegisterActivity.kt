package com.example.allmotion


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null
    
    private lateinit var RegisterButton: Button

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmpassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        email = findViewById(R.id.signUpEmailEditText)
        password = findViewById(R.id.signUpPasswordEditText)
        confirmpassword = findViewById(R.id.signUpConfirmPasswordEditText)
        register()
    }

    private fun register() {

        RegisterButton = findViewById(R.id.signUpButtonReg)
        RegisterButton.setOnClickListener {

            var email = email.text.toString()
            var password = password.text.toString()
            var confirmpassword = confirmpassword.text.toString()

            if (email.isEmpty() && password.isEmpty() && confirmpassword.isEmpty() && (password != confirmpassword)) {
                Toast.makeText(this, "შეიყვანეთ ყველა ველი სწორად !", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("! ! !")
                            builder.setMessage("თქვენი პერსონალური ინფორმაცია შენახულია და დაცულია ბაზაში")
                            builder.setPositiveButton("გასაგებია") { dialog, i ->
                                dialog.dismiss()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                            builder.show().setCanceledOnTouchOutside(false)
                        } else {
                            Toast.makeText(this, "შეამოწმეთ ყველა ველი", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}
