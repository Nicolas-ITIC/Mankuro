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
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.nicopaul.mankuro.R
import com.nicopaul.mankuro.utils.AccessibilityHelper
import androidx.activity.viewModels

class SignUpActivity : AppCompatActivity() {
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        AccessibilityHelper.applyAccessibility(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        val etUsername = findViewById<TextInputEditText>(R.id.etFullName)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)

        val tilUsername = findViewById<TextInputLayout>(R.id.tilFullName)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val tilConfirmPassword = findViewById<TextInputLayout>(R.id.tilConfirmPassword)

        etUsername.doAfterTextChanged {
            viewModel.updateUsername(it.toString())
        }

        etEmail.doAfterTextChanged {
            viewModel.updateEmail(it.toString())
        }

        etPassword.doAfterTextChanged {
            viewModel.updatePassword(it.toString())
        }

        etConfirmPassword.doAfterTextChanged {
            viewModel.updateConfirmPassword(it.toString())
        }

        viewModel.usernameError.observe(this) { error ->
            tilUsername.error = error
        }

        viewModel.emailError.observe(this) { error ->
            tilEmail.error = error
        }

        viewModel.passwordError.observe(this) { error ->
            tilPassword.error = error
        }

        viewModel.confirmPasswordError.observe(this) { error ->
            tilConfirmPassword.error = error
        }

        viewModel.isFormValid.observe(this) { isValid ->
            if (isValid == true) {
                Toast.makeText(
                    this,
                    "Cuenta creada con éxito",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(Intent(this, MainPageActivity::class.java))
                finish()
            }
        }

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
            viewModel.validateForm()
        }

        val tvGoToLogin = findViewById<Button>(R.id.tvGoToLogin)
        tvGoToLogin?.setOnClickListener {
            Toast.makeText(this, "Volviendo al inicio de sesión", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
