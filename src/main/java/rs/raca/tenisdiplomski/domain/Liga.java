/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.domain;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Raca420
 */
@Entity
@Table(name = "liga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Liga.findAll", query = "SELECT l FROM Liga l"),
    @NamedQuery(name = "Liga.findByLigaID", query = "SELECT l FROM Liga l WHERE l.ligaID = :ligaID"),
    @NamedQuery(name = "Liga.findByNaziv", query = "SELECT l FROM Liga l WHERE l.naziv = :naziv"),
    @NamedQuery(name = "Liga.findByBrojTakmicara", query = "SELECT l FROM Liga l WHERE l.brojTakmicara = :brojTakmicara")})
public class Liga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ligaID")
    private Integer ligaID;
    @Size(max = 255)
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "broj_takmicara")
    private Integer brojTakmicara;
    @JoinColumn(name = "takmicenje", referencedColumnName = "takmicenjeID")
    @ManyToOne
    private Takmicenje takmicenje;
    @OneToMany(mappedBy = "liga")
    private List<Takmicar> takmicarList;

    public Liga() {
    }

    public Liga(Integer ligaID) {
        this.ligaID = ligaID;
    }

    public Integer getLigaID() {
        return ligaID;
    }

    public void setLigaID(Integer ligaID) {
        this.ligaID = ligaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojTakmicara() {
        return brojTakmicara;
    }

    public void setBrojTakmicara(Integer brojTakmicara) {
        this.brojTakmicara = brojTakmicara;
    }

    public Takmicenje getTakmicenje() {
        return takmicenje;
    }

    public void setTakmicenje(Takmicenje takmicenje) {
        this.takmicenje = takmicenje;
    }

    @XmlTransient
    @JsonIgnore
    public List<Takmicar> getTakmicarList() {
        return takmicarList;
    }

    public void setTakmicarList(List<Takmicar> takmicarList) {
        this.takmicarList = takmicarList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ligaID != null ? ligaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liga)) {
            return false;
        }
        Liga other = (Liga) object;
        if ((this.ligaID == null && other.ligaID != null) || (this.ligaID != null && !this.ligaID.equals(other.ligaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.raca.tenisdiplomski.domain.Liga[ ligaID=" + ligaID + " ]";
    }
    
}
