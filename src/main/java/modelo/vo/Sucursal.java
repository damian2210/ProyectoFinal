/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dami2
 */
@Entity
@Table(name = "sucursal")
@NamedQueries({
    @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s")})
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_sucursal")
    private String codSucursal;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private Integer telefono;

    public Sucursal() {
    }

    public Sucursal(String codSucursal) {
        this.codSucursal = codSucursal;
    }

    public String getCodSucursal() {
        return codSucursal;
    }

    public Sucursal(String codSucursal, String direccion, Integer telefono) {
        this.codSucursal = codSucursal;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public void setCodSucursal(String codSucursal) {
        this.codSucursal = codSucursal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSucursal != null ? codSucursal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.codSucursal == null && other.codSucursal != null) || (this.codSucursal != null && !this.codSucursal.equals(other.codSucursal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codSucursal;
    }
    
}
