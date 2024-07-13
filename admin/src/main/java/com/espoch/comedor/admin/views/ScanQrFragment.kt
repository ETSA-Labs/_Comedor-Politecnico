package com.espoch.comedor.admin.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.espoch.comedor.admin.databinding.FragmentScanQrBinding

class ScanQrFragment : Fragment() {

    private lateinit var binding: FragmentScanQrBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanQrBinding.inflate(inflater)
        return binding.root
    }
}

/*
package com.espoch.comedor.admin

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.espoch.comedor.R
import me.dm7.barcodescanner.zxing.ZXingScannerView
import javax.xml.transform.Result

class EscanearQRActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var scannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escanear_qr)

        // Verificar el permiso de cámara
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST
            )
        } else {
            initializeQRScanner()
            // Procesar el código QR escaneado
            val scannedData = intent.getStringExtra("SCANNED_DATA")
            if (scannedData != null) {
                when {
                    scannedData.startsWith("MENU:") -> {
                        val menuItem = scannedData.substringAfter("MENU:")
                        // Abrir GestionarMenuActivity con el elemento del menú escaneado
                    }

                    scannedData.startsWith("RESERVA:") -> {
                        val reservationId = scannedData.substringAfter("RESERVA:")
                        // Abrir GestionarReservasActivity con la reserva escaneada


                    }

                    else -> {
                        Toast.makeText(
                            this,
                            "Código QR no reconocido: $scannedData",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
        }
        */
/*else {
            initializeQRScanner()
        }*//*

    }

    private fun initializeQRScanner() {
        scannerView = ZXingScannerView(this)
        findViewById<FrameLayout>(R.id.qrScannerPlaceholder).addView(scannerView)
    }

    override fun onResume() {
        super.onResume()
        if (::scannerView.isInitialized) {
            scannerView.setResultHandler(this)
            scannerView.startCamera()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::scannerView.isInitialized) {
            scannerView.stopCamera()
        }
    }

    override fun handleResult(result: Result) {
        val scannedData = result
        // Procesar el código QR escaneado
        when {
            scannedData.startsWith("MENU:") -> {
                val menuItem = scannedData.substringAfter("MENU:")
                // Abrir GestionarMenuActivity con el elemento del menú escaneado
                val intent = Intent(this, GestionarMenuActivity::class.java)
                intent.putExtra("SCANNED_MENU_ITEM", menuItem)
                startActivity(intent)
            }
            scannedData.startsWith("RESERVA:") -> {
                val reservationId = scannedData.substringAfter("RESERVA:")
                // Abrir GestionarReservasActivity con la reserva escaneada
                val intent = Intent(this, GestionarReservasActivity::class.java)
                intent.putExtra("SCANNED_RESERVATION", reservationId)
                startActivity(intent)
            }
            else -> {
                Toast.makeText(this, "Código QR no reconocido: $scannedData", Toast.LENGTH_LONG).show()
            }
        }

        // Reanudar la vista previa de la cámara
        scannerView.resumeCameraPreview(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeQRScanner()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    companion object{
        private const val CAMERA_PERMISSION_REQUEST = 101
    }
}
*/