package com.example.myapplication.Objetos;

public class VenderPK {
    private String codproducto;

    private String idCliente;

    public VenderPK(String codproducto, String idCliente) {
        this.codproducto = codproducto;
        this.idCliente = idCliente;
    }

    public String getCodproducto() {
        return codproducto;
    }

    public void setCodproducto(String codproducto) {
        this.codproducto = codproducto;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
