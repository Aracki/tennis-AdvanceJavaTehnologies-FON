package rs.raca.tenisdiplomski.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rs.raca.tenisdiplomski.domain.Takmicenje;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-26T18:04:26")
@StaticMetamodel(Tiptakmicenja.class)
public class Tiptakmicenja_ { 

    public static volatile SingularAttribute<Tiptakmicenja, Integer> vrstaSistema;
    public static volatile SingularAttribute<Tiptakmicenja, Integer> tiptakmicenjaID;
    public static volatile SingularAttribute<Tiptakmicenja, String> nazivTipa;
    public static volatile ListAttribute<Tiptakmicenja, Takmicenje> takmicenjeList;

}