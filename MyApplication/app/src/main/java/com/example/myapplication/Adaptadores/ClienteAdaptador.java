package com.example.myapplication.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Objetos.ObjCliente;
import com.example.myapplication.Objetos.ObjSucursal;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ClienteAdaptador extends ArrayAdapter<ObjCliente> {
    private ArrayList<ObjCliente> datos;

    public ClienteAdaptador(Context context, ArrayList<ObjCliente> datos){
        super(context, R.layout.elementocliente,datos);
        this.datos=datos;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater mostrado=LayoutInflater.from(getContext());
        View elemento=mostrado.inflate(R.layout.elementocliente,parent,false);
        TextView codigo=elemento.findViewById(R.id.verCliCod);
        codigo.setText("Código:"+datos.get(position).getIdCliente());
        TextView nombre=elemento.findViewById(R.id.verCliNombre);
        nombre.setText("Nombre:"+datos.get(position).getNombre());
        TextView tlf=elemento.findViewById(R.id.verCliTLF);
        tlf.setText("Teléfono:"+datos.get(position).getTelefono());
        TextView cuenta=elemento.findViewById(R.id.verCliNumCuenta);
        cuenta.setText("Num.Cuenta:"+datos.get(position).getNumCuenta());
        return elemento;
    }
}
