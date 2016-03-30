package rs.raca.tenisdiplomski.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rs.raca.tenisdiplomski.domain.Liga;
import rs.raca.tenisdiplomski.domain.Mec;
import rs.raca.tenisdiplomski.domain.Mesto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-26T18:04:26")
@StaticMetamodel(Takmicar.class)
public class Takmicar_ { 

    public static volatile SingularAttribute<Takmicar, String> ime;
    public static volatile SingularAttribute<Takmicar, String> prezime;
    public static volatile SingularAttribute<Takmicar, String> fbLink;
    public static volatile SingularAttribute<Takmicar, Integer> brojIzgubljenih;
    public static volatile SingularAttribute<Takmicar, Liga> liga;
    public static volatile SingularAttribute<Takmicar, Integer> brojPoena;
    public static volatile SingularAttribute<Takmicar, Integer> gemPlus;
    public static volatile ListAttribute<Takmicar, Mec> mecList1;
    public static volatile SingularAttribute<Takmicar, Integer> brojPobeda;
    public static volatile SingularAttribute<Takmicar, Integer> setPlus;
    public static volatile SingularAttribute<Takmicar, Integer> setMinus;
    public static volatile SingularAttribute<Takmicar, Integer> pozicija;
    public static volatile SingularAttribute<Takmicar, Mesto> mesto;
    public static volatile SingularAttribute<Takmicar, Integer> gemMinus;
    public static volatile SingularAttribute<Takmicar, Integer> takmicarID;
    public static volatile ListAttribute<Takmicar, Mec> mecList;
    public static volatile SingularAttribute<Takmicar, String> opis;

}