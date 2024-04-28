package com.damian.Servidor.Vender;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;

import com.damian.Servidor.Cliente.Cliente;
import com.damian.Servidor.Empleado.Empleado;
import com.damian.Servidor.Producto.Producto;

@Data
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

    


}
