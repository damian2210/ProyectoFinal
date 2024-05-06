/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import modelo.vo.Sucursal;
import modelo.vo.Vender;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author dami2
 */
public class SucursalDAO {

    public void cargarcombo(Session session, DefaultComboBoxModel modelocombo) {
         Query q = session.createQuery("from Sucursal s");

        List<Sucursal> listaProductos = q.list(); 
        Iterator it = listaProductos.iterator();

        while (it.hasNext()) {
            modelocombo.addElement(it.next());
        }
    }

    public Sucursal buscarSucursal(Session session, String cod) {
        String consulta="from Sucursal s where s.codSucursal=:id";
        Query q=session.createQuery(consulta);
        q.setParameter("id",cod);
        return (Sucursal)q.uniqueResult();
    }

    public void insertar(Session session, Sucursal s) {
        session.save(s);
    }

    public void modificar(Session session, Sucursal s, String dir,int tlf) {
         s.setDireccion(dir);
         s.setTelefono(tlf);
        session.update(s);
    }

    public void borrar(Session session,Sucursal s) {//no se puede borrar si participa en préstamos

        session.delete(s);
    }
     public  void cargadatos(Session session, JTextArea ta) throws Exception {
         ta.setText("");
        Sucursal s;

        String consulta="Select s from Sucursal s";
        Query q=session.createQuery(consulta);
           
        Iterator it=q.list().iterator();
        while (it.hasNext()){
            s=(Sucursal) it.next();  
            ta.append("Cod.Sucursal:"+s.getCodSucursal()+ " Dirección:"+s.getDireccion()+" Teléfono:"+s.getTelefono()+"\n");
        }
    
    }
    
}
