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

class logInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AccessibilityHelper.applyAccessibility(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)

        val root = findViewById<View>(R.id.main)
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin?.setOnClickListener {
            Toast.makeText(this, "Iniciando sesión...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainPageActivity::class.java))
            finish()
        }

        val btnForgotPassword = findViewById<Button>(R.id.btnForgotPassword)
        btnForgotPassword?.setOnClickListener {
            Toast.makeText(this, "Recuperación de contraseña", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister?.setOnClickListener {
            Toast.makeText(this, "Registro de usuario", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, signUpActivity::class.java))
        }
    }
}
