package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmacionBorrarPersonajesFragment : DialogFragment() {

    interface Callbacks {
        fun confirmacionBorrarPersonajes()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.ajustes_borrarListaAvatares)
            .setMessage(R.string.ajustes_subtituloDialogConfirmacionBorrarAvatares)
            .setPositiveButton(R.string.aceptar, DialogInterface.OnClickListener {
                dialog: DialogInterface?, which: Int ->
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).confirmacionBorrarPersonajes()
                }
            })
            .setNegativeButton(R.string.cancelar, null)
            .create()
    }
}