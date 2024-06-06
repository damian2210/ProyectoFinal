package com.damian.Servidor.Vender;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;





@RestController
@RequestMapping("/vender")
@RequiredArgsConstructor
public class VenderController {
    private final VenderService venderSer;
   
    @GetMapping("/listarVentas") 
    public List<Vender> listarVentas(){
        return venderSer.listarVentas();
    }

    @GetMapping("/buscarVenta") 
    public Vender buscarVenta(@RequestParam(value="id",defaultValue = "0") String codProd,@RequestParam(value="id2",defaultValue = "0") String idCli){
        return venderSer.buscarVenta(codProd,idCli);
    }

    @GetMapping("/buscarProductoEnVenta") 
    public Vender buscarProductoEnVenta(@RequestParam(value="id",defaultValue = "0") String id){
        return venderSer.buscarProductoEnVenta(id);
    }

    @GetMapping("/buscarClienteEnVenta") 
    public Vender buscarClienteEnVenta(@RequestParam(value="id",defaultValue = "0") String id){
        return venderSer.buscarClienteEnVenta(id);
    }
    @GetMapping("/buscarEmpEnVenta") 
    public Vender buscarEmpEnVenta(@RequestParam(value="id",defaultValue = "0") String id){
        return venderSer.buscarEmpEnVenta(id);
    }

    @PostMapping("/insertar") 
    public void insertar(@RequestBody Vender venta){
        venderSer.insertar(venta);
    }

    @PutMapping("/modificar") 
    public void modificar(@RequestBody Vender venta){
          venderSer.modificar(venta);
    }

    @PutMapping("/modificarEmpNull") 
    public void modificarEmpNull(@RequestBody String id){
          venderSer.modificarEmpNull(id);
    }
}
