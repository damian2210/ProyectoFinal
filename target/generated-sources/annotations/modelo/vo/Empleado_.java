package modelo.vo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.vo.Vender;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-04-27T18:35:47", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Empleado.class)
public class Empleado_ { 

    public static volatile SingularAttribute<Empleado, String> direccion;
    public static volatile SingularAttribute<Empleado, String> usuario;
    public static volatile SingularAttribute<Empleado, Integer> telefono;
    public static volatile SingularAttribute<Empleado, String> nombre;
    public static volatile SingularAttribute<Empleado, String> dni;
    public static volatile SingularAttribute<Empleado, String> rol;
    public static volatile ListAttribute<Empleado, Vender> venderList;
    public static volatile SingularAttribute<Empleado, String> codEmpleado;
    public static volatile SingularAttribute<Empleado, String> contraseña;

}