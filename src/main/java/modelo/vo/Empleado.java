/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author dami2
 */
@Entity
@Table(name = "empleado")

public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_empleado")
    private String codEmpleado;
    @Column(name = "contra")
    private String contraseña;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "dni")
    private String dni;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "rol")
    private String rol;
    @Column(name = "telefono")
    private Integer telefono;
    @Column(name = "usuario")
    private String usuario;
    @OneToMany(mappedBy = "codEmpleado")
    private List<Vender> venderList;

    public Empleado() {
    }

    public Empleado(String codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public Empleado(String codEmpleado, String contraseña, String dni, String nombre, String rol, String usuario) {
        this.codEmpleado = codEmpleado;
        this.contraseña = contraseña;
        this.direccion="Direccion";
        this.telefono=655380938;
        this.dni = dni;
        this.nombre = nombre;
        this.rol = rol;
        this.usuario = usuario;
    }

    public String getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(String codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Vender> getVenderList() {
        return venderList;
    }

    public void setVenderList(List<Vender> venderList) {
        this.venderList = venderList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpleado != null ? codEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.codEmpleado == null && other.codEmpleado != null) || (this.codEmpleado != null && !this.codEmpleado.equals(other.codEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre+"("+codEmpleado+")";
    }
    
}
