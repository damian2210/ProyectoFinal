package com.example.myapplication.bbdd.entidades;

public class Imagen {

    private int id;
    private String rol;

    private byte[] imagen;

    public Imagen(int id, String rol, byte[] imagen) {
        this.id = id;
        this.rol = rol;
        this.imagen = imagen;
    }

    public Imagen(String rol, byte[] imagen) {
        this.rol = rol;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
