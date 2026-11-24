package com.nicopaul.mankuro.Mankuro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
// EXTRA (stackoverflow)
import android.util.Patterns

class SignUpViewModel : ViewModel() {
    // 1. Declarar MutableLiveData PRIVAT

    val _username = MutableLiveData<String>()
    val _email = MutableLiveData<String>()
    val _password = MutableLiveData<String>()
    val _confirmPassword = MutableLiveData<String>()

    // 2. Exposar LiveData immutable PÚBLIC

    val username: LiveData<String> = _username
    val email: LiveData<String> = _email
    val password: LiveData<String> = _password
    val confirmPassword: LiveData<String> = _confirmPassword

    // 3. Funcions per modificar les dades

    fun updateUsername(value: String) {
        _username.value = value
    }

    fun updateEmail(value: String) {
        _email.value = value
    }

    fun updatePassword(value: String) {
        _password.value = value
    }

    fun updateConfirmPassword(value: String) {
        _confirmPassword.value = value
    }

    // 4. (Extra) Errores de validación
    private val _usernameError = MutableLiveData<String?>()
    val usernameError: LiveData<String?> = _usernameError

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _confirmPasswordError = MutableLiveData<String?>()
    val confirmPasswordError: LiveData<String?> = _confirmPasswordError

    // Resultado de la validación (Iniciamos en false para que no valide sin escribir nada)
    private val _isFormValid = MutableLiveData(false)
    val isFormValid: LiveData<Boolean> = _isFormValid

    // EN FUTURO COMPROBAR SI USUARIO O EMAIL YA ESTA REGISTRADO (BBDD)
    fun validateForm() {

        val name = _username.value.orEmpty().trim()
        val mail = _email.value.orEmpty().trim()
        val pass = _password.value.orEmpty()
        val confirmPass = _confirmPassword.value.orEmpty()

        // Validar username
        _usernameError.value = if (name.isEmpty()) {
            "El nombre de usuario es obligatorio"
        } else {
            null
        }

        // Validar email (utilizando libreria no explicada Patterns)
        _emailError.value = if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            "Introduce un email válido"
        } else {
            null
        }

        // Validar contraseña
        _passwordError.value = if (pass.length < 8) {
            "La contraseña debe tener al menos 8 caracteres"
        } else {
            null
        }

        // Validar confirmación de contraseña
        _confirmPasswordError.value = if (confirmPass != pass) {
            "Las contraseñas no coinciden"
        } else {
            null
        }

        // Comprobar si todo es correcto
        _isFormValid.value =
                    _usernameError.value == null &&
                    _emailError.value == null &&
                    _passwordError.value == null &&
                    _confirmPasswordError.value == null
    }

}