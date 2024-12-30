package com.caio.mendes.projetobtmandroid.Activitys;

import static com.caio.mendes.projetobtmandroid.Activitys.TelaEquipamento.Equipamento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.caio.mendes.projetobtmandroid.ModuloConexao.ModuloConexao;
import com.caio.mendes.projetobtmandroid.R;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class TelaEscolha extends AppCompatActivity {

    ModuloConexao moduloConexao = new ModuloConexao();

    private Button btnTemperatura;
    private Button btnVelocidade;

    private TextView txtEquipamento;
    private TextView txtData_Hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ProjetoInbraferForno);
        setContentView(R.layout.tela_escolha);

        btnTemperatura = (Button) findViewById(R.id.btnTemperatura);
        btnVelocidade = (Button) findViewById(R.id.btnVelocidade);
        txtEquipamento = (TextView) findViewById(R.id.txtEquipamento);
        txtData_Hora = (TextView) findViewById(R.id.txtData_Hora);

        txtEquipamento.setText(Equipamento);

        BuscarRpm();


        btnTemperatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TelaEscolha.this, TelaTemperatura.class);
                startActivity(intent);

            }
        });

        btnVelocidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TelaEscolha.this, TelaVelocidade.class);
                startActivity(intent);

            }
        });

    }

    private void BuscarRpm() {

        try {
            String URL = moduloConexao.HOST + "/BuscarRpm.php";

            Ion.with(TelaEscolha.this)
                    .load(URL)
                    .setBodyParameter("equipamento", Equipamento)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            if (result != null) {

                                String RETORNO = result.get("QUERY").getAsString();

                                if (RETORNO.equals("ERRO")) {
                                    Toast.makeText(TelaEscolha.this, "Error",
                                            Toast.LENGTH_LONG).show();

                                } else if(RETORNO.equals("SUCESSO")){
                                    btnVelocidade.setText(result.get("RPM").getAsString() + " RPM");
                                    txtData_Hora.setText(result.get("DATA_HORA").getAsString());
                                }
                            }
                        }
                    });

        } catch (Exception erro) {

        }
    }

    }