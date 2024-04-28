package com.damian.Servidor.Vender;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenderPK {
    @Basic(optional = false)
    @Column(name = "Cod_producto")
    private String codproducto;
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private String idCliente;
}
