package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "AjustesNuevaCita"
private const val ARG_CITA_ACTUAL_ID = "cita_actual_id"
private const val ARG_HAY_CITA_ACTUAL = "hay_cita_actual"
private const val FECHA_DIALOG = "fechaDialog"
private const val REQUEST_FECHA = 0
private const val HORA_DIALOG = "horaDialog"
private const val REQUEST_HORA = 1
private const val DESCARTAR_CAMBIOS_DIALOG = "descartarCambios"
private const val REQUEST_DESCARTAR_CAMBIOS = 3

class AjustesNuevaCitaFragment : Fragment(), DatePickerFragment.Callbacks,
    TimePickerFragment.Callbacks, ConfirmacionDescartarCambiosDialogFragment.Callbacks{

    interface Callbacks {
        fun ajustesEdicionCitasFinalizado()
    }

    private lateinit var idCitaActual: UUID
    private var hayCitaActual = false
    private lateinit var fechaTextView: TextView
    private lateinit var horaTextView: TextView
    private lateinit var comentarioEditText: EditText
    private lateinit var fechaButton: Button
    private lateinit var horaButton: Button
    private lateinit var borrarButton: Button
    private var botonGuardarPulsado: Boolean = false
    private var confirmacionDescartarCambios: Boolean = false

    private var callbacks: Callbacks? = null

    // Me creo el viewmodel para guardar los datos y hacer las consultas a la base de datos
    private val nuevaCitaViewModel: AjustesNuevaCitaViewModel by lazy {
        ViewModelProvider(this).get(AjustesNuevaCitaViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Fragmento $TAG creado")
        setHasOptionsMenu(true)
        idCitaActual = arguments?.getSerializable(ARG_CITA_ACTUAL_ID) as UUID
        hayCitaActual = arguments?.getSerializable(ARG_HAY_CITA_ACTUAL) as Boolean

        /* En los fragmentos no se puede hacer un override a onBackPressed() directamente,
        asi que creo esta callback para sobreescribir el funcionamiento del back button.
         */
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Pulsado boton back")

                    if (!nuevaCitaViewModel.algoHaSidoEditado || botonGuardarPulsado || confirmacionDescartarCambios) {
                        isEnabled = false
                        callbacks?.ajustesEdicionCitasFinalizado()
                        requireActivity().onBackPressed()
                    } else {
                        ConfirmacionDescartarCambiosDialogFragment().apply {
                            setTargetFragment(this@AjustesNuevaCitaFragment, REQUEST_DESCARTAR_CAMBIOS)
                            show(this@AjustesNuevaCitaFragment.requireActivity().supportFragmentManager,
                                DESCARTAR_CAMBIOS_DIALOG)
                        }
                    }
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ajustes_editar_cita, container, false)

        fechaTextView = view.findViewById(R.id.ajustes_citaFechaElegidaTextView)
        horaTextView = view.findViewById(R.id.ajustes_citaHoraElegidaTextView)
        comentarioEditText = view.findViewById(R.id.ajustes_citaComentarioEditText)

        fechaButton = view.findViewById(R.id.ajustes_citaEleccionFechaButton)
        horaButton = view.findViewById(R.id.ajustes_citaEleccionHoraButton)
        borrarButton = view.findViewById(R.id.ajustes_citaBorrarButton)

        borrarButton.isEnabled = false
        borrarButton.visibility = View.INVISIBLE

        actualizarInterfaz()
        return view
    }

    override fun onStart() {
        super.onStart()

        fechaButton.setOnClickListener {
            DatePickerFragment.newInstance(nuevaCitaViewModel.nuevaCitaPeluqueria.fecha).apply {
                setTargetFragment(this@AjustesNuevaCitaFragment, REQUEST_FECHA)
                show(this@AjustesNuevaCitaFragment.requireActivity().supportFragmentManager,
                    FECHA_DIALOG)
            }
        }

        horaButton.setOnClickListener {
            TimePickerFragment.newInstance(nuevaCitaViewModel.nuevaCitaPeluqueria.fecha).apply {
                setTargetFragment(this@AjustesNuevaCitaFragment, REQUEST_HORA)
                show(this@AjustesNuevaCitaFragment.requireActivity().supportFragmentManager,
                    HORA_DIALOG)
            }
        }


        comentarioEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nuevaCitaViewModel.nuevaCitaPeluqueria.comentario = s.toString()

                if (comentarioEditText.hasFocus()) {
                    nuevaCitaViewModel.algoHaSidoEditado = true
                }
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_ajustes_nueva_editar_cita, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.guardar_cita -> {
                if (nuevaCitaViewModel.nuevaCitaPeluqueria.fecha <= Date()) {
                    Toast.makeText(requireContext(), "La hora ha de ser posterior a la actual",
                        Toast.LENGTH_SHORT).show()
                } else {
                    if (hayCitaActual) {
                        nuevaCitaViewModel.borrarCitaActual(idCitaActual)
                    }
                    nuevaCitaViewModel.guardarCitaPeluqueria(nuevaCitaViewModel.nuevaCitaPeluqueria)
                    Toast.makeText(requireActivity(), "Cita creada correctamente", Toast.LENGTH_SHORT)
                        .show()
                    botonGuardarPulsado = true
                    requireActivity().onBackPressed()
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun actualizarInterfaz() {
        fechaTextView.text = SimpleDateFormat("dd-MM-yyyy")
            .format(nuevaCitaViewModel.nuevaCitaPeluqueria.fecha)

        horaTextView.text = DateFormat.getTimeInstance(DateFormat.SHORT)
            .format(nuevaCitaViewModel.nuevaCitaPeluqueria.fecha)

        comentarioEditText.setText(nuevaCitaViewModel.nuevaCitaPeluqueria.comentario)

    }

    override fun enFechaSeleccionada(fecha: Date) {
        nuevaCitaViewModel.nuevaCitaPeluqueria.fecha = fecha
        actualizarInterfaz()
        nuevaCitaViewModel.algoHaSidoEditado = true
    }

    override fun enHoraSeleccionada(fecha: Date) {
        nuevaCitaViewModel.nuevaCitaPeluqueria.fecha = fecha
        actualizarInterfaz()
        nuevaCitaViewModel.algoHaSidoEditado = true
    }


    override fun confirmacionDescartarCambios() {
        callbacks?.ajustesEdicionCitasFinalizado()
        confirmacionDescartarCambios = true
        requireActivity().onBackPressed()
    }

    /* Companion object, se puede llamar a la funcion como si fuese un metodo */
    companion object {
        /* Crea instancias de AjustesNuevaCitaFragment, recogiendo el valor que se le haya
        pasado como argumento, es decir, la ID de la cita actual (0 si no hay) y un bool que indica
        si hay cita actual o no
         */
        fun newInstance(idCitaActual: UUID, hayCitaActual: Boolean): AjustesNuevaCitaFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CITA_ACTUAL_ID, idCitaActual)
                putSerializable(ARG_HAY_CITA_ACTUAL, hayCitaActual)
            }
            return AjustesNuevaCitaFragment().apply {
                arguments = args
            }
        }
    }

}