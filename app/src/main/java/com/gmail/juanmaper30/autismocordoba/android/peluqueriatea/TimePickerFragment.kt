package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_FECHA = "fecha"

class TimePickerFragment : DialogFragment() {

    interface Callbacks {
        fun enHoraSeleccionada(fecha: Date)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val fecha = arguments?.getSerializable(ARG_FECHA) as Date
        val calendar = Calendar.getInstance()
        calendar.time = fecha

        val horaListener = TimePickerDialog.OnTimeSetListener {
                _: TimePicker, hora: Int, minutos: Int ->
            calendar.set(Calendar.HOUR_OF_DAY, hora)
            calendar.set(Calendar.MINUTE, minutos)

            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                Toast.makeText(requireContext(), "La hora ha de ser posterior a la actual",
                    Toast.LENGTH_SHORT).show()
            } else {
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).enHoraSeleccionada(calendar.time)
                }
            }

        }

        val horaInicial = calendar.get(Calendar.HOUR_OF_DAY)
        val minutosInicial = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
            requireContext(),
            horaListener,
            horaInicial,
            minutosInicial,
            true
        )
    }



    /* Companion object, se puede llamar a la funcion como si fuese un metodo */
    companion object {
        /* Crea instancias de EleccionFechaFragment, recogiendo el valor que se le haya
        pasado como argumento, es decir, la fecha de la cita pulsada para inicializar el calendario
         */
        fun newInstance(fecha: Date): TimePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_FECHA, fecha)
            }
            return TimePickerFragment().apply {
                arguments = args
            }
        }
    }
}