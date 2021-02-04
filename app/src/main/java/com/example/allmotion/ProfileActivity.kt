package com.example.allmotion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var logoutButton: Button
    private lateinit var inputName: EditText
    private lateinit var inputImageUrl: EditText
    private lateinit var saveButton: Button
    private lateinit var nameTextView: TextView
    private lateinit var imageView: ImageView

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        inputName = findViewById(R.id.nameEditText)
        inputImageUrl = findViewById(R.id.imageUrlEditText)
        saveButton = findViewById(R.id.saveButton)
        nameTextView = findViewById(R.id.nameTextView)
        imageView = findViewById(R.id.profileView)
        logoutButton = findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        saveButton.setOnClickListener {
            val name = inputName.text.toString()
            val url = inputImageUrl.text.toString()

            val p = Personinfo(name, url)

            if (auth.currentUser?.uid != null) {
                db.child(auth.currentUser?.uid!!).setValue(p).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "წარმატებით შეინახა", Toast.LENGTH_SHORT).show()
                        inputName.text = null
                        inputImageUrl.text = null
                    } else {
                        Toast.makeText(this, "დაფიქსირდა შეცდომა", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        if (auth.currentUser?.uid != null) {
            db.child(auth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    val p = snapshot.getValue(Personinfo::class.java)
                    if (p != null) {
                        // Name
                        nameTextView.text = p.name

                        // Profile Photo
                        Glide.with(this@ProfileActivity)
                            .load(p.imageUrl)
                            .centerCrop()
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(imageView)

                    }
                }

            })

        }

        }

}


