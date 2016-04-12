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
import javax.persistence.Lob;
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
@Table(name = "takmicar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Takmicar.findAll", query = "SELECT t FROM Takmicar t"),
    @NamedQuery(name = "Takmicar.findByTakmicarID", query = "SELECT t FROM Takmicar t WHERE t.takmicarID = :takmicarID"),
    @NamedQuery(name = "Takmicar.findByIme", query = "SELECT t FROM Takmicar t WHERE t.ime = :ime"),
    @NamedQuery(name = "Takmicar.findByPrezime", query = "SELECT t FROM Takmicar t WHERE t.prezime = :prezime"),
    @NamedQuery(name = "Takmicar.findByFbLink", query = "SELECT t FROM Takmicar t WHERE t.fbLink = :fbLink"),
    @NamedQuery(name = "Takmicar.findByPozicija", query = "SELECT t FROM Takmicar t WHERE t.pozicija = :pozicija"),
    @NamedQuery(name = "Takmicar.findByBrojPobeda", query = "SELECT t FROM Takmicar t WHERE t.brojPobeda = :brojPobeda"),
    @NamedQuery(name = "Takmicar.findByBrojIzgubljenih", query = "SELECT t FROM Takmicar t WHERE t.brojIzgubljenih = :brojIzgubljenih"),
    @NamedQuery(name = "Takmicar.findByBrojPoena", query = "SELECT t FROM Takmicar t WHERE t.brojPoena = :brojPoena"),
    @NamedQuery(name = "Takmicar.findByGemPlus", query = "SELECT t FROM Takmicar t WHERE t.gemPlus = :gemPlus"),
    @NamedQuery(name = "Takmicar.findByGemMinus", query = "SELECT t FROM Takmicar t WHERE t.gemMinus = :gemMinus"),
    @NamedQuery(name = "Takmicar.findBySetPlus", query = "SELECT t FROM Takmicar t WHERE t.setPlus = :setPlus"),
    @NamedQuery(name = "Takmicar.findBySetMinus", query = "SELECT t FROM Takmicar t WHERE t.setMinus = :setMinus")})
public class Takmicar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "takmicarID")
    private Integer takmicarID;
    @Size(max = 255)
    @Column(name = "ime")
    private String ime;
    @Size(max = 255)
    @Column(name = "prezime")
    private String prezime;
    @Lob
    @Size(max = 65535)
    @Column(name = "opis")
    private String opis;
    @Size(max = 255)
    @Column(name = "fb_link")
    private String fbLink;
    @Column(name = "pozicija")
    private int pozicija;
    @Column(name = "broj_pobeda")
    private int brojPobeda;
    @Column(name = "broj_izgubljenih")
    private int brojIzgubljenih;
    @Column(name = "broj_poena")
    private int brojPoena;
    @Column(name = "gem_plus")
    private int gemPlus;
    @Column(name = "gem_minus")
    private int gemMinus;
    @Column(name = "set_plus")
    private int setPlus;
    @Column(name = "set_minus")
    private int setMinus;
    @JoinColumn(name = "liga", referencedColumnName = "ligaID")
    @ManyToOne
    private Liga liga;
    @JoinColumn(name = "mesto", referencedColumnName = "ptt")
    @ManyToOne
    private Mesto mesto;
    @OneToMany(mappedBy = "takmicarDID")
    private List<Mec> mecList;
    @OneToMany(mappedBy = "takmicarGID")
    private List<Mec> mecList1;

    public Takmicar() {
    }

    public Takmicar(Integer takmicarID) {
        this.takmicarID = takmicarID;
    }

    public Integer getTakmicarID() {
        return takmicarID;
    }

    public void setTakmicarID(Integer takmicarID) {
        this.takmicarID = takmicarID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getFbLink() {
        return fbLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public Integer getPozicija() {
        return pozicija;
    }

    public void setPozicija(Integer pozicija) {
        this.pozicija = pozicija;
    }

    public Integer getBrojPobeda() {
        return brojPobeda;
    }

    public void setBrojPobeda(Integer brojPobeda) {
        this.brojPobeda = brojPobeda;
    }

    public Integer getBrojIzgubljenih() {
        return brojIzgubljenih;
    }

    public void setBrojIzgubljenih(Integer brojIzgubljenih) {
        this.brojIzgubljenih = brojIzgubljenih;
    }

    public Integer getBrojPoena() {
        return brojPoena;
    }

    public void setBrojPoena(Integer brojPoena) {
        this.brojPoena = brojPoena;
    }

    public Integer getGemPlus() {
        return gemPlus;
    }

    public void setGemPlus(Integer gemPlus) {
        this.gemPlus = gemPlus;
    }

    public Integer getGemMinus() {
        return gemMinus;
    }

    public void setGemMinus(Integer gemMinus) {
        this.gemMinus = gemMinus;
    }

    public Integer getSetPlus() {
        return setPlus;
    }

    public void setSetPlus(Integer setPlus) {
        this.setPlus = setPlus;
    }

    public Integer getSetMinus() {
        return setMinus;
    }

    public void setSetMinus(Integer setMinus) {
        this.setMinus = setMinus;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    @XmlTransient
    @JsonIgnore
    public List<Mec> getMecList() {
        return mecList;
    }

    public void setMecList(List<Mec> mecList) {
        this.mecList = mecList;
    }

    @XmlTransient
    @JsonIgnore
    public List<Mec> getMecList1() {
        return mecList1;
    }

    public void setMecList1(List<Mec> mecList1) {
        this.mecList1 = mecList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (takmicarID != null ? takmicarID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Takmicar)) {
            return false;
        }
        Takmicar other = (Takmicar) object;
        if ((this.takmicarID == null && other.takmicarID != null) || (this.takmicarID != null && !this.takmicarID.equals(other.takmicarID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.raca.tenisdiplomski.domain.Takmicar[ takmicarID=" + takmicarID + " ]";
    }
    
}
