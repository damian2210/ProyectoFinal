package com.damian.Servidor.Vender;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/vender")
@RequiredArgsConstructor
public class VenderController {
    private final VenderService venderSer;


    @PostMapping
    public void createVender(@RequestBody Vender vender){
            venderSer.createVender(vender);
    }
}
