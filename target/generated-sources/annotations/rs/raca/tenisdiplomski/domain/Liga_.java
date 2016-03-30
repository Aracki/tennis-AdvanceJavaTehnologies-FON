package rs.raca.tenisdiplomski.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rs.raca.tenisdiplomski.domain.Takmicar;
import rs.raca.tenisdiplomski.domain.Takmicenje;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-26T18:04:26")
@StaticMetamodel(Liga.class)
public class Liga_ { 

    public static volatile SingularAttribute<Liga, Integer> ligaID;
    public static volatile SingularAttribute<Liga, Integer> brojTakmicara;
    public static volatile SingularAttribute<Liga, Takmicenje> takmicenje;
    public static volatile SingularAttribute<Liga, String> naziv;
    public static volatile ListAttribute<Liga, Takmicar> takmicarList;

}