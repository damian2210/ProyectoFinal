/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dami2
 */
@Entity
@Table(name = "producto_financiero")

public class ProductoFinanciero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_producto")
    private String codProducto;
    @Column(name = "fecha_devolucion")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    @Column(name = "interes")
    private Float interes;
    @Column(name = "puntuacion")
    private String puntuacion;
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoFinanciero")
    private List<Vender> venderList;

    public ProductoFinanciero() {
    }

    public ProductoFinanciero(String codProducto) {
        this.codProducto = codProducto;
    }

    public String getCodProducto() {
        return codProducto;
    }

    public ProductoFinanciero(String codProducto, Date fechaDevolucion, Float interes, String puntuacion, String tipo) {
        this.codProducto = codProducto;
        this.fechaDevolucion = fechaDevolucion;
        this.interes = interes;
        this.puntuacion = puntuacion;
        this.tipo = tipo;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Float getInteres() {
        return interes;
    }

    public void setInteres(Float interes) {
        this.interes = interes;
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

    public List<Vender> getVenderList() {
        return venderList;
    }

    public void setVenderList(List<Vender> venderList) {
        this.venderList = venderList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codProducto != null ? codProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoFinanciero)) {
            return false;
        }
        ProductoFinanciero other = (ProductoFinanciero) object;
        if ((this.codProducto == null && other.codProducto != null) || (this.codProducto != null && !this.codProducto.equals(other.codProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codProducto+"("+tipo+")";
    }
    
}
