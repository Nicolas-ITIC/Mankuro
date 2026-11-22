package com.nicopaul.mankuro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private val openMainActivity = Runnable {
        startActivity(Intent(this, MainActivity::class.java))
        this.overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.splashLogo)

        // Estado inicial de la animación
        logo.alpha = 0f
        logo.scaleX = 0.67f
        logo.scaleY = 0.67f

        // Aparición y pequeño zoom
        logo.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(1000)
            .start()

        // Abrir la pantalla principal
        handler.postDelayed(openMainActivity, 2200)
    }

    override fun onDestroy() {
        handler.removeCallbacks(openMainActivity)
        super.onDestroy()
    }
}
