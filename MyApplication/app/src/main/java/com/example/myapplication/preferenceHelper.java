package com.example.myapplication;

import android.content.SharedPreferences;

public class preferenceHelper {
    private SharedPreferences sharPref;


    public preferenceHelper(SharedPreferences sharPref) {

        this.sharPref = sharPref;
    }

    public datos cargar(){
        datos d=new datos();

        d.setContra(sharPref.getString("contraseña","ant"));
        d.setUsuario(sharPref.getString("usuario","Antonio"));
        d.setUrl(sharPref.getString("url","http://10.0.2.2:8080"));

        return d;
    }
    public void guardar(String usuario,String contraseña,String url){
        SharedPreferences.Editor editor=sharPref.edit();
        editor.putString("contraseña",contraseña);
        editor.putString("usuario",usuario);
        editor.putString("url",url);
        editor.commit();
    }

    public datos cargarIdioma(){
        datos d=new datos();

        d.setIdioma(sharPref.getString("idioma",null));


        return d;
    }
    public void guardarIdioma(String idioma){
        SharedPreferences.Editor editor=sharPref.edit();
        editor.putString("idioma",idioma);

        editor.commit();
    }
}
