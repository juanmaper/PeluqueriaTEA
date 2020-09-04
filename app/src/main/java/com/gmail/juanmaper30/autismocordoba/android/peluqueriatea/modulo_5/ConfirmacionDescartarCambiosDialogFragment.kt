package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_5

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R

private const val TAG = "ConfirmacionDescartarCambios"

class ConfirmacionDescartarCambiosDialogFragment : DialogFragment(){

    interface Callbacks {
        fun confirmacionDescartarCambios()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.ajustes_descartarCambios)
            .setMessage(R.string.ajustes_subtituloDialogConfirmacionDescartarCambios)
            .setPositiveButton(R.string.aceptar, DialogInterface.OnClickListener {
                    dialog: DialogInterface?, which: Int ->
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).confirmacionDescartarCambios()
                }
            })
            .setNegativeButton(R.string.cancelar, null)
            .create()
    }
}