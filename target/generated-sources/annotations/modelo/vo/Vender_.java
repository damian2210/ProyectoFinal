package modelo.vo;

import java.sql.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.vo.Cliente;
import modelo.vo.Empleado;
import modelo.vo.ProductoFinanciero;
import modelo.vo.VenderPK;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-05T22:05:44", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Vender.class)
public class Vender_ { 

    public static volatile SingularAttribute<Vender, Cliente> cliente;
    public static volatile SingularAttribute<Vender, VenderPK> venderPK;
    public static volatile SingularAttribute<Vender, Date> fechaVenta;
    public static volatile SingularAttribute<Vender, Empleado> codEmpleado;
    public static volatile SingularAttribute<Vender, ProductoFinanciero> productoFinanciero;

}