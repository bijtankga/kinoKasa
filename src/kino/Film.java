/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kino;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marcin
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Film.findAll", query = "SELECT f FROM Film f")
    , @NamedQuery(name = "Film.findById", query = "SELECT f FROM Film f WHERE f.id = :id")
    , @NamedQuery(name = "Film.findByNazwa", query = "SELECT f FROM Film f WHERE f.nazwa = :nazwa")
    , @NamedQuery(name = "Film.findByRok", query = "SELECT f FROM Film f WHERE f.rok = :rok")
    , @NamedQuery(name = "Film.findByRezyser", query = "SELECT f FROM Film f WHERE f.rezyser = :rezyser")
    , @NamedQuery(name = "Film.findByOpis", query = "SELECT f FROM Film f WHERE f.opis = :opis")
    , @NamedQuery(name = "Film.findByDlugosc", query = "SELECT f FROM Film f WHERE f.dlugosc = :dlugosc")})
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    private String nazwa;
    @Basic(optional = false)
    private short rok;
    @Basic(optional = false)
    private String rezyser;
    @Basic(optional = false)
    private String opis;
    private Long dlugosc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "film")
    private Collection<Seans> seansCollection;

    public Film() {
    }

    public Film(Integer id) {
        this.id = id;
    }

    public Film(Integer id, String nazwa, short rok, String rezyser, String opis) {
        this.id = id;
        this.nazwa = nazwa;
        this.rok = rok;
        this.rezyser = rezyser;
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public short getRok() {
        return rok;
    }

    public void setRok(short rok) {
        this.rok = rok;
    }

    public String getRezyser() {
        return rezyser;
    }

    public void setRezyser(String rezyser) {
        this.rezyser = rezyser;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Long getDlugosc() {
        return dlugosc;
    }

    public void setDlugosc(Long dlugosc) {
        this.dlugosc = dlugosc;
    }

    @XmlTransient
    public Collection<Seans> getSeansCollection() {
        return seansCollection;
    }

    public void setSeansCollection(Collection<Seans> seansCollection) {
        this.seansCollection = seansCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Film)) {
            return false;
        }
        Film other = (Film) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kino.Film[ id=" + id + " ]";
    }
    
}
