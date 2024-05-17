package com.example.myapplication.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Objetos.ObjPrestar;
import com.example.myapplication.Objetos.ObjSucursal;
import com.example.myapplication.R;

import java.util.ArrayList;

public class PrestarAdaptador extends ArrayAdapter<ObjPrestar> {
    private ArrayList<ObjPrestar> datos;

    public PrestarAdaptador(Context context, ArrayList<ObjPrestar> datos){
        super(context, R.layout.elementoprestar,datos);
        this.datos=datos;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater mostrado=LayoutInflater.from(getContext());
        View elemento=mostrado.inflate(R.layout.elementoprestar,parent,false);
        TextView sucR=elemento.findViewById(R.id.verPrestPKSucR);
        sucR.setText("Suc.Recibe:"+datos.get(position).getPrestarPK().getCodSucursal());
        TextView sucP=elemento.findViewById(R.id.verPrestPKSucP);
        sucP.setText("Suc.Presta:"+datos.get(position).getPrestarPK().getCodSucursalPrestadora());
        TextView cant=elemento.findViewById(R.id.verPrestCant);
        cant.setText("Cantidad:"+datos.get(position).getCantidad());
        return elemento;
    }
}
