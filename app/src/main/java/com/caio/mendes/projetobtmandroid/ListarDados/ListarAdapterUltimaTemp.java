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

public class ListarAdapterUltimaTemp extends BaseAdapter {

    private Context ctx2;
    private List<ListarUltimaTemp> Lista2;

    public ListarAdapterUltimaTemp(Context ctx3, List<ListarUltimaTemp> Lista3){
        ctx2 = ctx3;
        Lista2 = Lista3;
    }

    @Override
    public int getCount() {
        return Lista2.size();
    }

    @Override
    public ListarUltimaTemp getItem(int position) {
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
            v = inflater.inflate(R.layout.item_lista_ultima_temp, null);

        }
        else{
            v = convertView;
        }
        ListarUltimaTemp c = getItem(position);


        TextView itemSensor = (TextView) v.findViewById(R.id.itemSensor);
        TextView itemTemperatura = (TextView) v.findViewById(R.id.itemTemperatura);
        TextView itemData = (TextView) v.findViewById(R.id.itemData);


        itemSensor.setText(c.getSensor());
        itemTemperatura.setText(c.getTemperatura());
        itemData.setText(c.getData());



        return v;
    }
}
