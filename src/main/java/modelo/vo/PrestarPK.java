/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author dami2
 */
@Embeddable
public class PrestarPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_sucursal")
    private String codSucursal;
    @Basic(optional = false)
    @Column(name = "cod_sucursal_prestadora")
    private String codSucursalPrestadora;

    public PrestarPK() {
    }

    public PrestarPK(String codSucursal, String codSucursalPrestadora) {
        this.codSucursal = codSucursal;
        this.codSucursalPrestadora = codSucursalPrestadora;
    }

    public String getCodSucursal() {
        return codSucursal;
    }

    public void setCodSucursal(String codSucursal) {
        this.codSucursal = codSucursal;
    }

    public String getCodSucursalPrestadora() {
        return codSucursalPrestadora;
    }

    public void setCodSucursalPrestadora(String codSucursalPrestadora) {
        this.codSucursalPrestadora = codSucursalPrestadora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSucursal != null ? codSucursal.hashCode() : 0);
        hash += (codSucursalPrestadora != null ? codSucursalPrestadora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrestarPK)) {
            return false;
        }
        PrestarPK other = (PrestarPK) object;
        if ((this.codSucursal == null && other.codSucursal != null) || (this.codSucursal != null && !this.codSucursal.equals(other.codSucursal))) {
            return false;
        }
        if ((this.codSucursalPrestadora == null && other.codSucursalPrestadora != null) || (this.codSucursalPrestadora != null && !this.codSucursalPrestadora.equals(other.codSucursalPrestadora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.PrestarPK[ codSucursal=" + codSucursal + ", codSucursalPrestadora=" + codSucursalPrestadora + " ]";
    }
    
}
