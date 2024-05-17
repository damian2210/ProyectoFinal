package com.example.myapplication.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Objetos.ObjEmpleado;
import com.example.myapplication.Objetos.ObjSucursal;
import com.example.myapplication.R;

import java.util.ArrayList;

public class EmpleadoAdaptador extends ArrayAdapter<ObjEmpleado> {
    private ArrayList<ObjEmpleado> datos;

    public EmpleadoAdaptador(Context context, ArrayList<ObjEmpleado> datos){
        super(context, R.layout.elementoempleado,datos);
        this.datos=datos;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater mostrado=LayoutInflater.from(getContext());
        View elemento=mostrado.inflate(R.layout.elementoempleado,parent,false);
        TextView codigo=elemento.findViewById(R.id.verEmpCod);
        codigo.setText("Código:"+datos.get(position).getCodEmpleado());
        TextView direccion=elemento.findViewById(R.id.verEmpDir);
        direccion.setText("Dirección:"+datos.get(position).getDireccion());
        TextView tlf=elemento.findViewById(R.id.verEmptlf);
        tlf.setText("Teléfono:"+datos.get(position).getTelefono());
        TextView nombre=elemento.findViewById(R.id.verEmpNombre);
        nombre.setText("Nombre:"+datos.get(position).getNombre());
        TextView contra=elemento.findViewById(R.id.verEmpContra);
        contra.setText("Contraseña::"+datos.get(position).getContraseña());
        TextView dni=elemento.findViewById(R.id.verEmpDni);
        dni.setText("Dni:"+datos.get(position).getDni());
        TextView rol=elemento.findViewById(R.id.verEmpRol);
        rol.setText("Rol:"+datos.get(position).getCodEmpleado());
        TextView user=elemento.findViewById(R.id.verEmpUser);
        user.setText("Dirección:"+datos.get(position).getUsuario());

        return elemento;
    }
}
