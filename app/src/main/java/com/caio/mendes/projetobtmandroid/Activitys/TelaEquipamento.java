package com.caio.mendes.projetobtmandroid.Activitys;

import static com.caio.mendes.projetobtmandroid.Activitys.TelaLogin.Nf_alta;
import static com.caio.mendes.projetobtmandroid.Activitys.TelaLogin.Nf_baixa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.caio.mendes.projetobtmandroid.ListarDados.ListarAdapterEquipamento;
import com.caio.mendes.projetobtmandroid.ListarDados.ListarEquipamento;
import com.caio.mendes.projetobtmandroid.ModuloConexao.ModuloConexao;
import com.caio.mendes.projetobtmandroid.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.List;

public class TelaEquipamento extends AppCompatActivity {

    ModuloConexao moduloConexao = new ModuloConexao();

    private String condicao = "0";

    ListView ListViewEquipamento;

    ListarAdapterEquipamento listarAdapterEquipamento;

    List<ListarEquipamento> Lista2;
    ExtendedFloatingActionButton fab_add_equipamento;

    public static String Equipamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_equipamento);

        getSupportActionBar().hide();



        fab_add_equipamento = (ExtendedFloatingActionButton) findViewById(R.id.fab_add_equipamento);

        ListViewEquipamento = (ListView) findViewById(R.id.ListViewEquipamento);

        Lista2 = new ArrayList<ListarEquipamento>();

        listarAdapterEquipamento = new ListarAdapterEquipamento(TelaEquipamento.this, Lista2);

        ListViewEquipamento.setAdapter(listarAdapterEquipamento);

        System.out.println("Nf alta " + Nf_alta );

        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String myValue = prefs.getString("myKey", condicao);
        condicao = myValue;

     //  System.out.println("Minha Váriavel: " + condicao);




        if (condicao.equals("1")) {

            fab_add_equipamento.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.verde)));
            fab_add_equipamento.setTextColor(Color.BLACK);
            fab_add_equipamento.setText("ALARME ATIVADO");

        }
        if (condicao.equals("0")) {
            fab_add_equipamento.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.vermelho)));
            fab_add_equipamento.setTextColor(Color.WHITE);
            fab_add_equipamento.setText("ALARME DESATIVADO");
        }


        // metodo para setar itens de uma lista
        ListViewEquipamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                ListarEquipamento setando =
                        (ListarEquipamento) adapterView.getAdapter().getItem(position);

                Equipamento = (String.valueOf(setando.getEquipamento()));
                System.out.println(Equipamento);
                Intent intent = new Intent(TelaEquipamento.this, TelaEscolha.class);
                startActivity(intent);
            }

        });

        ListarEquipamento();



        fab_add_equipamento.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (Nf_alta != null) {

                    if (condicao.equals("1")) {
                        condicao = "0";
                        gravarCondicao();
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(Nf_alta);
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(Nf_baixa);

                        fab_add_equipamento.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.vermelho)));
                        fab_add_equipamento.setTextColor(Color.WHITE);
                        fab_add_equipamento.setText("ALARME DESATIVADO");

                    } else if (condicao.equals("0")) {
                        condicao = "1";
                        gravarCondicao();
                        FirebaseMessaging.getInstance().subscribeToTopic(Nf_alta);
                        FirebaseMessaging.getInstance().subscribeToTopic(Nf_baixa);
                        fab_add_equipamento.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.verde)));
                        fab_add_equipamento.setTextColor(Color.BLACK);
                        fab_add_equipamento.setText("ALARME ATIVADO");

                    }


                }



                return false;
            }
        });


    }

    private void ListarEquipamento() {
        try {
            Lista2.clear();
            String url = moduloConexao.HOST + "/ListarEquipamento.php";
            Ion.with(getBaseContext())
                    .load(url)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {

                            if (result != null) {

                                for (int i = 0; i < result.size(); i++) {

                                    JsonObject obj = result.get(i).getAsJsonObject();

                                    ListarEquipamento c = new ListarEquipamento();

                                    if (!obj.get("equipamento").isJsonNull()) {
                                        c.setEquipamento(obj.get("equipamento").getAsString());
                                    }

                                    Lista2.add(c);
                                }
                                listarAdapterEquipamento.notifyDataSetChanged();
                            }
                        }
                    });
        } catch (Exception erro) {

        }
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



    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

}