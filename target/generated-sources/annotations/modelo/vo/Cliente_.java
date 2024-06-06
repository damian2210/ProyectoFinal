package modelo.vo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.vo.Vender;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-05T22:05:44", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, Long> numCuenta;
    public static volatile SingularAttribute<Cliente, String> idCliente;
    public static volatile SingularAttribute<Cliente, Integer> telefono;
    public static volatile SingularAttribute<Cliente, String> nombre;
    public static volatile SingularAttribute<Cliente, String> dni;
    public static volatile ListAttribute<Cliente, Vender> venderList;

}