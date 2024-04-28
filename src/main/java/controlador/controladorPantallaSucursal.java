/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import modelo.dao.SucursalDAO;

import org.hibernate.Session;
import vista.PantallaSucursal;

/**
 *
 * @author dami2
 */
public class controladorPantallaSucursal {
       public static Session session;
    public static PantallaSucursal ventana=new PantallaSucursal();
    public static SucursalDAO sucDAO;
     public static DefaultComboBoxModel modelocmbSucR = new DefaultComboBoxModel();
      public static DefaultComboBoxModel modelocmbSucP = new DefaultComboBoxModel();
       

    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
         ventana.getCmbPrestSucPres().setModel(modelocmbSucP);
         ventana.getCmbPrestSucR().setModel(modelocmbSucR);
       
    }

    public static void iniciarSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        //DAOS
        sucDAO=HibernateUtil.getSucursalDAO();
    }

    public static void cerrarSession() {
        session.close();
    }
    
     public static void cargarCb() {
        try {
            session.beginTransaction();
           sucDAO.cargarcombo(session,modelocmbSucR);
           sucDAO.cargarcombo(session,modelocmbSucP);
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(controladorPantallaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
