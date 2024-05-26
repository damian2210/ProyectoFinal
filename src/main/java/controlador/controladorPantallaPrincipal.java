/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.dao.ClienteDAO;
import modelo.dao.EmpleadoDAO;
import modelo.dao.VenderDAO;
import modelo.vo.*;
import org.hibernate.Session;
import vista.PantallaPrincipal;

/**
 *
 * @author acceso a datos
 */
public class controladorPantallaPrincipal {

    public static Session session;
    public static PantallaPrincipal ventana = new PantallaPrincipal();
    public static ClienteDAO cliDAO;
    public static EmpleadoDAO empDAO;
    public static VenderDAO venDAO;

    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }

    public static void iniciarSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        //DAOS
        cliDAO = HibernateUtil.getClienteDAO();
        empDAO = HibernateUtil.getEmpleadoDAO();
        venDAO = HibernateUtil.getVenderDAO();
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void insertarEmp(String codEmp, String contraseña, String dni, String rol, String usuario) {
        try {
            HibernateUtil.beginTx(session);
            Empleado e = empDAO.buscarEmpleado(session, codEmp);
            if (e != null) {
                JOptionPane.showMessageDialog(ventana, "Ya existe el empleado");
                return;
            }
            e = empDAO.buscarUsuario(session, usuario);
            if (e != null) {
                JOptionPane.showMessageDialog(ventana, "Ya existe usuario");
                return;
            }
            e = new Empleado(codEmp, contraseña, dni, rol, usuario);

            empDAO.insertar(session, e);
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Empleado insertado correctamente");
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void borrarEmp(String codEmp) {
        try {
           HibernateUtil.beginTx(session);
            Empleado e = empDAO.buscarEmpleado(session, codEmp);
            if (e == null) {
                JOptionPane.showMessageDialog(ventana, "No existe el empleado");
                return;
            }
            venDAO.modificarEmpNull(session, e);
            empDAO.borrar(session, e);
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Empleado borrado correctamente");

        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modificarEmp(String codEmp, String contraseña, String dni, String rol, String usuario) {
        try {
            HibernateUtil.beginTx(session);
            Empleado e = empDAO.buscarEmpleado(session, codEmp);
            if (e == null) {
                JOptionPane.showMessageDialog(ventana, "No existe el empleado");
                return;
            }
            e = empDAO.buscarUsuario(session, usuario);
            if (e != null) {
                JOptionPane.showMessageDialog(ventana, "Ya existe usuario");
                return;
            }

            empDAO.modificar(session, e,usuario, contraseña, dni, rol);
           HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Empleado modificado correctamente");

        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargarDatosEmp(String codEmp, JTextField usuario, JTextField contra, JTextField dni, JComboBox rol) {
        try {
            HibernateUtil.beginTx(session);
            Empleado e = empDAO.buscarEmpleado(session, codEmp);
            if (e != null) {
                
                usuario.setText(e.getUsuario());
                contra.setText(e.getContraseña());
                dni.setText(e.getDni());
                rol.setSelectedItem(e.getRol());
            } else {
               
                usuario.setText("");
                contra.setText("");
                dni.setText("");
            }
           HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void VisualizarEmp(JTextArea ta) {
        try {
            HibernateUtil.beginTx(session);
            empDAO.cargadatos(session, ta);
           HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertarCli(String idCliente, String dni, String nombre, String numCuenta, Integer telefono) {
        try {
            HibernateUtil.beginTx(session);
            Cliente c = cliDAO.buscarCliente(session, idCliente);
            if (c != null) {
                JOptionPane.showMessageDialog(ventana, "Ya existe el cliente");
                return;
            }
            c = new Cliente(idCliente, dni, nombre, numCuenta, telefono);

            cliDAO.insertar(session, c);
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Cliente insertado correctamente");
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void borrarCli(String idCliente) {
        try {
            HibernateUtil.beginTx(session);
            Cliente c = cliDAO.buscarCliente(session, idCliente);
            if (c == null) {
                JOptionPane.showMessageDialog(ventana, "No existe el cliente");
                return;
            }
            Vender v = venDAO.buscarClienteEnVenta(session, idCliente);
            if (v != null) {
                JOptionPane.showMessageDialog(ventana, "El cliente esta en ventas,no se puede borrar");
                return;
            }
            cliDAO.borrar(session, c);
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Cliente borrado correctamente");
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void modificarCli(String idCliente, String dni, String nombre, String numCuenta, Integer telefono) {
        try {
            HibernateUtil.beginTx(session);
            Cliente c = cliDAO.buscarCliente(session, idCliente);
            if (c == null) {
                JOptionPane.showMessageDialog(ventana, "No existe el cliente");
                return;
            }
            cliDAO.modificar(session, c, nombre, dni, numCuenta, telefono);
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(ventana, "Cliente modificado correctamente");
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargarDatosCli(String idCliente, JTextField dni, JTextField nombre, JTextField numCuenta, JTextField telefono) {
        try {
            HibernateUtil.beginTx(session);
            Cliente c = cliDAO.buscarCliente(session, idCliente);
            if (c != null) {
                nombre.setText(c.getNombre());
                numCuenta.setText(c.getNumCuenta());
                telefono.setText(c.getTelefono() + "");
                dni.setText(c.getDni());

            } else {
                nombre.setText("");
                numCuenta.setText("");
                telefono.setText("");
                dni.setText("");
            }
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
           HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void VisualizarCli(JTextArea ta) {
        try {
            HibernateUtil.beginTx(session);
            cliDAO.cargadatos(session, ta);
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPantallaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
