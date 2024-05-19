package com.example.myapplication.bbdd.entidades;

public class Ajustes {

    private int id;
    private int tamaño;

    private boolean seleccionado;

    public Ajustes(int id, int tamaño, boolean seleccionado) {
        this.id = id;
        this.tamaño = tamaño;
        this.seleccionado = seleccionado;
    }


    public Ajustes(int tamaño, boolean seleccionado) {
        this.tamaño = tamaño;
        this.seleccionado = seleccionado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public boolean getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
