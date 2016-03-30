package rs.raca.tenisdiplomski.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rs.raca.tenisdiplomski.domain.Takmicar;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-26T18:04:26")
@StaticMetamodel(Mesto.class)
public class Mesto_ { 

    public static volatile SingularAttribute<Mesto, Integer> ptt;
    public static volatile SingularAttribute<Mesto, String> naziv;
    public static volatile ListAttribute<Mesto, Takmicar> takmicarList;

}