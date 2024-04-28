package com.damian.Servidor.Vender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenderRepository extends JpaRepository<Vender,VenderPK>{
    
}
