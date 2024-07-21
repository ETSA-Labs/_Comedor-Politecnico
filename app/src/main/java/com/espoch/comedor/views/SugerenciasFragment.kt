package com.espoch.comedor.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.espoch.comedor.R
import com.espoch.comedor.models.EmailData
import com.espoch.comedor.models.EmailData.*
import com.espoch.comedor.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SugerenciasFragment : Fragment() {

    private lateinit var etSuggestions: EditText
    private lateinit var btnSendSuggestions: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sugerencias, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etSuggestions = view.findViewById(R.id.et_suggestions)
        btnSendSuggestions = view.findViewById(R.id.btn_send_suggestions)

        btnSendSuggestions.setOnClickListener {
            val suggestions = etSuggestions.text.toString()
            if (suggestions.isNotBlank()) {
                sendEmail(suggestions)
            } else {
                Toast.makeText(context, "Por favor, escribe tus sugerencias", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmail(suggestions: String) {
        val emailData = EmailData.default.apply {
            sender = Sender("App Sugerencias", "no-reply@tuapp.com")
            to = listOf(Recipient("proyeclmm.ti@gmail.com", "Administrador"))
            subject = "Nueva Sugerencia"
            htmlContent = "<html><body><p>$suggestions</p></body></html>"
        }

        val retrofitClient = RetrofitClient()
        val apiService = retrofitClient.brevoApiService

        apiService.sendEmail(emailData).enqueue(object : Callback<EmailResponse> {
            override fun onResponse(call: Call<EmailResponse>, response: Response<EmailResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Sugerencia enviada con éxito", Toast.LENGTH_SHORT).show()
                    etSuggestions.text.clear()
                } else {
                    Toast.makeText(context, "Error al enviar la sugerencia", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                Toast.makeText(context, "Fallo al enviar la sugerencia", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
