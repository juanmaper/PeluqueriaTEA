package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

private const val TAG = "ConfirmacionCalendario"

class ConfirmacionGrabarCitaCalendarioDialogFragment : DialogFragment(){

    interface Callbacks {
        fun confirmacionGrabarCitaCalendario()
        fun cancelarGrabarCitaCalendario()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.ajustes_grabarCitaCalendarioConfirmacion)
            .setMessage(R.string.ajustes_subtituloGrabarCitaCalendarioConfirmacion)
            .setPositiveButton(R.string.aceptar, DialogInterface.OnClickListener {
                    dialog: DialogInterface?, which: Int ->
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).confirmacionGrabarCitaCalendario()
                }
            })
            .setNegativeButton(R.string.cancelar, DialogInterface.OnClickListener {
                    dialog: DialogInterface?, which: Int ->
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).cancelarGrabarCitaCalendario()
                }
            })
            .create()
    }
}