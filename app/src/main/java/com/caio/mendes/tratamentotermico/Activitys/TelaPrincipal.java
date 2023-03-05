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
    private Button btnAtivadoDesativado;
    private String condicao = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TratamentoTermico);
        setContentView(R.layout.tela_principal);

        btnTemperatura = (Button) findViewById(R.id.btnTemperatura);
        btnAlarme = (Button) findViewById(R.id.btnAlarme);
        btnAtivadoDesativado = (Button) findViewById(R.id.btnAtivaDessativa);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String myValue = prefs.getString("myKey", condicao);
        condicao = myValue;

        System.out.println("Minha Váriavel: " + condicao);

        if (condicao.equals("1")) {
        //    Imagem.setImageDrawable(getDrawable(R.drawable.cadeadoaberto));
        }
        if (condicao.equals("0")) {
       //     Imagem.setImageDrawable(getDrawable(R.drawable.cadeadofechado));
        }


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

        btnAtivadoDesativado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Nf_alta != null) {

                    if (condicao.equals("1")) {
                     //   Imagem.setImageDrawable(getDrawable(R.drawable.cadeadofechado));
                        condicao = "0";
                        gravarCondicao();
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(Nf_alta);
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(Nf_baixa);
                    } else if (condicao.equals("0")) {
                     //   Imagem.setImageDrawable(getDrawable(R.drawable.cadeadoaberto));
                        condicao = "1";
                        gravarCondicao();
                        FirebaseMessaging.getInstance().subscribeToTopic(Nf_alta);
                        FirebaseMessaging.getInstance().subscribeToTopic(Nf_baixa);
                    }


                }

            }
        });

    }

    private void gravarCondicao() {

        try {
            SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("myKey", condicao);
            editor.apply();


        } catch (Exception e) {

        }
    }


}