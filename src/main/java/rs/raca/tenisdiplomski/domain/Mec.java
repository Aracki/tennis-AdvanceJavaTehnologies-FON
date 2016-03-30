/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.raca.tenisdiplomski.domain;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Raca420
 */
@Entity
@Table(name = "mec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mec.findAll", query = "SELECT m FROM Mec m"),
    @NamedQuery(name = "Mec.findByMecID", query = "SELECT m FROM Mec m WHERE m.mecID = :mecID"),
    @NamedQuery(name = "Mec.findByRezultat", query = "SELECT m FROM Mec m WHERE m.rezultat = :rezultat")})
public class Mec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mecID")
    private Integer mecID;
    @Size(max = 255)
    @Column(name = "rezultat")
    private String rezultat;
    @JoinColumn(name = "takmicarDID", referencedColumnName = "takmicarID")
    @ManyToOne
    private Takmicar takmicarDID;
    @JoinColumn(name = "takmicarGID", referencedColumnName = "takmicarID")
    @ManyToOne
    private Takmicar takmicarGID;

    public Mec() {
    }

    public Mec(Integer mecID) {
        this.mecID = mecID;
    }

    public Integer getMecID() {
        return mecID;
    }

    public void setMecID(Integer mecID) {
        this.mecID = mecID;
    }

    public String getRezultat() {
        return rezultat;
    }

    public void setRezultat(String rezultat) {
        this.rezultat = rezultat;
    }

    public Takmicar getTakmicarDID() {
        return takmicarDID;
    }

    public void setTakmicarDID(Takmicar takmicarDID) {
        this.takmicarDID = takmicarDID;
    }

    public Takmicar getTakmicarGID() {
        return takmicarGID;
    }

    public void setTakmicarGID(Takmicar takmicarGID) {
        this.takmicarGID = takmicarGID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mecID != null ? mecID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mec)) {
            return false;
        }
        Mec other = (Mec) object;
        if ((this.mecID == null && other.mecID != null) || (this.mecID != null && !this.mecID.equals(other.mecID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.raca.tenisdiplomski.domain.Mec[ mecID=" + mecID + " ]";
    }
    
}
