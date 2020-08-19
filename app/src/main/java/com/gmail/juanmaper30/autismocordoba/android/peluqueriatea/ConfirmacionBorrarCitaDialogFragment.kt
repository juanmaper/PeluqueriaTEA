package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

private const val TAG = "ConfirmacionBorrarCita"

class ConfirmacionBorrarCitaDialogFragment : DialogFragment(){

    interface Callbacks {
        fun confirmacionBorrarCita()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.ajustes_borrarCita)
            .setMessage(R.string.ajustes_subtituloDialogConfirmacionBorrarCita)
            .setPositiveButton(R.string.aceptar, DialogInterface.OnClickListener {
                    dialog: DialogInterface?, which: Int ->
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).confirmacionBorrarCita()
                }
            })
            .setNegativeButton(R.string.cancelar, null)
            .create()
    }
}