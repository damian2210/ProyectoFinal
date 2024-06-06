package modelo.vo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.vo.Prestar;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-05T22:05:44", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Sucursal.class)
public class Sucursal_ { 

    public static volatile SingularAttribute<Sucursal, String> direccion;
    public static volatile ListAttribute<Sucursal, Prestar> prestarRealizadosList1;
    public static volatile ListAttribute<Sucursal, Prestar> prestarRecibidosList;
    public static volatile SingularAttribute<Sucursal, Integer> telefono;
    public static volatile SingularAttribute<Sucursal, String> codSucursal;

}