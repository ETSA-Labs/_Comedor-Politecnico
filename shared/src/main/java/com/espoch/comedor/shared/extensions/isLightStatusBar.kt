package com.espoch.comedor.shared.extensions

import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity

private var winCtrl: WindowInsetsControllerCompat? = null

private fun checkWinCtrl(window: Window) {
    if (winCtrl == null)
        winCtrl = WindowInsetsControllerCompat(window, window.decorView)
}

var FragmentActivity.isLightStatusBar: Boolean
    get() {
        checkWinCtrl(window)

        return winCtrl!!.isAppearanceLightStatusBars
    }
    set(value) {
        checkWinCtrl(window)

        winCtrl!!.isAppearanceLightStatusBars = value

    }