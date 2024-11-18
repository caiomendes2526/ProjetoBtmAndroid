package com.caio.mendes.projetobtmandroid.Activitys;

import static com.caio.mendes.projetobtmandroid.Activitys.TelaEquipamento.Equipamento;
import static com.caio.mendes.projetobtmandroid.Activitys.TelaTemperatura.Sensores;

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

import com.caio.mendes.projetobtmandroid.ModuloConexao.ModuloConexao;
import com.caio.mendes.projetobtmandroid.R;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class TelaOffSet extends AppCompatActivity {

    ModuloConexao moduloConexao = new ModuloConexao();

    private EditText txtOffSet;
    private Button btnOffSet;
    private TextView txtSensores;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.tela_off_set);

        txtSensores = (TextView) findViewById(R.id.txtSensores);
        txtOffSet = (EditText) findViewById(R.id.txtOffSet);
        btnOffSet = (Button) findViewById(R.id.btnOffSet);

        txtSensores.setText(Sensores + " - " + Equipamento);

        buscar_off_set();

        btnOffSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setarOffSet();
            }
        });


    }

    private void buscar_off_set() {

        try {
            String URL = moduloConexao.HOST + "/BuscandoOffSet.php";

            Ion.with(TelaOffSet.this)
                    .load(URL)
                    .setBodyParameter("sensor", Sensores)
                    .setBodyParameter("equipamento", Equipamento)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            if (result != null) {

                                String RETORNO = result.get("QUERY").getAsString();

                                if (RETORNO.equals("ERRO")) {
                                    Toast.makeText(TelaOffSet.this, "ops! Não há dados no " +
                                                    "momento",
                                            Toast.LENGTH_LONG).show();

                                } else if (RETORNO.equals("SUCESSO")) {

                                    txtOffSet.setText(result.get("OFFSET").getAsString());

                                }
                            }
                        }
                    });

        } catch (Exception erro) {

        }
    }

    private void alterar_off_set() {

        try {
            String URL = moduloConexao.HOST + "/AlterarOffSet.php";

            Ion.with(TelaOffSet.this)
                    .load(URL)
                    .setBodyParameter("off_set", txtOffSet.getText().toString())
                    .setBodyParameter("sensor", Sensores)
                    .setBodyParameter("equipamento", Equipamento)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            if (result != null) {

                                String RETORNO = result.get("UPDATE").getAsString();

                                if (RETORNO.equals("ERRO")) {
                                    Toast.makeText(TelaOffSet.this, "Erro ao salvar o start",
                                            Toast.LENGTH_LONG).show();

                                } else if (RETORNO.equals("OK")) {
                                    Toast.makeText(TelaOffSet.this, "Off Set alterado com " +
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

    private void setarOffSet() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            alterar_off_set();

        } else {
            Toast.makeText(TelaOffSet.this, "Sem conexão de rede", Toast.LENGTH_SHORT).show();
            // swipe_container.setRefreshing(false);
        }
    }


}