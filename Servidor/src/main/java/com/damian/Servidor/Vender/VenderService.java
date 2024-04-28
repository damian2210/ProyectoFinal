package com.damian.Servidor.Vender;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenderService {
    private final VenderRepository venderRep;

    public void createVender(Vender vender){
        venderRep.save(vender);
    }

}
