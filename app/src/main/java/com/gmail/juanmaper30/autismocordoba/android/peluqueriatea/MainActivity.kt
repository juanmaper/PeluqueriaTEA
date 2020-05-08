package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Aqui bloqueo la actividad para que solo se muestre en modo landscape
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        setContentView(R.layout.activity_main)

        val fragmentoActual = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragmentoActual == null) {
            val fragmento = PantallaPrincipalFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragmento)
                .commit()
        }
    }
}
