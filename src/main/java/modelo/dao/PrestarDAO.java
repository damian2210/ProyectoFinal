/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import javax.swing.JTextArea;
import modelo.vo.Prestar;
import modelo.vo.PrestarPK;
import modelo.vo.Sucursal;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author dami2
 */
public class PrestarDAO {
     public Prestar buscarPrestamo(Session session, PrestarPK pPK) {
        String consulta="from Prestar p where p.prestarPK=:id";
        Query q=session.createQuery(consulta);
        q.setParameter("id",pPK);
        return (Prestar)q.uniqueResult();
    }

    public void insertar(Session session, Prestar p) {
        session.save(p);
    }

    public void modificar(Session session, Prestar p, float cant) {
      p.setCantidad(cant);
        session.update(p);
    }
 public  void cargadatos(Session session, JTextArea ta) throws Exception {
         ta.setText("");
        Prestar p;

        String consulta="Select p from Prestar p";
        Query q=session.createQuery(consulta);
           
        Iterator it=q.list().iterator();
        while (it.hasNext()){
            p=(Prestar) it.next();  
            ta.append("Cod.Sucursal:"+p.getPrestarPK().getCodSucursal()+ " Cod.Sucursal prestadora:"+p.getPrestarPK().getCodSucursalPrestadora()+" Cantidad:"+p.getCantidad()+"\n");
        }
    
    }
    //no se borra por la misma razón que con las ventas,se quieren saber los préstamos realizados
}
