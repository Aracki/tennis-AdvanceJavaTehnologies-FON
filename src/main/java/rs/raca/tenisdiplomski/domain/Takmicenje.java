/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Raca420
 */
@Entity
@Table(name = "takmicenje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Takmicenje.findAll", query = "SELECT t FROM Takmicenje t"),
    @NamedQuery(name = "Takmicenje.findByTakmicenjeID", query = "SELECT t FROM Takmicenje t WHERE t.takmicenjeID = :takmicenjeID"),
    @NamedQuery(name = "Takmicenje.findByNaziv", query = "SELECT t FROM Takmicenje t WHERE t.naziv = :naziv"),
    @NamedQuery(name = "Takmicenje.findByDatumPocetka", query = "SELECT t FROM Takmicenje t WHERE t.datumPocetka = :datumPocetka")})
public class Takmicenje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "takmicenjeID")
    private Integer takmicenjeID;
    @Size(max = 255)
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "datum_pocetka")
    @Temporal(TemporalType.DATE)
    private Date datumPocetka;
    @JoinColumn(name = "tiptakmicenja", referencedColumnName = "tiptakmicenjaID")
    @ManyToOne
    private Tiptakmicenja tiptakmicenja;
    @OneToMany(mappedBy = "takmicenje")
    private List<Liga> ligaList;

    public Takmicenje() {
    }

    public Takmicenje(Integer takmicenjeID) {
        this.takmicenjeID = takmicenjeID;
    }

    public Integer getTakmicenjeID() {
        return takmicenjeID;
    }

    public void setTakmicenjeID(Integer takmicenjeID) {
        this.takmicenjeID = takmicenjeID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(Date datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public Tiptakmicenja getTiptakmicenja() {
        return tiptakmicenja;
    }

    public void setTiptakmicenja(Tiptakmicenja tiptakmicenja) {
        this.tiptakmicenja = tiptakmicenja;
    }

    @XmlTransient
    @JsonIgnore
    public List<Liga> getLigaList() {
        return ligaList;
    }

    public void setLigaList(List<Liga> ligaList) {
        this.ligaList = ligaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (takmicenjeID != null ? takmicenjeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Takmicenje)) {
            return false;
        }
        Takmicenje other = (Takmicenje) object;
        if ((this.takmicenjeID == null && other.takmicenjeID != null) || (this.takmicenjeID != null && !this.takmicenjeID.equals(other.takmicenjeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.raca.tenisdiplomski.domain.Takmicenje[ takmicenjeID=" + takmicenjeID + " ]";
    }
    
}
