package com.damian.Servidor.Prestar;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prestar")
public class Prestar {
    @EmbeddedId
    protected PrestarPK prestarPK;
    @Column(name = "cantidad")
    private Integer cantidad;
    
}
