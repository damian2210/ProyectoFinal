/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import modelo.vo.Cliente;
import modelo.vo.Sucursal;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author dami2
 */
public class ClienteDAO {

    public void cargacombo(Session session, DefaultComboBoxModel modelocombo) {
          
      
        Query q = session.createQuery("from Cliente c");

        List<Cliente> listaClientes = q.list(); 
        Iterator it = listaClientes.iterator();

        while (it.hasNext()) {
            modelocombo.addElement(it.next());
        }
    
    }
    public Cliente buscarCliente(Session session, String id) {
        String consulta="from Cliente c where c.idCliente=:id";
        Query q=session.createQuery(consulta);
        q.setParameter("id",id);
        return (Cliente)q.uniqueResult();
    }

    public void insertar(Session session, Cliente c) {
        session.save(c);
    }

    public void modificar(Session session, Cliente c, String nombre, String dni, Long numCuenta , int tlf) {
        c.setNombre(nombre);
        c.setDni(dni);
        c.setNumCuenta(numCuenta);
        c.setTelefono(tlf);
        session.update(c);
    }

    public void borrar(Session session, Cliente c) {//no se puede borrar si esta en una venta,queremos saber las ventas realizadas

        session.delete(c);
    }
    
    public  void cargadatos(Session session, JTextArea ta) throws Exception {
         ta.setText("");
        Cliente c;

        String consulta="Select c from Cliente c";
        Query q=session.createQuery(consulta);
           
        Iterator it=q.list().iterator();
        while (it.hasNext()){
            c=(Cliente) it.next();  
            ta.append("Id.Cliente:"+c.getIdCliente()+ " Nombre:"+c.getNombre()+" Dni:"+c.getDni()+" Teléfono:"+c.getTelefono()+" Num.Cuenta:"+c.getNumCuenta()+"\n");
        }
    
    }
    
}
