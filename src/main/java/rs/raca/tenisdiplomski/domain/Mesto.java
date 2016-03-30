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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Raca420
 */
@Entity
@Table(name = "mesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findByPtt", query = "SELECT m FROM Mesto m WHERE m.ptt = :ptt"),
    @NamedQuery(name = "Mesto.findByNaziv", query = "SELECT m FROM Mesto m WHERE m.naziv = :naziv")})
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ptt")
    private Integer ptt;
    @Size(max = 255)
    @Column(name = "naziv")
    private String naziv;
    @OneToMany(mappedBy = "mesto")
    private List<Takmicar> takmicarList;

    public Mesto() {
    }

    public Mesto(Integer ptt) {
        this.ptt = ptt;
    }

    public Integer getPtt() {
        return ptt;
    }

    public void setPtt(Integer ptt) {
        this.ptt = ptt;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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
        hash += (ptt != null ? ptt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.ptt == null && other.ptt != null) || (this.ptt != null && !this.ptt.equals(other.ptt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.raca.tenisdiplomski.domain.Mesto[ ptt=" + ptt + " ]";
    }
    
}
