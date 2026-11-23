package com.nicopaul.mankuro.Mankuro

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

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AccessibilityHelper.applyAccessibility(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        val root = findViewById<View>(R.id.main)
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val btnResetPassword = findViewById<Button>(R.id.btnResetPassword)
        btnResetPassword?.setOnClickListener {
            Toast.makeText(this, "Enlace de recuperación enviado al correo", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
