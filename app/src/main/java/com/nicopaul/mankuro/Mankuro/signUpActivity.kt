package com.nicopaul.mankuro.Mankuro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.utils.AccessibilityHelper

class signUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AccessibilityHelper.applyAccessibility(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        val root = findViewById<View>(R.id.main)
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        btnSignUp?.setOnClickListener {
            Toast.makeText(this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainPageActivity::class.java))
            finish()
        }

        val tvGoToLogin = findViewById<Button>(R.id.tvGoToLogin)
        tvGoToLogin?.setOnClickListener {
            Toast.makeText(this, "Volviendo al inicio de sesión", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
