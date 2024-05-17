package com.damian.Servidor.Vender;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;

import com.damian.Servidor.Cliente.Cliente;
import com.damian.Servidor.Empleado.Empleado;
import com.damian.Servidor.Producto.Producto;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vender")
public class Vender {
    @EmbeddedId
    protected VenderPK venderPK;
    @Column(name = "fecha_venta")
    @Temporal(TemporalType.DATE)
    private Date fechaVenta;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "cod_empleado", referencedColumnName = "cod_empleado")
    @ManyToOne
    private Empleado codEmpleado;
    @JoinColumn(name = "Cod_producto", referencedColumnName = "Cod_producto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto productoFinanciero;


    
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



    public Producto getProductoFinanciero() {
        return productoFinanciero;
    }



    public void setProductoFinanciero(Producto productoFinanciero) {
        this.productoFinanciero = productoFinanciero;
    }



    @Override
    public String toString() {
        return "Vender Cod.Cliente"+cliente.getIdCliente() + ", codEmpleado="
        + codEmpleado.getCodEmpleado()+", fechaVenta=" + fechaVenta +", productoFinanciero=" + productoFinanciero.getCodproducto() + "]";
    }

    


}
