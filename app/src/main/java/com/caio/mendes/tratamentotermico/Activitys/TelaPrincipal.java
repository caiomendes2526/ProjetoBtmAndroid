package com.caio.mendes.tratamentotermico.Activitys;

import static com.caio.mendes.tratamentotermico.Activitys.TelaLogin.Nf_alta;
import static com.caio.mendes.tratamentotermico.Activitys.TelaLogin.Nf_baixa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.caio.mendes.tratamentotermico.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class TelaPrincipal extends AppCompatActivity {

    private Button btnTemperatura;
    private Button btnAlarme;
    private Button btnOffSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TratamentoTermico);
        setContentView(R.layout.tela_principal);

        btnTemperatura = (Button) findViewById(R.id.btnTemperatura);
        btnAlarme = (Button) findViewById(R.id.btnAlarme);
        btnOffSet = (Button) findViewById(R.id.btnOffSet);




        btnTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaPrincipal.this, TelaDados.class);
                startActivity(intent);

            }
        });


        btnAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alarme = new Intent(TelaPrincipal.this, TelaNovoAlarme.class);
                startActivity(alarme);
            }
        });

        btnOffSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent offset = new Intent(TelaPrincipal.this, TelaOffSet.class);
                startActivity(offset);
            }
        });

    }




}