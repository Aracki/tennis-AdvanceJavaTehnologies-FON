package rs.raca.tenisdiplomski.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rs.raca.tenisdiplomski.domain.Liga;
import rs.raca.tenisdiplomski.domain.Tiptakmicenja;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-26T18:04:26")
@StaticMetamodel(Takmicenje.class)
public class Takmicenje_ { 

    public static volatile SingularAttribute<Takmicenje, Integer> takmicenjeID;
    public static volatile SingularAttribute<Takmicenje, Date> datumPocetka;
    public static volatile SingularAttribute<Takmicenje, Tiptakmicenja> tiptakmicenja;
    public static volatile SingularAttribute<Takmicenje, String> naziv;
    public static volatile ListAttribute<Takmicenje, Liga> ligaList;

}