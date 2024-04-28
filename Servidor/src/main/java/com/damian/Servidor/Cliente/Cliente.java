package com.damian.Servidor.Cliente;
import java.util.List;

import com.damian.Servidor.Vender.Vender;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private String idCliente;
    @Column(name = "dni")
    private String dni;
    @Column(name = "telefono")
    private Integer telefono;
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Column(name = "num_cuenta")
    private String numCuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Vender> venderList;
}
