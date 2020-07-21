package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"

private const val KEY_INDICE_VAMOS_PELUQUERIA = "Indice_VamosPeluqueria"
private const val KEY_OPCION_PEINADO_ELEGIDA_ELIJO_MI_PEINADO = "opcion_peinado_elegida_ElijoMiPeinado"
private const val KEY_OPCION_COLOR_ELEGIDA_ELIJO_MI_PEINADO = "opcion_color_elegida_ElijoMiPeinado"

private const val SHARED_PREFERENCES_OPCION_CHICO_ESCOGIDA = "opcionChicoEscogida"
private const val SHARED_PREFERENCES_LISTA_AVATARES = "listaDeAvataresAlmacenada"

class MainActivity : AppCompatActivity(), PantallaPrincipalFragment.Callbacks,
    VamosPeluqueriaPaso1Fragment.Callbacks, VamosPeluqueriaPaso2Fragment.Callbacks,
    VamosPeluqueriaPaso3Fragment.Callbacks, VamosPeluqueriaPaso4Fragment.Callbacks,
    VamosPeluqueriaPaso5Fragment.Callbacks, VamosPeluqueriaPaso6Fragment.Callbacks,
    VamosPeluqueriaPaso7Fragment.Callbacks, VamosPeluqueriaPaso8Fragment.Callbacks,
    VamosPeluqueriaPaso9Fragment.Callbacks,
    AjustesFragment.Callbacks, ElijoMiPeinadoPantallaPrincipalFragment.Callbacks,
    ElijoMiPeinadoMiNuevoCorteDePeloPaso1Fragment.Callbacks, ElijoMiPeinadoMiNuevoCorteDePeloPaso2Fragment.Callbacks,
    ElijoMiPeinadoMiNuevoCorteDePeloResultadoFragment.Callbacks, ElijoMiPeinadoMisCortesDePeloFragment.Callbacks,
    JuegoAsociacionSonidosFragment.Callbacks, JuegoAsociacionSonidosResultadoFragment.Callbacks,
    AjustesGestionCitasFragment.Callbacks{


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

        /* Miro si hay algo en el bundle relativo al modulo 3 */
        val opcionPeinadoElijoMiPeinadoActual = savedInstanceState?.getInt(
            KEY_OPCION_PEINADO_ELEGIDA_ELIJO_MI_PEINADO, 2) ?: 2
        val opcionColorElijoMiPeinadoActual = savedInstanceState?.getInt(
            KEY_OPCION_COLOR_ELEGIDA_ELIJO_MI_PEINADO, 2) ?: 2

        Log.i(TAG, "Valor cogido de savedInstanceState o defecto de VamosPeluqueria: $indiceVamosPeluqueriaActual")
        mainActivityViewModel.indiceInternoSecuenciaPasos = indiceVamosPeluqueriaActual
        mainActivityViewModel.opcionChicoElegida = recuperarOpcionChicoChica()
        mainActivityViewModel.opcionPeinadoEscogida = opcionPeinadoElijoMiPeinadoActual
        mainActivityViewModel.opcionColorPeinadoEscogida = opcionColorElijoMiPeinadoActual

        val cadenaListaAvatares = recuperarCadenaListaAvatares()
        mainActivityViewModel.cargarListaAvataresConCadena(cadenaListaAvatares)

        // Aqui bloqueo la actividad para que solo se muestre en modo landscape
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //supportActionBar?.title = ""

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
        Log.i(TAG, "Guardando saveInstanceState de VamosPeluqueria con indice: " +
                "${mainActivityViewModel.indiceInternoSecuenciaPasos}")
        savedInstanceState.putInt(KEY_INDICE_VAMOS_PELUQUERIA, mainActivityViewModel.indiceInternoSecuenciaPasos)

        Log.i(TAG, "Guardando saveInstanceState de ElijoMiPeinado con opcionPeinado: " +
                "${mainActivityViewModel.opcionPeinadoEscogida} y opcionColor: " +
                "${mainActivityViewModel.opcionColorPeinadoEscogida}")

        savedInstanceState.putInt(KEY_OPCION_PEINADO_ELEGIDA_ELIJO_MI_PEINADO,
            mainActivityViewModel.opcionPeinadoEscogida)
        savedInstanceState.putInt(KEY_OPCION_COLOR_ELEGIDA_ELIJO_MI_PEINADO,
            mainActivityViewModel.opcionColorPeinadoEscogida)
    }

    /* Sobrescribo la funcion de PantallaPrincipalFragment que uso como interfaz para saber que se
        ha pulsado el boton del modulo de consejos. Al ser llamada, monto el modulo Consejos */
    override fun moduloConsejosSeleccionado() {
        Log.i(TAG, "Montando modulo consejos")
        val fragmentoConsejos = ConsejosFragment()
        Log.d(TAG, "Opcion chico: ${recuperarOpcionChicoChica()}")
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
        if (mainActivityViewModel.numeroAvataresGuardadosEnLista() != 0) {
            val fragmentoElijoMiPeinado = ElijoMiPeinadoPantallaPrincipalFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentoElijoMiPeinado)
                .addToBackStack(null)
                .commit()
        } else {
            elijoMiPeinadoMontarModuloMiNuevoCorteDePelo()
        }
    }

    /* Sobrescribo la funcion de PantallaPrincipalFragment que uso como interfaz para saber que se
        ha pulsado el boton del modulo de Juegos. Al ser llamada, monto
        el modulo Elijo mi peinado */
    override fun moduloJuegoAsociacionSonidosSeleccionado() {
        Log.i(TAG, "Montando modulo juego asociacion de sonidos")
        val fragmentoJuegoAsociacionSonidosFragment = JuegoAsociacionSonidosFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoJuegoAsociacionSonidosFragment)
            .addToBackStack(null)
            .commit()
    }


    /* Sobrescribo la funcion de PantallaPrincipalFragment que uso como interfaz para saber que se
        ha pulsado el boton del modulo de ajustes. Al ser llamada, monto
        el modulo ajustes */
    override fun moduloAjustesSeleccionado() {
        Log.i(TAG, "Montando modulo ajustes")
        val fragmentoAjustes = //SettingsFragment()
            AjustesFragment.newInstance(mainActivityViewModel.getOpcionChicoElegida)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
        Log.i(TAG, "Montando modulo Elijo mi peinado Mis Cortes De Pelo")
        val fragmentoElijoMiPeinadoMisCortesDePelo= ElijoMiPeinadoMisCortesDePeloFragment
            .newInstance(mainActivityViewModel.listaAvataresGuardados, mainActivityViewModel.numeroAvataresGuardadosEnLista())

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoElijoMiPeinadoMisCortesDePelo)
            .addToBackStack(null)
            .commit()
    }

    override fun elijoMiPeinadoMisCortesDePeloTerminado() {
        Log.d(TAG, "Quitando fragmentos de elijoMiPeinado Mis Cortes de Pelo. Vuelta a " +
                "pantalla principal")
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }



    override fun elijoMiPeinadoMontarModuloMiNuevoCorteDePelo() {
        Log.i(TAG, "Montando modulo Elijo mi peinado Mi Nuevo Corte de Pelo Paso 1")
        val fragmentoElijoMiPeinadoNuevoCorteDePeloPaso1 = ElijoMiPeinadoMiNuevoCorteDePeloPaso1Fragment
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
        val fragmentoElijoMiPeinadoNuevoCorteDePeloPaso2 = ElijoMiPeinadoMiNuevoCorteDePeloPaso2Fragment
            .newInstance(mainActivityViewModel.opcionChicoElegida, mainActivityViewModel.opcionPeinadoEscogida)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoElijoMiPeinadoNuevoCorteDePeloPaso2)
            .addToBackStack(null)
            .commit()
    }


    override fun elijoMiPeinadoNuevoCorteDePeloMontarResultado(opcionColor: Int) {
        mainActivityViewModel.opcionColorPeinadoEscogida = opcionColor

        Log.d(TAG, "OpcionChico = ${mainActivityViewModel.opcionChicoElegida}, " +
                "opcionPeinado = ${mainActivityViewModel.opcionPeinadoEscogida}, " +
                "opcionColor = ${mainActivityViewModel.opcionColorPeinadoEscogida}")
        Log.i(TAG, "Montando modulo Elijo mi peinado Mi Nuevo Corte de Pelo Resultado")
        val fragmentoElijoMiPeinadoNuevoCorteDePeloResultado = ElijoMiPeinadoMiNuevoCorteDePeloResultadoFragment
            .newInstance(mainActivityViewModel.opcionChicoElegida, mainActivityViewModel.opcionPeinadoEscogida,
            mainActivityViewModel.opcionColorPeinadoEscogida)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoElijoMiPeinadoNuevoCorteDePeloResultado)
            .addToBackStack(null)
            .commit()
    }

    override fun elijoMiPeinadoNuevoCorteDePeloNuevoAvatarCreado(avatarCreado: Avatar) {
        Log.d(TAG, "Ha llegado a MainActivity el avatar nuevo con opcionChico: " +
                "${avatarCreado.soyChico}, opcionPeinado: ${avatarCreado.opcionPeinado}, " +
                "opcionColor: ${avatarCreado.opcionColor}")
        mainActivityViewModel.guardarAvatarEnLista(avatarCreado)
        guardarCadenaListaAvatares()
        Log.d(TAG, "Terminado modulo ElijoMiPeinado_MiNuevoCorteDePelo. Avatar guardado." +
                " Saliendo")
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    override fun juegoAsociacionSonidosPantallaResultado(acierto: Boolean, objeto: ObjetoSonidoPeluqueria,
                                                         finalDelJuego: Boolean) {
        Log.d(TAG, "Montando pantalla resultado con acierto = $acierto y nombreObjeto = " +
                "${objeto.nombreObjeto} y final del juego = $finalDelJuego"
        )

        val fragmentoJuegoAsociacionSonidosResultadoFragment = JuegoAsociacionSonidosResultadoFragment
            .newInstance(acierto, objeto, finalDelJuego)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoJuegoAsociacionSonidosResultadoFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun juegoAsociacionSonidosTerminado() {
        Log.d(TAG, "Quitando fragmentos de JuegoAsociacionSonidos. Vuelta a " +
                "pantalla principal")
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }







    override fun ajustesCambiarOpcionChicoChica(chicoElegido: Boolean) {
        mainActivityViewModel.opcionChicoElegida = chicoElegido
        guardarOpcionChicoChica()
        Log.i(TAG, "Opcion de chico cambiada en el viewmodel a: ${mainActivityViewModel.opcionChicoElegida}")
    }

    override fun ajustesReiniciarListaAvataresGuardados() {
        mainActivityViewModel.reiniciarListaAvatares()
        guardarCadenaListaAvatares()
        Log.d(TAG, "Lista de avatares reiniciada")
        Toast.makeText(this, R.string.ajustes_personajesBorradosToast, Toast.LENGTH_SHORT)
            .show()
    }

    override fun ajustesMontarGestionCitas() {
        val fragmentoAjustesGestionCitas = AjustesGestionCitasFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAjustesGestionCitas)
            .addToBackStack(null)
            .commit()
    }

    override fun loquesea() {
        Log.d(TAG, "hola")
    }

    override fun ajustesFinalizado() {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
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

    /* Funcion donde guardo en shared preferences la lista de avatares almacenados */
    fun guardarCadenaListaAvatares() {
        val cadena = mainActivityViewModel.convertirListaAvataresACadena()
        Log.d(TAG, "Lista convertida a cadena: $cadena")

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(SHARED_PREFERENCES_LISTA_AVATARES, cadena)
            apply()
        }
        Log.d(TAG, "Guardado en shared preferences cadena de lista de avatares" +
                " = $cadena")
    }

    /* Funcion que recupera la cadena asociada a la lista de avatares */
    fun recuperarCadenaListaAvatares(): String {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        val cadenaGuardada = sharedPref.getString(SHARED_PREFERENCES_LISTA_AVATARES, "0,0,0")
        Log.d(TAG, "Recuperada cadena de lista de avatares: $cadenaGuardada")
        return cadenaGuardada!!
    }

}
