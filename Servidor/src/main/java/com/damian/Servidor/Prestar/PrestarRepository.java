package com.damian.Servidor.Prestar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestarRepository  extends JpaRepository<Prestar,PrestarPK>{

}
