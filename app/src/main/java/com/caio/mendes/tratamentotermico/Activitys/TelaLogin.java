package com.caio.mendes.tratamentotermico.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.caio.mendes.tratamentotermico.ModuloConexao.ModuloConexao;
import com.caio.mendes.tratamentotermico.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class TelaLogin extends AppCompatActivity {

    ModuloConexao moduloConexao = new ModuloConexao();

    private Button btnLogar;
    private EditText editSenhaLogar;
    public static String API_CLIENTE;
    private String minhasenha;
    public static String Nf_alta;
    public static String Nf_baixa;
    public static String Cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_TratamentoTermico);
        setContentView(R.layout.tela_login);

        btnLogar = (Button) findViewById(R.id.btnLogar);
        editSenhaLogar = (EditText) findViewById(R.id.editSenhaLogar);

        SharedPreferences prefs = getSharedPreferences("MinhaSenha", Context.MODE_PRIVATE);
        minhasenha = prefs.getString("myKey", editSenhaLogar.getText().toString());
        editSenhaLogar.setText(minhasenha);


        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senha = editSenhaLogar.getText().toString();

                if (senha.isEmpty()) {
                    Toast.makeText(TelaLogin.this, "O CAMPO SENHA É OBRIGATÓRIO!",
                            Toast.LENGTH_LONG).show();

                    editSenhaLogar.requestFocus();

                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(TelaLogin.INPUT_METHOD_SERVICE);
                    if (imm.isActive())
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


                } else {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(TelaLogin.INPUT_METHOD_SERVICE);
                    if (imm.isActive())
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    editSenhaLogar.setEnabled(false);

                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {
                        BuscarTokenLogar();

                    } else {
                        Toast.makeText(TelaLogin.this, "Sem conexão de rede", Toast.LENGTH_SHORT).show();
                        // swipe_container.setRefreshing(false);
                    }

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editSenhaLogar.setEnabled(true);
                        }
                    }, 2000);
                }
            }
        });

    }

    private void BuscarTokenLogar() {

        try {
            String URL = moduloConexao.HOST_CLIENTE + "/Logar.php";

            Ion.with(TelaLogin.this)
                    .load(URL)
                    .setBodyParameter("senha_app", editSenhaLogar.getText().toString())
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            if(result != null){

                                String RETORNO = result.get("LOGIN").getAsString();

                                if (RETORNO.equals("ERRO")) {
                                    Toast.makeText(TelaLogin.this, "Usuário não cadastrado no " +
                                            "sistema, contate o Administrador", Toast.LENGTH_LONG).show();

                                } else if (RETORNO.equals("SUCESSO")) {
                                    API_CLIENTE = result.get("API_CLIENTE").getAsString();
                                    Cliente = result.get("CLIENTE").getAsString();
                                    Nf_alta = result.get("NOTIFICACAO_ALTA").getAsString();
                                    Nf_baixa = result.get("NOTIFICACAO_BAIXA").getAsString();


                                    if(editSenhaLogar.getText().toString() != minhasenha){
                                        gravarCondicao();
                                    }


                                    Intent intent = new Intent(TelaLogin.this, TelaTemperatura.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    });

        } catch (Exception erro) {

        }
    }

    public void subscribleTopics() {
        FirebaseMessaging.getInstance().subscribeToTopic("001_teste_alta");
        FirebaseMessaging.getInstance().subscribeToTopic("001_teste_baixa");

    }

    private void gravarCondicao() {

        try {
            SharedPreferences prefs = getSharedPreferences("MinhaSenha", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("myKey", editSenhaLogar.getText().toString());
            editor.apply();

        } catch (Exception e) {

        }

    }



    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}