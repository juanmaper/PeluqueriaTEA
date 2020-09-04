package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_5

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R

private const val TAG = "ConfirmacionSustituirCita"

class ConfirmacionSustituirCitaActual : DialogFragment(){

    interface Callbacks {
        fun confirmacionSustituirCita()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.ajustes_sustituirCita)
            .setMessage(R.string.ajustes_subtituloDialogConfirmacionSustituirCita)
            .setPositiveButton(R.string.aceptar, DialogInterface.OnClickListener {
                    dialog: DialogInterface?, which: Int ->
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).confirmacionSustituirCita()
                }
            })
            .setNegativeButton(R.string.cancelar, null)
            .create()
    }
}