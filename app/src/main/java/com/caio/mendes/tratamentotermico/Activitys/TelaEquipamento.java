package com.caio.mendes.tratamentotermico.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.caio.mendes.tratamentotermico.ListarDados.ListarAdapterEquipamento;
import com.caio.mendes.tratamentotermico.ListarDados.ListarEquipamento;
import com.caio.mendes.tratamentotermico.ModuloConexao.ModuloConexao;
import com.caio.mendes.tratamentotermico.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class TelaEquipamento extends AppCompatActivity {

    ModuloConexao moduloConexao = new ModuloConexao();


    ListView ListViewEquipamento;

    ListarAdapterEquipamento listarAdapterEquipamento;

    List<ListarEquipamento> Lista2;

    public static String Equipamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_equipamento);

        ListViewEquipamento = (ListView) findViewById(R.id.ListViewEquipamento);

        Lista2 = new ArrayList<ListarEquipamento>();

        listarAdapterEquipamento = new ListarAdapterEquipamento(TelaEquipamento.this, Lista2);

        ListViewEquipamento.setAdapter(listarAdapterEquipamento);


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



}