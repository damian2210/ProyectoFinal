package modelo.vo;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.vo.Vender;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-04-27T18:35:47", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(ProductoFinanciero.class)
public class ProductoFinanciero_ { 

    public static volatile SingularAttribute<ProductoFinanciero, String> codProducto;
    public static volatile SingularAttribute<ProductoFinanciero, String> tipo;
    public static volatile SingularAttribute<ProductoFinanciero, Date> fechaDevolucion;
    public static volatile SingularAttribute<ProductoFinanciero, String> puntuacion;
    public static volatile ListAttribute<ProductoFinanciero, Vender> venderList;
    public static volatile SingularAttribute<ProductoFinanciero, Integer> interes;

}