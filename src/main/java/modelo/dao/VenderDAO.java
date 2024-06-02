/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import javax.swing.JTextArea;
import modelo.vo.Empleado;
import modelo.vo.Vender;
import modelo.vo.VenderPK;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author dami2
 */
public class VenderDAO {
     public Vender buscarVenta(Session session, VenderPK vpk) {
        String consulta="from Vender v where v.venderPK.codProducto=:id and v.venderPK.idCliente=:id2";
        Query q=session.createQuery(consulta);
        q.setParameter("id",vpk.getCodProducto());
        q.setParameter("id2",vpk.getIdCliente());
        return (Vender)q.uniqueResult();
    }
     public Vender buscarProductoEnVenta(Session session, String codProd) {
        String consulta="from Vender v where v.venderPK.codProducto=:id";
        Query q=session.createQuery(consulta);
        q.setParameter("id",codProd);
        return (Vender)q.uniqueResult();
    }
      
        public Vender buscarClienteEnVenta(Session session, String codCli) {
        String consulta="from Vender v where v.venderPK.idCliente=:id";
        Query q=session.createQuery(consulta);
        q.setParameter("id",codCli);
        return (Vender)q.uniqueResult();
    }
        
        public Vender buscarEmpEnVenta(Session session, String codEmp) {
        String consulta="from Vender v where v.codEmpleado=:id";
        Query q=session.createQuery(consulta);
        q.setParameter("id",codEmp);
        return (Vender)q.uniqueResult();
    }
     
    public void insertar(Session session, Vender v) {
        session.save(v);
    }

    public void modificar(Session session, Vender v, Empleado codemp) {//al borrar un empleado se pone null el empleado encargado de la venta
      v.setCodEmpleado(codemp);
      session.update(v);
    }
    
     public  void cargadatos(Session session, JTextArea ta) throws Exception {
         ta.setText("");
        Vender v;

        String consulta="Select v from Vender v";
        Query q=session.createQuery(consulta);
           
        Iterator it=q.list().iterator();
        while (it.hasNext()){
            v=(Vender) it.next();  
            ta.append("Cod.Producto:"+v.getVenderPK().getCodProducto() + " Id.Cliente:"+v.getVenderPK().getIdCliente()+" Cod.Empleado:"+v.getCodEmpleado().getCodEmpleado()+" Fecha venta:"+v.getFechaVenta().toString()+"\n");
        }
    
    }
     public void modificarEmpNull(Session session, Empleado codemp) {
          String consulta="from Vender v where v.codEmpleado.codEmpleado=:id";
        Query q=session.createQuery(consulta);
        q.setParameter("id",codemp.getCodEmpleado());
       Iterator it=q.list().iterator();
       while(it.hasNext()){
           Vender v=(Vender)it.next();
           v.setCodEmpleado(null);
           session.update(v);
       }
      
    }
    
    //no se borra para guardar las ventas realizadas
}
