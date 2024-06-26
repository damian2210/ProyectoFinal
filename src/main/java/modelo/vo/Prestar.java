/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dami2
 */
@Entity
@Table(name = "prestar")
@NamedQueries({
    @NamedQuery(name = "Prestar.findAll", query = "SELECT p FROM Prestar p")})
public class Prestar implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrestarPK prestarPK;
    @Column(name = "cantidad")
    private Integer cantidad;
     @JoinColumn(name = "cod_sucursal", referencedColumnName = "cod_sucursal", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sucursal sucursal;
    @JoinColumn(name = "cod_sucursal_prestadora", referencedColumnName = "cod_sucursal", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sucursal sucursalPrest;

    public Prestar() {
    }

    public Prestar(PrestarPK prestarPK) {
        this.prestarPK = prestarPK;
    }

    public Prestar(String codSucursal, String codSucursalPrestadora) {
        this.prestarPK = new PrestarPK(codSucursal, codSucursalPrestadora);
    }

    public Prestar(PrestarPK prestarPK, Integer cantidad) {
        this.prestarPK = prestarPK;
        this.cantidad = cantidad;
    }

    public PrestarPK getPrestarPK() {
        return prestarPK;
    }

    public void setPrestarPK(PrestarPK prestarPK) {
        this.prestarPK = prestarPK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Sucursal getSucursalPrest() {
        return sucursalPrest;
    }

    public void setSucursalPrest(Sucursal sucursalPrest) {
        this.sucursalPrest = sucursalPrest;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prestarPK != null ? prestarPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prestar)) {
            return false;
        }
        Prestar other = (Prestar) object;
        if ((this.prestarPK == null && other.prestarPK != null) || (this.prestarPK != null && !this.prestarPK.equals(other.prestarPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.Prestar[ prestarPK=" + prestarPK + " ]";
    }
    
}
