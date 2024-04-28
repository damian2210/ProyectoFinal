package controlador.factory;

import modelo.dao.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
 

public class HibernateUtil {
     
    private static final SessionFactory sessionFactory;
     
    static{
        try{
            sessionFactory = new Configuration().configure().buildSessionFactory();
 
        }catch (Throwable ex) {
            System.err.println("Session Factory could not be created." + ex);
            throw new ExceptionInInitializerError(ex);
        }   
    }
     
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /***************** PARA GESTIONAR LAS TRANSACCIONES ***************/
    public static Transaction beginTx(Session s) {

        if (s.getTransaction() == null || !s.getTransaction().isActive()) {
            return s.beginTransaction();     
        }
        return sessionFactory.getCurrentSession().getTransaction();
    }

    public static void commitTx(Session s) {
        if (s.getTransaction().isActive()) {
            s.getTransaction().commit();
        }
    }
    public static void rollbackTx(Session s) {
        if (s.getTransaction().isActive()) {
            s.getTransaction().rollback();
        }
    }
    /************************ INCORPORA LOS MÉTODOS PARA CREAR LOS OBJETOS DAO ********/
    public static ClienteDAO getClienteDAO(){
        return new ClienteDAO();
    }
    
    public static EmpleadoDAO getEmpleadoDAO(){
        return new EmpleadoDAO();
    }
    
    public static PrestarDAO getPrestarDAO(){
        return new PrestarDAO();
    }
    
    public static ProductoDAO getProductoDAO(){
        return new ProductoDAO();
    }
    
    public static SucursalDAO getSucursalDAO(){
        return new SucursalDAO();
    }
    
    public static VenderDAO getVenderDAO(){
        return new VenderDAO();
    }
    
}