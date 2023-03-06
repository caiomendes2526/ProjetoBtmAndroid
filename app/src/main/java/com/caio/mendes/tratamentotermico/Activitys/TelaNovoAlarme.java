package com.caio.mendes.tratamentotermico.Activitys;

import static com.caio.mendes.tratamentotermico.Activitys.TelaTemperatura.Sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.caio.mendes.tratamentotermico.ModuloConexao.ModuloConexao;
import com.caio.mendes.tratamentotermico.R;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class TelaNovoAlarme extends AppCompatActivity {

    ModuloConexao moduloConexao = new ModuloConexao();

    private EditText txtAlarmeAlta;
    private EditText txtAlarmeBaixa;
    private Button btnAlarme;
    private TextView txtSensores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TratamentoTermico);
        setContentView(R.layout.tela_novo_alarme);

        txtAlarmeAlta = (EditText) findViewById(R.id.txtAlarmeAlta);
        txtAlarmeBaixa = (EditText) findViewById(R.id.txtAlarmeBaixa);
        btnAlarme = (Button) findViewById(R.id.btnAlarme);
        txtSensores = (TextView) findViewById(R.id.txtSensores);

        buscar_alarmes();

        txtSensores.setText(Sensores);



        btnAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtAlarmeAlta.getText().toString().isEmpty()) {
                    txtAlarmeAlta.setText("0");
                }
                if (txtAlarmeBaixa.getText().toString().isEmpty()) {
                    txtAlarmeBaixa.setText("0");
                }

                setarAlarme();

            }
        });

    }


    private void alarmes() {

        try {
            String URL = moduloConexao.HOST + "/AlterarAlarmes.php";

            Ion.with(TelaNovoAlarme.this)
                    .load(URL)
                    .setBodyParameter("alta", txtAlarmeAlta.getText().toString())
                    .setBodyParameter("baixa", txtAlarmeBaixa.getText().toString())
                    .setBodyParameter("sensor", Sensores)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            if (result != null) {

                                String RETORNO = result.get("UPDATE").getAsString();

                                if (RETORNO.equals("ERRO")) {
                                    Toast.makeText(TelaNovoAlarme.this, "Erro ao salvar o start",
                                            Toast.LENGTH_LONG).show();

                                } else if (RETORNO.equals("OK")) {
                                    Toast.makeText(TelaNovoAlarme.this, "Alarme alterado com " +
                                                    "sucesso!",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        }
                    });

        } catch (Exception erro) {

        }
    }


    private void buscar_alarmes() {

        try {
            String URL = moduloConexao.HOST + "/BuscandoAlarmes.php";

            Ion.with(TelaNovoAlarme.this)
                    .load(URL)
                    .setBodyParameter("sensor", Sensores)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            if (result != null) {

                                String RETORNO = result.get("QUERY").getAsString();

                                if (RETORNO.equals("ERRO")) {
                                    Toast.makeText(TelaNovoAlarme.this, "ops! Não há dados no " +
                                                    "momento",
                                            Toast.LENGTH_LONG).show();

                                } else if (RETORNO.equals("SUCESSO")) {

                                    txtAlarmeAlta.setText(result.get("ALTA").getAsString());
                                    txtAlarmeBaixa.setText(result.get("BAIXA").getAsString());

                                }
                            }
                        }
                    });

        } catch (Exception erro) {

        }
    }


    private void setarAlarme() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            alarmes();

        } else {
            Toast.makeText(TelaNovoAlarme.this, "Sem conexão de rede", Toast.LENGTH_SHORT).show();
            // swipe_container.setRefreshing(false);
        }
    }


}

