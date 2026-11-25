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
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.utils.AccessibilityHelper
import com.nicopaul.mankuro.viewmodel.LogInViewModel

class logInActivity : AppCompatActivity() {

    private lateinit var viewModel: LogInViewModel

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

        viewModel = ViewModelProvider(this)
            .get(LogInViewModel::class.java)

        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        viewModel.emailError.observe(this) { errorMsg ->
            tilEmail.error = if (errorMsg.isEmpty()) null else errorMsg
        }

        viewModel.passwordError.observe(this) { errorMsg ->
            tilPassword.error = if (errorMsg.isEmpty()) null else errorMsg
        }

        viewModel.loginSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "Iniciando sesión...", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainPageActivity::class.java))
                finish()
            }
        }

        btnLogin?.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            viewModel.validateLogin(email, password)
        }

        val btnForgotPassword = findViewById<Button>(R.id.btnForgotPassword)
        btnForgotPassword?.setOnClickListener {
            Toast.makeText(this, "Recuperación de contraseña", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister?.setOnClickListener {
            Toast.makeText(this, "Registro de usuario", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SignUpActivity::class.java))
        }


    }
}
