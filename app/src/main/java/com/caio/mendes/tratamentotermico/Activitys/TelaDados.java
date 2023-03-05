package com.caio.mendes.tratamentotermico.Activitys;

import static com.caio.mendes.tratamentotermico.Activitys.TelaTemperatura.Sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.caio.mendes.tratamentotermico.Classes.MaskEditUtil;
import com.caio.mendes.tratamentotermico.ListarDados.ListarAdapterDados;
import com.caio.mendes.tratamentotermico.ListarDados.ListarDados;
import com.caio.mendes.tratamentotermico.ModuloConexao.ModuloConexao;
import com.caio.mendes.tratamentotermico.R;
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

public class TelaDados extends AppCompatActivity {

    ListView ListViewDados;

    ModuloConexao moduloConexao = new ModuloConexao();

    ListarAdapterDados listarAdapterDados;

    List<ListarDados> Lista2;

    private TextView txtDataInicial;
    private TextView txtDataFinal;
    private Button btnBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TratamentoTermico);
        setContentView(R.layout.tela_dados);


        ListViewDados = (ListView) findViewById(R.id.ListViewDados);

        Lista2 = new ArrayList<ListarDados>();

        listarAdapterDados = new ListarAdapterDados(TelaDados.this, Lista2);

        ListViewDados.setAdapter(listarAdapterDados);

        txtDataInicial = (TextView) findViewById(R.id.txtDataInicial);
        txtDataFinal = (TextView) findViewById(R.id.txtDataFinal);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);

        txtDataInicial.addTextChangedListener(MaskEditUtil.mask((EditText) txtDataInicial,
                MaskEditUtil.FORMAT_DATE));
        txtDataFinal.addTextChangedListener(MaskEditUtil.mask((EditText) txtDataFinal,
                MaskEditUtil.FORMAT_DATE));

        data();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListarDadosZona();
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(TelaLogin.INPUT_METHOD_SERVICE);
                if (imm.isActive())
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

    }

    private void dataFinal() {
        try {

            Locale locale = new Locale("pt", "BR");
            GregorianCalendar calendar = new GregorianCalendar();
            SimpleDateFormat formatador = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            }
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            txtDataInicial.setText(formatador.format(calendar.getTime()));

                    ListarDadosZona();


        } catch (Exception e) {

        }
    }

    private void data() {
        try {

            Locale locale = new Locale("pt", "BR");
            GregorianCalendar calendar = new GregorianCalendar();
            SimpleDateFormat formatador = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            }
            calendar.add(Calendar.HOUR, 3);
            txtDataFinal.setText(formatador.format(calendar.getTime()));
            dataFinal();


        } catch (Exception e) {

        }
    }

    /*
    private void ListarDados() {
        try {
            Lista2.clear();
            String url = moduloConexao.HOST + "/ListarDados.php";
            Ion.with(getBaseContext())
                    .load(url)
                    .setBodyParameter("data", txtDataInicial.getText().toString())
                    .setBodyParameter("dataf", txtDataFinal.getText().toString())
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {

                            for (int i = 0; i < result.size(); i++) {

                                JsonObject obj = result.get(i).getAsJsonObject();

                                ListarDados c = new ListarDados();

                                c.setSensor(obj.get("sensor").getAsString());
                                c.setTemperatura(obj.get("temp").getAsString() + " ºC");
                                c.setData(obj.get("data_hora").getAsString());

                                Lista2.add(c);
                            }
                            listarAdapterDados.notifyDataSetChanged();
                        }
                    });
        } catch (Exception erro) {

        }
    }
    */

    private void ListarDadosZona() {
        try {
            Lista2.clear();
            String url = moduloConexao.HOST + "/ListarDadosZona.php";
            Ion.with(getBaseContext())
                    .load(url)
                    .setBodyParameter("sensor", Sensores)
                    .setBodyParameter("data", txtDataInicial.getText().toString())
                    .setBodyParameter("dataf", txtDataFinal.getText().toString())
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {

                            if (result != null) {

                                for (int i = 0; i < result.size(); i++) {

                                    JsonObject obj = result.get(i).getAsJsonObject();

                                    ListarDados c = new ListarDados();

                                    if (!obj.get("sensor").isJsonNull()) {
                                        c.setSensor(obj.get("sensor").getAsString());
                                    }
                                    if (!obj.get("temp").isJsonNull()) {
                                        c.setTemperatura(obj.get("temp").getAsString() + " ºC");
                                    }
                                    if (!obj.get("resultado").isJsonNull()) {
                                        c.setData(obj.get("resultado").getAsString());
                                    }

                                    Lista2.add(c);
                                }
                                listarAdapterDados.notifyDataSetChanged();
                            }
                        }
                    });
        } catch (Exception erro) {

        }
    }

}