package com.damian.Servidor.Prestar;
import com.damian.Servidor.Sucursal.Sucursal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prestar")
public class Prestar {
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


    
}
