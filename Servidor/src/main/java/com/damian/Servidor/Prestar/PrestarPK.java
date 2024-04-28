package com.damian.Servidor.Prestar;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestarPK {
    @Basic(optional = false)
    @Column(name = "cod_sucursal")
    private String codSucursal;
    @Basic(optional = false)
    @Column(name = "cod_sucursal_prestadora")
    private String codSucursalPrestadora;
}
