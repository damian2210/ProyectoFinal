package com.example.myapplication.Objetos;

public class ObjEmpleado {
    private String codEmpleado;

    private String dni;


    private Integer telefono;

    private String rol;

    private String usuario;

    private String contraseña;


    public ObjEmpleado(String codEmpleado, String dni,Integer telefono, String rol, String usuario, String contraseña) {
        this.codEmpleado = codEmpleado;
        this.dni = dni;

        this.telefono = telefono;
        this.rol = rol;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public String getCodEmpleado() {
        return codEmpleado;
    }
    public void setCodEmpleado(String codEmpleado) {
        this.codEmpleado = codEmpleado;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getTelefono() {
        return telefono;
    }
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
