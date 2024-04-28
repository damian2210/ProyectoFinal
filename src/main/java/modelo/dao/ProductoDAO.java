/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import modelo.vo.ProductoFinanciero;
import modelo.vo.Sucursal;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author dami2
 */
public class ProductoDAO {

    public void cargacombo(Session session, DefaultComboBoxModel modelocombo) {
        Query q = session.createQuery("from ProductoFinanciero p");

        List<ProductoFinanciero> listaProductos = q.list(); 
        Iterator it = listaProductos.iterator();

        while (it.hasNext()) {
            modelocombo.addElement(it.next());
        }
    }
    
     public ProductoFinanciero buscarProducto(Session session, String cod) {
        String consulta="from ProductoFinanciero p where p.cod_producto=:id";
        Query q=session.createQuery(consulta);
        q.setParameter("id",cod);
        return (ProductoFinanciero)q.uniqueResult();
    }

    public void insertar(Session session, ProductoFinanciero e) {
        session.save(e);
    }


    
      public void modificar(Session session, ProductoFinanciero p, float interes, String puntuacion,String tipo,Date fechaD) {
       p.setInteres(interes);
       p.setPuntuacion(puntuacion);
       p.setTipo(tipo);
       p.setFechaDevolucion(fechaD);
        session.update(p);
    }

    public void borrar(Session session, ProductoFinanciero p) {//no se puede borrar si esta en una venta,queremos saber las ventas realizadas

        session.delete(p);
    }
    
    public  void cargadatos(Session session, JTextArea ta) throws Exception {
        ta.setText("");
        ProductoFinanciero p;

        String consulta="Select p from ProductoFinanciero p";
        Query q=session.createQuery(consulta);
           
        Iterator it=q.list().iterator();
        while (it.hasNext()){
            p=(ProductoFinanciero) it.next();
            String fecha;
            if(p.getFechaDevolucion().toString()==null){
                fecha="No hay";
            }else{
                fecha=p.getFechaDevolucion().toString();
            }
            ta.append("Cod.Producto:"+p.getCodProducto()+ " Puntuación:"+p.getPuntuacion()+" Tipo:"+p.getTipo()+" Interés:"+p.getInteres()+" Fecha de devolución:"+fecha+"\n");
        }
    
    }
    
}
