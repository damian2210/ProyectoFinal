package com.damian.Servidor.Empleado;
import java.util.List;

import com.damian.Servidor.Vender.Vender;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empleado")
public class Empleado {
     @Id
    @Basic(optional = false)
    @Column(name = "cod_empleado")
    private String codEmpleado;
    @Column(name = "dni")
    private String dni;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "telefono")
    private Integer telefono;
    @Column(name = "rol")
    private String rol;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "contrase\u00f1a")
    private String contraseña;
    @OneToMany(mappedBy = "codEmpleado")
    private List<Vender> venderList;

}
