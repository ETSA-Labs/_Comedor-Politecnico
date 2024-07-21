package com.espoch.comedor.extensions

import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity

private var winCtrl: WindowInsetsControllerCompat? = null

private fun checkWinCtrl(window: Window) {
    if (com.espoch.comedor.extensions.winCtrl == null)
        com.espoch.comedor.extensions.winCtrl = WindowInsetsControllerCompat(window, window.decorView)
}

var FragmentActivity.isLightStatusBar: Boolean
    get() {
        com.espoch.comedor.extensions.checkWinCtrl(window)

        return com.espoch.comedor.extensions.winCtrl!!.isAppearanceLightStatusBars
    }
    set(value) {
        com.espoch.comedor.extensions.checkWinCtrl(window)

        com.espoch.comedor.extensions.winCtrl!!.isAppearanceLightStatusBars = value

    }