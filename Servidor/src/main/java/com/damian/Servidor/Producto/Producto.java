package com.damian.Servidor.Producto;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

import com.damian.Servidor.Vender.Vender;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "producto_financiero")
public class Producto {
    
    @Id
    @Basic(optional = false)
    @Column(name = "Cod_producto")
    private String codproducto;
    @Column(name = "puntuacion")
    private String puntuacion;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "interes")
    private Integer interes;
    @Column(name = "fecha_devolucion")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoFinanciero")
    private List<Vender> venderList;

}
