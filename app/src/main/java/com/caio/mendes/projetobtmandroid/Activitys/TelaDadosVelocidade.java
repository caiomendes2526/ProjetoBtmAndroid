package com.caio.mendes.projetobtmandroid.Activitys;

import static com.caio.mendes.projetobtmandroid.Activitys.TelaEquipamento.Equipamento;
import static com.caio.mendes.projetobtmandroid.Activitys.TelaTemperatura.Sensores;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.caio.mendes.projetobtmandroid.Classes.MaskEditUtil;
import com.caio.mendes.projetobtmandroid.ListarDados.ListarAdapterDadosVelocidade;
import com.caio.mendes.projetobtmandroid.ListarDados.ListarDadosVelocidade;
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

public class TelaDadosVelocidade extends AppCompatActivity {

    ListView ListViewDadosVelocidade;

    ModuloConexao moduloConexao = new ModuloConexao();

    ListarAdapterDadosVelocidade listarAdapterDadosVelocidade;

    List<ListarDadosVelocidade> Lista2;

    private TextView txtDataInicial;
    private TextView txtDataFinal;
    private Button btnBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ProjetoInbraferForno);
        setContentView(R.layout.tela_dados_velocidade);


        ListViewDadosVelocidade = (ListView) findViewById(R.id.ListViewDadosVelocidade);

        Lista2 = new ArrayList<ListarDadosVelocidade>();

        listarAdapterDadosVelocidade = new ListarAdapterDadosVelocidade(TelaDadosVelocidade.this, Lista2);

        ListViewDadosVelocidade.setAdapter(listarAdapterDadosVelocidade);

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
                ListarDadosVelocidade();
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

                    ListarDadosVelocidade();


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

    private void ListarDadosVelocidade() {
        try {
            Lista2.clear();
            String url = moduloConexao.HOST + "/ListarDadosVelocidade.php";
            Ion.with(getBaseContext())
                    .load(url)
                    .setBodyParameter("equipamento", Equipamento)
                    .setBodyParameter("data", txtDataInicial.getText().toString())
                    .setBodyParameter("dataf", txtDataFinal.getText().toString())
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {

                            if (result != null) {

                                for (int i = 0; i < result.size(); i++) {

                                    JsonObject obj = result.get(i).getAsJsonObject();

                                    ListarDadosVelocidade c = new ListarDadosVelocidade();

                                    if (!obj.get("equipamento").isJsonNull()) {
                                        c.setEquipamento(obj.get("equipamento").getAsString());
                                    }
                                    if (!obj.get("rpm").isJsonNull()) {
                                        c.setVelocidade(obj.get("rpm").getAsString() + " RPM");
                                    }
                                    if (!obj.get("resultado").isJsonNull()) {
                                        c.setData(obj.get("resultado").getAsString());
                                    }

                                    Lista2.add(c);
                                }
                                listarAdapterDadosVelocidade.notifyDataSetChanged();
                            }
                        }
                    });
        } catch (Exception erro) {

        }
    }

}