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
@Table(name = "tiptakmicenja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiptakmicenja.findAll", query = "SELECT t FROM Tiptakmicenja t"),
    @NamedQuery(name = "Tiptakmicenja.findByTiptakmicenjaID", query = "SELECT t FROM Tiptakmicenja t WHERE t.tiptakmicenjaID = :tiptakmicenjaID"),
    @NamedQuery(name = "Tiptakmicenja.findByNazivTipa", query = "SELECT t FROM Tiptakmicenja t WHERE t.nazivTipa = :nazivTipa"),
    @NamedQuery(name = "Tiptakmicenja.findByVrstaSistema", query = "SELECT t FROM Tiptakmicenja t WHERE t.vrstaSistema = :vrstaSistema")})
public class Tiptakmicenja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tiptakmicenjaID")
    private Integer tiptakmicenjaID;
    @Size(max = 255)
    @Column(name = "naziv_tipa")
    private String nazivTipa;
    @Column(name = "vrsta_sistema")
    private Integer vrstaSistema;
    @OneToMany(mappedBy = "tiptakmicenja")
    private List<Takmicenje> takmicenjeList;

    public Tiptakmicenja() {
    }

    public Tiptakmicenja(Integer tiptakmicenjaID) {
        this.tiptakmicenjaID = tiptakmicenjaID;
    }

    public Integer getTiptakmicenjaID() {
        return tiptakmicenjaID;
    }

    public void setTiptakmicenjaID(Integer tiptakmicenjaID) {
        this.tiptakmicenjaID = tiptakmicenjaID;
    }

    public String getNazivTipa() {
        return nazivTipa;
    }

    public void setNazivTipa(String nazivTipa) {
        this.nazivTipa = nazivTipa;
    }

    public Integer getVrstaSistema() {
        return vrstaSistema;
    }

    public void setVrstaSistema(Integer vrstaSistema) {
        this.vrstaSistema = vrstaSistema;
    }

    @XmlTransient
    @JsonIgnore
    public List<Takmicenje> getTakmicenjeList() {
        return takmicenjeList;
    }

    public void setTakmicenjeList(List<Takmicenje> takmicenjeList) {
        this.takmicenjeList = takmicenjeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tiptakmicenjaID != null ? tiptakmicenjaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiptakmicenja)) {
            return false;
        }
        Tiptakmicenja other = (Tiptakmicenja) object;
        if ((this.tiptakmicenjaID == null && other.tiptakmicenjaID != null) || (this.tiptakmicenjaID != null && !this.tiptakmicenjaID.equals(other.tiptakmicenjaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.raca.tenisdiplomski.domain.Tiptakmicenja[ tiptakmicenjaID=" + tiptakmicenjaID + " ]";
    }
    
}
