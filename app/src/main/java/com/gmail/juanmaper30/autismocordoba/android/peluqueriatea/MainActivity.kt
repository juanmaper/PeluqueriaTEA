package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDICE_VAMOS_PELUQUERIA = "Indice_VamosPeluqueria"
private const val SHARED_PREFERENCES_OPCION_CHICO_ESCOGIDA = "opcionChicoEscogida"

class MainActivity : AppCompatActivity(), PantallaPrincipalFragment.Callbacks,
    VamosPeluqueriaPaso1Fragment.Callbacks, VamosPeluqueriaPaso2Fragment.Callbacks,
    VamosPeluqueriaPaso3Fragment.Callbacks, VamosPeluqueriaPaso4Fragment.Callbacks,
    VamosPeluqueriaPaso5Fragment.Callbacks, VamosPeluqueriaPaso6Fragment.Callbacks,
    VamosPeluqueriaPaso7Fragment.Callbacks, VamosPeluqueriaPaso8Fragment.Callbacks,
    VamosPeluqueriaPaso9Fragment.Callbacks,
    AjustesFragment.Callbacks, ElijoMiPeinadoPantallaPrincipalFragment.Callbacks,
    ElijoMiPeinadoMiNuevoCorteDePeloPaso1.Callbacks, ElijoMiPeinadoMiNuevoCorteDePeloPaso2.Callbacks {


    /* Me creo el viewmodel que guarda informacion sobre el indice del paso a mostrar en
        Vamos a la peluqueria */
    private val mainActivityViewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Miro si hay algo en el bundle, por si el OS ha destruido el proceso, si no, pongo
        el indice a 0 */
        val indiceVamosPeluqueriaActual = savedInstanceState?.getInt(KEY_INDICE_VAMOS_PELUQUERIA, 0) ?: 0
        Log.i(TAG, "Valor cogido de savedInstanceState o defecto de VamosPeluqueria: $indiceVamosPeluqueriaActual")
        mainActivityViewModel.indiceInternoSecuenciaPasos = indiceVamosPeluqueriaActual
        mainActivityViewModel.opcionChicoElegida = recuperarOpcionChicoChica()
        mainActivityViewModel
        // Aqui bloqueo la actividad para que solo se muestre en modo landscape
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
          window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
          )

        setContentView(R.layout.activity_main)

        Log.i(TAG, "Actividad creada")
        Log.i(TAG, "Indice activityViewModel: ${mainActivityViewModel.indiceInternoSecuenciaPasos}")

        val fragmentoActual = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragmentoActual == null) {
            val fragmento = PantallaPrincipalFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragmento)
                .commit()
        }

    }

    /* Por si el OS destruye el proceso, guardo los datos que necesite:
        - Guardo el indice del modulo 2, VamosPeluqueria
     */
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "Guardando saveInstanceState de VamosPeluqueria con indice: ${mainActivityViewModel.indiceInternoSecuenciaPasos}")
        savedInstanceState.putInt(KEY_INDICE_VAMOS_PELUQUERIA, mainActivityViewModel.indiceInternoSecuenciaPasos)
    }

    /* Sobrescribo la funcion de PantallaPrincipalFragment que uso como interfaz para saber que se
        ha pulsado el boton del modulo de consejos. Al ser llamada, monto el modulo Consejos */
    override fun moduloConsejosSeleccionado() {
        Log.i(TAG, "Montando modulo consejos")
        val fragmentoConsejos = ConsejosFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoConsejos)
            .addToBackStack(null)
            .commit()
    }

    /* Sobrescribo la funcion de PantallaPrincipalFragment que uso como interfaz para saber que se
        ha pulsado el boton del modulo de vamos a la peluqueria. Al ser llamada, monto
        el modulo Vamos a la peluqueria */
    override fun moduloVamosPeluqueriaSeleccionado() {
        Log.i(TAG, "Montando modulo vamos a la peluqueria")
        mainActivityViewModel.reiniciarIndice()
        val fragmentoVamosPeluqueria = VamosPeluqueriaPaso1Fragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoVamosPeluqueria)
            .addToBackStack(null)
            .commit()
    }

    /* Sobrescribo la funcion de PantallaPrincipalFragment que uso como interfaz para saber que se
        ha pulsado el boton del modulo de Elijo mi peinado. Al ser llamada, monto
        el modulo Elijo mi peinado */
    override fun moduloElijoMiPeinadoSeleccionado() {
        Log.i(TAG, "Montando modulo Elijo mi peinado")
        val fragmentoElijoMiPeinado = ElijoMiPeinadoPantallaPrincipalFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoElijoMiPeinado)
            .addToBackStack(null)
            .commit()
    }


    /* Sobrescribo la funcion de PantallaPrincipalFragment que uso como interfaz para saber que se
        ha pulsado el boton del modulo de ajustes. Al ser llamada, monto
        el modulo ajustes */
    override fun moduloAjustesSeleccionado() {
        Log.i(TAG, "Montando modulo ajustes")
        val fragmentoAjustes = AjustesFragment.newInstance(mainActivityViewModel.getOpcionChicoElegida)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAjustes)
            .addToBackStack(null)
            .commit()
    }






    /* Sobrescribo la funcion de VamosPeluqueriaPaso1Fragment que uso como interfaz para saber
        que se ha pulsado el boton siguiente. Al ser llamada, monto el siguiente fragmento
        de la secuencia de Vamos a la peluqueria */
    override fun vamosPeluqueriaMontarSiguienteFragmento() {
        // Si estoy en el ultimo consejo, desmonto los fragmentos y vuelvo a la pantalla principal
        if (mainActivityViewModel.estoyEnUltimoConsejo) {
            Log.d(TAG, "Terminado vamosPeluqueria fragmento. Saliendo")
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        // Si no, monto el fragmento correspondiente segun el indice del viewmodel
        else {
            mainActivityViewModel.incrementarIndice()
            Log.i(TAG, "Indice incrementado a:${mainActivityViewModel.indiceInternoSecuenciaPasos}")

            Log.i(TAG, "Montando vamosPeluqueria fragmento ${mainActivityViewModel.getIndicePasoActualVamosPeluqueria}")

            val mostrarChico: Boolean = mainActivityViewModel.getOpcionChicoElegida

            val fragmentoVamosPeluqueria = when(mainActivityViewModel.getIndicePasoActualVamosPeluqueria) {
                1 -> VamosPeluqueriaPaso1Fragment()
                2 -> VamosPeluqueriaPaso2Fragment.newInstance(mostrarChico)
                3 -> VamosPeluqueriaPaso3Fragment.newInstance(mostrarChico)
                4 -> VamosPeluqueriaPaso4Fragment.newInstance(mostrarChico)
                5 -> VamosPeluqueriaPaso5Fragment.newInstance(mostrarChico)
                6 -> VamosPeluqueriaPaso6Fragment.newInstance(mostrarChico)
                7 -> VamosPeluqueriaPaso7Fragment.newInstance(mostrarChico)
                8 -> VamosPeluqueriaPaso8Fragment.newInstance(mostrarChico)
                9 -> VamosPeluqueriaPaso9Fragment.newInstance(mostrarChico)
                else -> VamosPeluqueriaPaso1Fragment()
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentoVamosPeluqueria)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun vamosPeluqueriaDecrementarIndiceCallback() {
        mainActivityViewModel.decrementarIndice()
        Log.i(TAG, "Indice decrementado a:${mainActivityViewModel.indiceInternoSecuenciaPasos}")
    }


    /* Modulo 3: Elijo mi peinado */

    override fun elijoMiPeinadoMontarModuloMisCortesDePelo() {
        Log.d(TAG, "Hola 1")
    }

    override fun elijoMiPeinadoMontarModuloMiNuevoCorteDePelo() {
        Log.i(TAG, "Montando modulo Elijo mi peinado Mi Nuevo Corte de Pelo Paso 1")
        val fragmentoElijoMiPeinadoNuevoCorteDePeloPaso1 = ElijoMiPeinadoMiNuevoCorteDePeloPaso1
            .newInstance(mainActivityViewModel.opcionChicoElegida)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoElijoMiPeinadoNuevoCorteDePeloPaso1)
            .addToBackStack(null)
            .commit()
    }

    override fun elijoMiPeinadoNuevoCorteDePeloMontarPaso2(opcionPeinado: Int) {
        mainActivityViewModel.opcionPeinadoEscogida = opcionPeinado

        Log.i(TAG, "Montando modulo Elijo mi peinado Mi Nuevo Corte de Pelo Paso 2")
        val fragmentoElijoMiPeinadoNuevoCorteDePeloPaso2 = ElijoMiPeinadoMiNuevoCorteDePeloPaso2
            .newInstance(mainActivityViewModel.opcionChicoElegida, mainActivityViewModel.opcionPeinadoEscogida)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoElijoMiPeinadoNuevoCorteDePeloPaso2)
            .addToBackStack(null)
            .commit()
    }

    override fun elijoMiPeinadoNuevoCorteDePeloMontarResultado(opcionColor: Int) {
        mainActivityViewModel.opcionColorPeinadoEscogida = opcionColor

        Log.d(TAG, "chicoElegido: ${mainActivityViewModel.opcionChicoElegida}, " +
                "peinado: ${mainActivityViewModel.opcionPeinadoEscogida}, " +
                "color: ${mainActivityViewModel.opcionColorPeinadoEscogida}")
    }






    override fun ajustesCambiarOpcionChicoChica(chicoElegido: Boolean) {
        mainActivityViewModel.opcionChicoElegida = chicoElegido
        guardarOpcionChicoChica()
        Log.i(TAG, "Opcion de chico cambiada en el viewmodel a: ${mainActivityViewModel.opcionChicoElegida}")
    }

    override fun onDestroy() {
        super.onDestroy()
        guardarOpcionChicoChica()
        Log.i(TAG, "Destruyendo actividad")
    }




    /* Funcion donde guardo en shared preferences la opcion elegida entre chico o chica */
    fun guardarOpcionChicoChica() {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(SHARED_PREFERENCES_OPCION_CHICO_ESCOGIDA, mainActivityViewModel.opcionChicoElegida)
            apply()
        }
        Log.d(TAG, "Guardado en shared preferences valor de opcion chico = ${mainActivityViewModel.opcionChicoElegida}")
    }

    /* Funcion que recupera la opcion chico o chica guardada en shared preferences */
    fun recuperarOpcionChicoChica(): Boolean {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        val opcionGuardada = sharedPref.getBoolean(SHARED_PREFERENCES_OPCION_CHICO_ESCOGIDA, true)
        Log.d(TAG, "Recuperada opcion chico guardada: $opcionGuardada")
        return opcionGuardada
    }

}
