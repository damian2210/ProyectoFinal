package com.example.myapplication;

public class datos {

    private String usuario;
    private String contra;
    private String url;

    public datos(String usuario, String contra, String url) {
        this.usuario = usuario;
        this.contra = contra;
        this.url = url;
    }

    public datos() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
