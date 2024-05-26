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
import com.example.myapplication.R;

import java.util.ArrayList;

public class SucursalAdaptador extends ArrayAdapter<ObjSucursal> {
private ArrayList<ObjSucursal> datos;

public SucursalAdaptador(Context context, ArrayList<ObjSucursal> datos){
        super(context,R.layout.elementosucursal,datos);
        this.datos=datos;
        }



@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater mostrado=LayoutInflater.from(getContext());
        View elemento=mostrado.inflate(R.layout.elementosucursal,parent,false);
        TextView codigo=elemento.findViewById(R.id.verCodSuc);
        codigo.setText("Código:"+datos.get(position).getCodSucursal());
        TextView direccion=elemento.findViewById(R.id.verDirSuc);
        direccion.setText(R.string.direccion+":"+datos.get(position).getDireccion());
        TextView tlf=elemento.findViewById(R.id.verTlfSuc);
        tlf.setText("Teléfono:"+datos.get(position).getTelefono());
        return elemento;
        }
        }
