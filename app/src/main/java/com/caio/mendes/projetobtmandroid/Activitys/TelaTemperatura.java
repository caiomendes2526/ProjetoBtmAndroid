package com.caio.mendes.projetobtmandroid.Activitys;

import static com.caio.mendes.projetobtmandroid.Activitys.TelaEquipamento.Equipamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.caio.mendes.projetobtmandroid.ListarDados.ListarAdapterUltimaTemp;
import com.caio.mendes.projetobtmandroid.ListarDados.ListarUltimaTemp;
import com.caio.mendes.projetobtmandroid.ModuloConexao.ModuloConexao;
import com.caio.mendes.projetobtmandroid.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class TelaTemperatura extends AppCompatActivity {

    ModuloConexao moduloConexao = new ModuloConexao();

    ListView ListViewUltimaTemp;

    ListarAdapterUltimaTemp listarAdapterUltimaTemp;

    List<ListarUltimaTemp> Lista2;

    Handler handler = new Handler();

    boolean statusRecebimento = true;

    public static String Sensores = null;


    private String DataInicial;
    private String DataFinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.tela_temperatura);

        //  handler.postDelayed(atualizaStatus, 0);

        ListViewUltimaTemp = (ListView) findViewById(R.id.ListViewUltimaTemp);

        Lista2 = new ArrayList<ListarUltimaTemp>();

        listarAdapterUltimaTemp = new ListarAdapterUltimaTemp(TelaTemperatura.this, Lista2);

        ListViewUltimaTemp.setAdapter(listarAdapterUltimaTemp);


        ListarUltimaTemp();

        // metodo para setar itens de uma lista
        ListViewUltimaTemp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                ListarUltimaTemp setando =
                        (ListarUltimaTemp) adapterView.getAdapter().getItem(position);

                Sensores = (String.valueOf(setando.getSensor()));
                System.out.println(Sensores);
                Intent intent = new Intent(TelaTemperatura.this, TelaPrincipal.class);
                startActivity(intent);
            }

        });

    }

    private void ListarUltimaTemp() {
        try {
            Lista2.clear();
            String url = moduloConexao.HOST + "/ListarUltimaTemp.php";
            Ion.with(getBaseContext())
                    .load(url)
                    .setBodyParameter("equipamento", Equipamento)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {

                            if (result != null) {

                                for (int i = 0; i < result.size(); i++) {

                                    JsonObject obj = result.get(i).getAsJsonObject();

                                    ListarUltimaTemp c = new ListarUltimaTemp();

                                    if (!obj.get("sensor").isJsonNull()) {
                                        c.setSesor(obj.get("sensor").getAsString());
                                    }
                                    if (!obj.get("temp").isJsonNull()) {
                                        c.setTemperatura(obj.get("temp").getAsString() + " ºC");
                                    }
                                    if (!obj.get("resultado").isJsonNull()) {
                                        c.setData(obj.get("resultado").getAsString());
                                    }

                                    Lista2.add(c);
                                    dataInicial();
                                }
                                listarAdapterUltimaTemp.notifyDataSetChanged();
                            }
                        }
                    });
        } catch (Exception erro) {

        }
    }

    private void dataInicial() {
        try {

            Locale locale = new Locale("pt", "BR");
            GregorianCalendar calendar = new GregorianCalendar();
            SimpleDateFormat formatador = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            }
            calendar.add(Calendar.HOUR, 1);
            DataInicial = (formatador.format(calendar.getTime()));
            dataSistema();

        } catch (Exception e) {

        }
    }

    // atenção devido ao atraso na hora do bd ( a hora inicial foi calculada com 1 hora a mais e
    // a final com 3 horas a mais)
    // por tanto o tempo é de 2 horas
    private void dataSistema() {
        try {

            Locale locale = new Locale("pt", "BR");
            GregorianCalendar calendar = new GregorianCalendar();
            SimpleDateFormat formatador = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            }
            calendar.add(Calendar.HOUR, 3);
            DataFinal = (formatador.format(calendar.getTime()));
          //  buscar_temp_max();
        } catch (Exception e) {

        }
    }


}

// Método que atualiza o aplicativo sozinho
   /* private Runnable atualizaStatus = new Runnable() {
        @Override
        public void run() {

            if(statusRecebimento){
                buscar_data_inicial();
                handler.postDelayed(this, 7000);
                Log.d("Status", "Solicitado");
            } else{
                handler.removeCallbacks(atualizaStatus);
                Log.d("Status", "Finalizado");
            }

        }
    };

    */