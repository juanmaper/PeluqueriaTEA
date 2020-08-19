package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_FECHA = "fecha"

class DatePickerFragment : DialogFragment() {

    interface Callbacks {
        fun enFechaSeleccionada(fecha: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val fecha = arguments?.getSerializable(ARG_FECHA) as Date
        val calendar = Calendar.getInstance()
        calendar.time = fecha

        val fechaListener = DatePickerDialog.OnDateSetListener {
                _: DatePicker, ano: Int, mes: Int, dia: Int ->

            calendar.set(Calendar.YEAR, ano)
            calendar.set(Calendar.MONTH, mes)
            calendar.set(Calendar.DAY_OF_MONTH, dia)

            targetFragment?.let { fragment ->
                (fragment as Callbacks).enFechaSeleccionada(calendar.time)
            }
        }

        val anoInicial = calendar.get(Calendar.YEAR)
        val mesInicial = calendar.get(Calendar.MONTH)
        val diaInicial = calendar.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog = DatePickerDialog(
            requireContext(),
            fechaListener,
            anoInicial,
            mesInicial,
            diaInicial
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        return datePickerDialog
    }

    /* Companion object, se puede llamar a la funcion como si fuese un metodo */
    companion object {
        /* Crea instancias de EleccionFechaFragment, recogiendo el valor que se le haya
        pasado como argumento, es decir, la fecha de la cita pulsada para inicializar el calendario
         */
        fun newInstance(fecha: Date): DatePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_FECHA, fecha)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }
}