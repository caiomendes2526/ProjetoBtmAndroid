package com.caio.mendes.projetobtmandroid.ListarDados;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.caio.mendes.projetobtmandroid.R;

import java.util.List;

public class ListarAdapterDadosVelocidade extends BaseAdapter {

    private Context ctx2;
    private List<ListarDadosVelocidade> Lista2;

    public ListarAdapterDadosVelocidade(Context ctx3, List<ListarDadosVelocidade> Lista3){
        ctx2 = ctx3;
        Lista2 = Lista3;
    }

    @Override
    public int getCount() {
        return Lista2.size();
    }

    @Override
    public ListarDadosVelocidade getItem(int position) {
        return Lista2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        if(convertView == null){
            LayoutInflater inflater = ((Activity)ctx2).getLayoutInflater();
            v = inflater.inflate(R.layout.item_lista_dados_velocidade, null);

        }
        else{
            v = convertView;
        }
        ListarDadosVelocidade c = getItem(position);


        TextView itemEquipamento = (TextView) v.findViewById(R.id.itemEquipamento);
        TextView itemVelocidade = (TextView) v.findViewById(R.id.itemVelocidade);
        TextView itemData = (TextView) v.findViewById(R.id.itemData);

        itemEquipamento.setText(c.getEquipamento());
        itemVelocidade.setText(c.getVelocidade());
        itemData.setText(c.getData());



        return v;
    }
}
