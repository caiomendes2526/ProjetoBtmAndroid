package com.caio.mendes.projetobtmandroid.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.caio.mendes.projetobtmandroid.R;

public class TelaVelocidade extends AppCompatActivity {

    private Button btnVelocidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_velocidade);

        btnVelocidade = (Button) findViewById(R.id.btnVelocidade);


        btnVelocidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent tela = new Intent(TelaVelocidade.this, TelaDadosVelocidade.class);
                startActivity(tela);

            }
        });

    }
}