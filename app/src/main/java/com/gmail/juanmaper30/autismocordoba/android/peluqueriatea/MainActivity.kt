package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), PantallaPrincipalFragment.Callbacks {


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
        Log.i(TAG, "Actividad creada")
    }

    /* Sobrescribo la funcion de ConsejosFragment que uso como interfaz. Al ser llamada,
        monto el modulo Consejos */
    override fun moduloConsejosSeleccionado() {
        Log.i(TAG, "Montando modulo consejos")
        val fragmentoConsejos = ConsejosFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoConsejos)
            .addToBackStack(null)
            .commit()
    }
}
