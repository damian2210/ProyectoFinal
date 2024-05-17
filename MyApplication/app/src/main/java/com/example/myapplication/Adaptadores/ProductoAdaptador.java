package com.example.myapplication.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Objetos.ObjProducto;
import com.example.myapplication.Objetos.ObjSucursal;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ProductoAdaptador extends ArrayAdapter<ObjProducto> {
    private ArrayList<ObjProducto> datos;

    public ProductoAdaptador(Context context, ArrayList<ObjProducto> datos){
        super(context, R.layout.elementoproducto,datos);
        this.datos=datos;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater mostrado=LayoutInflater.from(getContext());
        View elemento=mostrado.inflate(R.layout.elementoproducto,parent,false);
        TextView codigo=elemento.findViewById(R.id.verProCod);
        codigo.setText("Código:"+datos.get(position).getCodproducto());
        TextView tipo=elemento.findViewById(R.id.verProdTipo);
        tipo.setText("Tipo:"+datos.get(position).getTipo());
        TextView fecha=elemento.findViewById(R.id.verProFecha);
        String fechaDev=datos.get(position).getFechaDevolucion().toString();
        if(!fechaDev.isEmpty()&&fechaDev!=null){
            fecha.setText("Teléfono:"+fechaDev);
        }else{
            fecha.setText("");
        }
        TextView interes=elemento.findViewById(R.id.verProInt);
        interes.setText("Interes:"+datos.get(position).getInteres());
        TextView punt=elemento.findViewById(R.id.verProPunt);
        punt.setText("Puntuación:"+datos.get(position).getPuntuacion());

        return elemento;
    }
}
