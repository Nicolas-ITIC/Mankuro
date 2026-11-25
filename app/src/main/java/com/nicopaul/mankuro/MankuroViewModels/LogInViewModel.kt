package com.nicopaul.mankuro.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogInViewModel : ViewModel() {

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> = _emailError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> = _passwordError

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    fun validateLogin(email: String, password: String) {
        val cleanEmail = email.trim()
        var isValid = true

        if (cleanEmail.isEmpty()) {
            _emailError.value = "El correo electrónico es obligatorio"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) {
            _emailError.value = "Introduce un correo electrónico válido"
            isValid = false
        } else {
            _emailError.value = ""
        }

        if (password.isEmpty()) {
            _passwordError.value = "La contraseña es obligatoria"
            isValid = false
        }  else {
            _passwordError.value = ""
        }

        if (isValid) {
            _loginSuccess.value = true
        }
    }
}