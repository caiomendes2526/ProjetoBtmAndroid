package com.caio.mendes.tratamentotermico.Activitys;

import static com.caio.mendes.tratamentotermico.Activitys.TelaLogin.Nf_alta;
import static com.caio.mendes.tratamentotermico.Activitys.TelaLogin.Nf_baixa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.caio.mendes.tratamentotermico.ListarDados.ListarAdapterEquipamento;
import com.caio.mendes.tratamentotermico.ListarDados.ListarEquipamento;
import com.caio.mendes.tratamentotermico.ModuloConexao.ModuloConexao;
import com.caio.mendes.tratamentotermico.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    FloatingActionButton fab_add_equipamento;

    public static String Equipamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_equipamento);

        fab_add_equipamento = (FloatingActionButton) findViewById(R.id.fab_add_equipamento);

        ListViewEquipamento = (ListView) findViewById(R.id.ListViewEquipamento);

        Lista2 = new ArrayList<ListarEquipamento>();

        listarAdapterEquipamento = new ListarAdapterEquipamento(TelaEquipamento.this, Lista2);

        ListViewEquipamento.setAdapter(listarAdapterEquipamento);

        System.out.println("Nf alta " + Nf_alta );

        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String myValue = prefs.getString("myKey", condicao);
        condicao = myValue;

        System.out.println("Minha Váriavel: " + condicao);

        if (condicao.equals("1")) {

            fab_add_equipamento.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.verde)));
            fab_add_equipamento.setImageResource(R.drawable.alarmeativado);
        }
        if (condicao.equals("0")) {
            fab_add_equipamento.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.vermelho)));
            fab_add_equipamento.setImageResource(R.drawable.alarmedesativado);
        }


        // metodo para setar itens de uma lista
        ListViewEquipamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                ListarEquipamento setando =
                        (ListarEquipamento) adapterView.getAdapter().getItem(position);

                Equipamento = (String.valueOf(setando.getEquipamento()));
                System.out.println(Equipamento);
                Intent intent = new Intent(TelaEquipamento.this, TelaTemperatura.class);
                startActivity(intent);
            }

        });

        ListarEquipamento();


        fab_add_equipamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });

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
                        fab_add_equipamento.setImageResource(R.drawable.alarmedesativado);
                        Toast.makeText(TelaEquipamento.this, "ALARMES DESATIVADOS",
                                Toast.LENGTH_LONG).show();

                    } else if (condicao.equals("0")) {
                        condicao = "1";
                        gravarCondicao();
                        FirebaseMessaging.getInstance().subscribeToTopic(Nf_alta);
                        FirebaseMessaging.getInstance().subscribeToTopic(Nf_baixa);
                        fab_add_equipamento.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.verde)));
                        fab_add_equipamento.setImageResource(R.drawable.alarmeativado);
                        Toast.makeText(TelaEquipamento.this, "ALARMES ATIVADOS",
                                Toast.LENGTH_LONG).show();

                    }


                }



                return false;
            }
        });

        fab_add_equipamento.setSize(FloatingActionButton.SIZE_AUTO);


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