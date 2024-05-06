/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import static controlador.controladorPantallaProductos.venDAO;
import controlador.factory.HibernateUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.dao.PrestarDAO;
import modelo.dao.SucursalDAO;
import modelo.vo.Empleado;
import modelo.vo.Prestar;
import modelo.vo.PrestarPK;
import modelo.vo.Sucursal;
import modelo.vo.Vender;
import modelo.vo.VenderPK;

import org.hibernate.Session;
import vista.PantallaSucursal;

/**
 *
 * @author dami2
 */
public class controladorPantallaSucursal {

    public static Session session;
    public static PantallaSucursal ventana = new PantallaSucursal();
    public static SucursalDAO sucDAO;
    public static PrestarDAO preDAO;
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
        sucDAO = HibernateUtil.getSucursalDAO();
        preDAO = HibernateUtil.getPrestarDAO();
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void cargarCb() {
        try {
             HibernateUtil.beginTx(session);
            sucDAO.cargarcombo(session, modelocmbSucR);
            sucDAO.cargarcombo(session, modelocmbSucP);
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertarSuc(String codSucursal, String direccion, Integer telefono) {

        try {

             HibernateUtil.beginTx(session);
            Sucursal s = sucDAO.buscarSucursal(session, codSucursal);
            if (s != null) {
                JOptionPane.showMessageDialog(ventana, "Ya existe la sucursal");
                return;
            }
            s = new Sucursal(codSucursal, direccion, telefono);
            sucDAO.insertar(session, s);
             HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Sucursal insertada correctamente");

        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void borrarSuc(String codSucursal) {
        try {
            HibernateUtil.beginTx(session);
            Sucursal s = sucDAO.buscarSucursal(session, codSucursal);
            if (s == null) {
                JOptionPane.showMessageDialog(ventana, "No existe la sucursal");
                return;
            }
            Prestar p = preDAO.buscarSucursalEnPrestamo(session, codSucursal);
            if (p != null) {
                JOptionPane.showMessageDialog(ventana, "La sucursal realiza o recibe un préstamo,no se puede borrar");
                return;
            }
            sucDAO.borrar(session, s);
             HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Sucursal borrada correctamente");
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modificarSuc(String codSucursal, String direccion, Integer telefono) {
        try {
            HibernateUtil.beginTx(session);
            Sucursal s = sucDAO.buscarSucursal(session, codSucursal);
            if (s == null) {
                JOptionPane.showMessageDialog(ventana, "No existe la sucursal");
                return;
            }
            sucDAO.modificar(session, s, direccion, telefono);
             HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Sucursal modificada correctamente");
        } catch (Exception ex) {
           HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargarDatosSuc(String codSucursal, JTextField direccion, JTextField telefono) {
        try {
            HibernateUtil.beginTx(session);
            Sucursal s = sucDAO.buscarSucursal(session, codSucursal);

            if (s != null) {

                direccion.setText(s.getDireccion());
                telefono.setText(s.getTelefono() + "");

            } else {
                direccion.setText("");
                telefono.setText("");
            }
            HibernateUtil.commitTx(session);

        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void VisualizarSuc(JTextArea ta) {
        try {
            HibernateUtil.beginTx(session);
            sucDAO.cargadatos(session, ta);
           HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertarPrest(PrestarPK prestarPK, float cantidad) {
        try {
            HibernateUtil.beginTx(session);
            Prestar p = preDAO.buscarPrestamo(session, prestarPK);
            if (p != null) {
                JOptionPane.showMessageDialog(ventana, "Ya existe el préstamo");
                return;
            }
            p = new Prestar(prestarPK, cantidad);
            preDAO.insertar(session, p);
           HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Préstamo insertado correctamente");

        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modificarPrest(PrestarPK prestarPK, float cantidad) {
        try {
            HibernateUtil.beginTx(session);
             Prestar p = preDAO.buscarPrestamo(session, prestarPK);
            if (p == null) {
                JOptionPane.showMessageDialog(ventana, "No existe el préstamo");
                return;
            }
            preDAO.modificar(session, p, cantidad);
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Préstamo modificado correctamente");
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargarDatosPres(PrestarPK prestarPK, JTextField cantidad) {
        try {

            HibernateUtil.beginTx(session);
           Prestar p = preDAO.buscarPrestamo(session, prestarPK);

            if (p != null) {
                cantidad.setText(p.getCantidad()+"");
            }else{
                 cantidad.setText("");
            }
            HibernateUtil.commitTx(session);

        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void VisualizarPres(JTextArea ta) {
        try {
            HibernateUtil.beginTx(session);
            preDAO.cargadatos(session, ta);
           HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
