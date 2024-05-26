package com.example.myapplication.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Objetos.ObjSucursal;
import com.example.myapplication.Objetos.ObjVender;
import com.example.myapplication.R;

import java.util.ArrayList;

public class VenderAdaptador extends ArrayAdapter<ObjVender> {
    private ArrayList<ObjVender> datos;

    public VenderAdaptador(Context context, ArrayList<ObjVender> datos){
        super(context,R.layout.elementovender,datos);
        this.datos=datos;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater mostrado=LayoutInflater.from(getContext());
        View elemento=mostrado.inflate(R.layout.elementovender,parent,false);
        TextView codProd=elemento.findViewById(R.id.verVendPKPro);
        codProd.setText("Cod.Prod:"+datos.get(position).getVenderPK().getCodproducto());
        TextView idCli=elemento.findViewById(R.id.verVendPKCli);
        idCli.setText("Id.Cliente:"+datos.get(position).getVenderPK().getIdCliente());
        TextView codEmp=elemento.findViewById(R.id.verVendEmp);
        codEmp.setText("Cod.Emp:"+datos.get(position).getCodEmpleado().getCodEmpleado());
        TextView fecha=elemento.findViewById(R.id.verVendFecha);
        fecha.setText(R.string.fechaVent+":"+datos.get(position).getFechaVenta().toString());
        return elemento;
    }
}

