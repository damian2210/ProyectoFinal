package modelo.vo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.vo.PrestarPK;
import modelo.vo.Sucursal;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-05T22:05:44", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Prestar.class)
public class Prestar_ { 

    public static volatile SingularAttribute<Prestar, Sucursal> sucursal;
    public static volatile SingularAttribute<Prestar, Sucursal> sucursalPrest;
    public static volatile SingularAttribute<Prestar, PrestarPK> prestarPK;
    public static volatile SingularAttribute<Prestar, Integer> cantidad;

}