package com.caio.mendes.projetobtmandroid.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.caio.mendes.projetobtmandroid.R;


public class TelaEscolha extends AppCompatActivity {


    private Button btnTemperatura;
    private Button btnVelocidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ProjetoInbraferForno);
        setContentView(R.layout.tela_escolha);

        btnTemperatura = (Button) findViewById(R.id.btnTemperatura);

        btnTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TelaEscolha.this, TelaTemperatura.class);
                startActivity(intent);

            }
        });


    }

    }