/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kino;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marcin
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Miejsce.findAll", query = "SELECT m FROM Miejsce m")
    , @NamedQuery(name = "Miejsce.findById", query = "SELECT m FROM Miejsce m WHERE m.id = :id")
    , @NamedQuery(name = "Miejsce.findByRzad", query = "SELECT m FROM Miejsce m WHERE m.rzad = :rzad")
    , @NamedQuery(name = "Miejsce.findByNr", query = "SELECT m FROM Miejsce m WHERE m.nr = :nr")
    , @NamedQuery(name = "Miejsce.findBySeans", query = "SELECT m FROM Miejsce m WHERE m.seans = :seans")})
public class Miejsce implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private int rzad;
    @Basic(optional = false)
    private int nr;
    @JoinColumn(name = "rezerwacja", referencedColumnName = "id")
    @ManyToOne
    private Rezerwacja rezerwacja;
    @JoinColumn(name = "seans", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Seans seans;

    public Miejsce() {
    }

    public Miejsce(Integer id) {
        this.id = id;
    }

    public Miejsce(Integer id, int rzad, int nr) {
        this.id = id;
        this.rzad = rzad;
        this.nr = nr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRzad() {
        return rzad;
    }

    public void setRzad(int rzad) {
        this.rzad = rzad;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public Rezerwacja getRezerwacja() {
        return rezerwacja;
    }

    public void setRezerwacja(Rezerwacja rezerwacja) {
        this.rezerwacja = rezerwacja;
    }

    public Seans getSeans() {
        return seans;
    }

    public void setSeans(Seans seans) {
        this.seans = seans;
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
        if (!(object instanceof Miejsce)) {
            return false;
        }
        Miejsce other = (Miejsce) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kino.Miejsce[ id=" + id + " ]";
    }
    
}
