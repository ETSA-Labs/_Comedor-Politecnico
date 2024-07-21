package com.espoch.comedor.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espoch.comedor.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DetallesPago extends AppCompatActivity {

    TextView txtId, txtEstatus, txtMonto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalles_pago);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Referencia los componentes de vista
        txtId = (TextView)findViewById(R.id.txtId) ;
        txtEstatus = (TextView)findViewById(R.id.txtEstatus);
        txtMonto = (TextView)findViewById(R.id.txtMonto);

        //Recibir datos del intent activity de ConfPaypal
        try {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("PaymentDetails"));
            verDetalles(jsonObject.getJSONObject("response"), getIntent().getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void verDetalles(JSONObject response, String monto) {
        //Seteo de los parametros  a los TextViews
        try {
            txtId.setText(response.getString("id"));
            txtEstatus.setText(response.getString("state"));
            txtMonto.setText("$"+ monto );

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}