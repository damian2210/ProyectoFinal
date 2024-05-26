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
import modelo.vo.Empleado;
import modelo.vo.Sucursal;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author dami2
 */
public class EmpleadoDAO {

    public void cargacombo(Session session, DefaultComboBoxModel modelocombo) {
            Empleado e;
        Query q = session.createQuery("from Empleado e");

        List<Empleado> listaEmpleados = q.list(); 
        Iterator it = listaEmpleados.iterator();

        while (it.hasNext()) {
            modelocombo.addElement(it.next());
        }
    }
    
    public Empleado buscarEmpleado(Session session, String cod) {
        String consulta="from Empleado e where e.codEmpleado=:id";
        Query q=session.createQuery(consulta);
        q.setParameter("id",cod);
        return (Empleado)q.uniqueResult();
    }
    
      public Empleado buscarUsuario(Session session, String usuario) {
        String consulta="from Empleado e where e.usuario=:id";
        Query q=session.createQuery(consulta);
        q.setParameter("id",usuario);
        return (Empleado)q.uniqueResult();
    }

    public void insertar(Session session, Empleado e) {
        session.save(e);
    }

    public void modificar(Session session, Empleado e, String usuario, String contraseña , String dni,String rol) {
        e.setUsuario(usuario);
        e.setContraseña(contraseña);
        e.setDni(dni);
        e.setRol(rol);
        session.update(e);
    }

    public void borrar(Session session,Empleado e) {
 
        session.delete(e);
    }
    
    public  void cargadatos(Session session, JTextArea ta) throws Exception {
         ta.setText("");
        Empleado e;

        String consulta="Select e from Empleado e";
        Query q=session.createQuery(consulta);
           
        Iterator it=q.list().iterator();
        while (it.hasNext()){
            e=(Empleado) it.next();  
            ta.append("Cod.Empleado:"+e.getCodEmpleado()+" Nombre:"+e.getDni()+" Usuario:"+e.getUsuario()+" Contraseña:"+e.getContraseña()+" Rol:"+e.getRol()+"\n");
        }
    
    }
    
}
