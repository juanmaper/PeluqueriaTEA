package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_5

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R

private const val TAG = "ConfirmacionGrabarCita"

class ConfirmacionGrabarCitaCalendarioDialogFragment : DialogFragment(){

    interface Callbacks {
        fun confirmacionGrabarCitaCalendario()
        fun cancelarGrabarCitaCalendario()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.ajustes_grabarCitaCalendarioConfirmacion)
            .setMessage(R.string.ajustes_subtituloGrabarCitaCalendarioConfirmacion)
            .setPositiveButton(R.string.si, DialogInterface.OnClickListener {
                    dialog: DialogInterface?, which: Int ->
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).confirmacionGrabarCitaCalendario()
                }
            })
            .setNegativeButton(R.string.no, DialogInterface.OnClickListener {
                    dialog: DialogInterface?, which: Int ->
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).cancelarGrabarCitaCalendario()
                }
            })
            .create()
    }
}