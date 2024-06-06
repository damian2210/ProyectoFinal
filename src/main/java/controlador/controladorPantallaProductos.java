/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.dao.*;
import modelo.vo.Empleado;
import modelo.vo.ProductoFinanciero;
import modelo.vo.Vender;
import modelo.vo.VenderPK;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import vista.PantallaProductos;

/**
 *
 * @author dami2
 */
public class controladorPantallaProductos {

    public static Session session;
    public static PantallaProductos ventana = new PantallaProductos();
    public static ProductoDAO proDAO;
    public static VenderDAO venDAO;
    public static ClienteDAO cliDAO;
    public static EmpleadoDAO empDAO;
    public static DefaultComboBoxModel modelocmbEmp = new DefaultComboBoxModel();
    public static DefaultComboBoxModel modelocmbProd = new DefaultComboBoxModel();
    public static DefaultComboBoxModel modelocmbCli = new DefaultComboBoxModel();

    public static void iniciar() {

        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getCmbVendCli().setModel(modelocmbCli);
        ventana.getCmbVendEmp().setModel(modelocmbEmp);
        ventana.getCmbVendProd().setModel(modelocmbProd);
    }

    public static void iniciarSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        //DAOS
        proDAO = HibernateUtil.getProductoDAO();
        venDAO = HibernateUtil.getVenderDAO();
        cliDAO = HibernateUtil.getClienteDAO();
        empDAO = HibernateUtil.getEmpleadoDAO();
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void cargarCb() {
        try {
             HibernateUtil.beginTx(session);
            cliDAO.cargacombo(session, modelocmbCli);
            empDAO.cargacombo(session, modelocmbEmp);
            proDAO.cargacombo(session, modelocmbProd);
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertarPro(String codProducto, Date fechaDevolucion, Float interes, String puntuacion, String tipo) {

        try {
            if (!tipo.equals("Financiación")) {
                fechaDevolucion = null;
            }
             HibernateUtil.beginTx(session);
            ProductoFinanciero p = proDAO.buscarProducto(session, codProducto);
            if (p != null) {
                JOptionPane.showMessageDialog(ventana, "Ya existe el producto");
                return;
            }
            p = new ProductoFinanciero(codProducto, fechaDevolucion, interes, puntuacion, tipo);
            proDAO.insertar(session, p);
           HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Producto insertado correctamente");

        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void borrarPro(String codProducto) {
        try {
            HibernateUtil.beginTx(session);
            ProductoFinanciero p = proDAO.buscarProducto(session, codProducto);
            if (p == null) {
                JOptionPane.showMessageDialog(ventana, "No existe el producto");
                return;
            }
            Vender v = venDAO.buscarProductoEnVenta(session, codProducto);
            if (v != null) {
                JOptionPane.showMessageDialog(ventana, "El producto está en ventas,no se puede borrar");
                return;
            }
            proDAO.borrar(session, p);
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Producto borrado correctamente");
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modificarPro(String codProducto, Date fechaDevolucion, Float interes, String puntuacion, String tipo) {
        try {
            if (!tipo.equals("Financiación")) {
                fechaDevolucion = null;
            }
             HibernateUtil.beginTx(session);
            ProductoFinanciero p = proDAO.buscarProducto(session, codProducto);
            if (p == null) {
                JOptionPane.showMessageDialog(ventana, "No existe el producto");
                return;
            }
            proDAO.modificar(session, p, interes, puntuacion, tipo, fechaDevolucion);
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Producto modificado correctamente");
        } catch (Exception ex) {
           HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargarDatosPro(String codProducto, JTextField fechaDevolucion, JTextField interes, JTextField puntuacion, JComboBox tipo) {
        try {
            String fecha;

             HibernateUtil.beginTx(session);
            ProductoFinanciero p = proDAO.buscarProducto(session, codProducto);

            if (p != null) {
                if (!p.getTipo().equals("Financiación")) {
                    fecha = null;
                } else {
                    fecha = p.getFechaDevolucion().toString();
                }
                fechaDevolucion.setText(fecha);
                interes.setText(p.getInteres() + "");
                puntuacion.setText(p.getPuntuacion());
                
                tipo.setSelectedItem(p.getTipo().toString());
            } else {
                fechaDevolucion.setText("");
                interes.setText("");
                puntuacion.setText("");
                tipo.setSelectedItem("");
            }
            HibernateUtil.commitTx(session);

        } catch (Exception ex) {
           HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void VisualizarPro(JTextArea ta) {
        try {
             HibernateUtil.beginTx(session);
            proDAO.cargadatos(session, ta);
           HibernateUtil.commitTx(session);
        } catch (Exception ex) {
          HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
      public static void insertarVend(VenderPK venderPK,Empleado codEmpleado) {
        try {
            HibernateUtil.beginTx(session);
            Vender v = venDAO.buscarVenta(session, venderPK);
            if (v != null) {
                JOptionPane.showMessageDialog(ventana, "Ya existe la venta");
                return;
            }
            v = new Vender(venderPK, codEmpleado);
            venDAO.insertar(session, v);
           HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Venta insertada correctamente");

        } catch (Exception ex) {
           HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public static void modificarVend(VenderPK venderPK,Empleado codEmpleado) {
        try {
             HibernateUtil.beginTx(session);
           Vender v = venDAO.buscarVenta(session, venderPK);
            if (v == null) {
                JOptionPane.showMessageDialog(ventana, "No existe la venta");
                return;
            }
            venDAO.modificar(session, v,codEmpleado);
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Venta modificada correctamente");
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargarDatosVend(VenderPK venderPK, JComboBox codEmpleado) {
        try {

             HibernateUtil.beginTx(session);
            Vender v = venDAO.buscarVenta(session, venderPK);

            if (v != null) {
                 codEmpleado.setSelectedItem(v.getCodEmpleado());
            } 
           HibernateUtil.commitTx(session);

        } catch (Exception ex) {
           HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void VisualizarVend(JTextArea ta) {
        try {
             HibernateUtil.beginTx(session);
            venDAO.cargadatos(session, ta);
             HibernateUtil.commitTx(session);
        } catch (Exception ex) {
           HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   


}
