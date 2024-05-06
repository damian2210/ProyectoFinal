/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;


/**
 *
 * @author dami2
 */
@Entity
@Table(name = "vender")
public class Vender implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VenderPK venderPK;
    @Column(name = "fecha_venta")
    private Date fechaVenta;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "cod_empleado", referencedColumnName = "cod_empleado")
    @ManyToOne
    private Empleado codEmpleado;
    @JoinColumn(name = "cod_producto", referencedColumnName = "cod_producto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ProductoFinanciero productoFinanciero;

    public Vender() {
    }

    public Vender(VenderPK venderPK) {
        this.venderPK = venderPK;
    }

    public Vender(String codProducto, String idCliente) {
        this.venderPK = new VenderPK(codProducto, idCliente);
    }

    public Vender(VenderPK venderPK,Empleado codEmpleado) {
        this.venderPK = venderPK;
        this.fechaVenta = Date.valueOf(LocalDate.now());
        this.codEmpleado = codEmpleado;
    }

    public VenderPK getVenderPK() {
        return venderPK;
    }

    public void setVenderPK(VenderPK venderPK) {
        this.venderPK = venderPK;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(Empleado codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public ProductoFinanciero getProductoFinanciero() {
        return productoFinanciero;
    }

    public void setProductoFinanciero(ProductoFinanciero productoFinanciero) {
        this.productoFinanciero = productoFinanciero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (venderPK != null ? venderPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vender)) {
            return false;
        }
        Vender other = (Vender) object;
        if ((this.venderPK == null && other.venderPK != null) || (this.venderPK != null && !this.venderPK.equals(other.venderPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.Vender[ venderPK=" + venderPK + " ]";
    }
    
}
