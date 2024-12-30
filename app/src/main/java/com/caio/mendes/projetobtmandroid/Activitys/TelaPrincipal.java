package com.caio.mendes.projetobtmandroid.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.caio.mendes.projetobtmandroid.R;

public class TelaPrincipal extends AppCompatActivity {

    private Button btnTemperatura;
    private Button btnAlarme;
    private Button btnOffSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
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