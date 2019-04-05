package com.g214.pita;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Adaptador extends BaseAdapter {
    private Context context;
    private ArrayList<Entidad> listItems;

    public Adaptador(Context context, ArrayList<Entidad> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        Entidad item = (Entidad) getItem(position);
        //Typeface regular=Typeface.createFromAsset(context.getAssets(), "fonts/montserratRegular.otf");

        convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
        TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.tvTitulo);
        TextView tvContenido = (TextView) convertView.findViewById(R.id.tvContenido);
        //tvId.setTypeface(regular);
        //tvTitulo.setTypeface(regular);
        //tvContenido.setTypeface(regular);

        tvTitulo.setVisibility(View.GONE);
        tvId.setVisibility(View.GONE);
        tvId.setText(item.getId());
        tvTitulo.setText(item.getTitulo());
        tvContenido.setText(item.getContenido());

        return convertView;
    }
}
