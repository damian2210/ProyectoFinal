package com.example.myapplication.Objetos;

import java.sql.Date;

public class ObjProducto {
    private String codproducto;

    private String puntuacion;

    private String tipo;

    private Integer interes;

    private Date fechaDevolucion;

    public ObjProducto(String codproducto, String puntuacion, String tipo, Integer interes, Date fechaDevolucion) {
        this.codproducto = codproducto;
        this.puntuacion = puntuacion;
        this.tipo = tipo;
        this.interes = interes;
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getCodproducto() {
        return codproducto;
    }
    public void setCodproducto(String codproducto) {
        this.codproducto = codproducto;
    }
    public String getPuntuacion() {
        return puntuacion;
    }
    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Integer getInteres() {
        return interes;
    }
    public void setInteres(Integer interes) {
        this.interes = interes;
    }
    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }
    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

}
