/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kino;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Rezerwacja.findAll", query = "SELECT r FROM Rezerwacja r")
    , @NamedQuery(name = "Rezerwacja.findById", query = "SELECT r FROM Rezerwacja r WHERE r.id = :id")
    , @NamedQuery(name = "Rezerwacja.findByKupione", query = "SELECT r FROM Rezerwacja r WHERE r.kupione = :kupione")
    //, @NamedQuery(name = "Rezerwacja.findBySeans", query = "SELECT r FROM Rezerwacja r LEFT JOIN Miejsce m ON r.id = m.id where m.seans = :seans")})
    , @NamedQuery(name = "Rezerwacja.findBySeans", query = "SELECT r FROM Rezerwacja r LEFT JOIN Miejsce m ON r = m.rezerwacja where m.seans = :seans GROUP BY r")})
public class Rezerwacja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    private boolean kupione;
    @OneToMany(mappedBy = "rezerwacja")
    private Collection<Miejsce> miejsceCollection;
    @JoinColumn(name = "klient", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Klient klient;

    public Rezerwacja() {
    }

    public Rezerwacja(Integer id) {
        this.id = id;
    }

    public Rezerwacja(Integer id, boolean kupione) {
        this.id = id;
        this.kupione = kupione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getKupione() {
        return kupione;
    }

    public void setKupione(boolean kupione) {
        this.kupione = kupione;
    }

    @XmlTransient
    public Collection<Miejsce> getMiejsceCollection() {
        return miejsceCollection;
    }

    public void setMiejsceCollection(Collection<Miejsce> miejsceCollection) {
        if (this.miejsceCollection != null)
        {
            for(kino.Miejsce miejsce : this.miejsceCollection)
            {
                miejsce.setRezerwacja(null);
            }
        }
        this.miejsceCollection = miejsceCollection;
        for(kino.Miejsce miejsce : miejsceCollection)
        {
            miejsce.setRezerwacja(this);
        }
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
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
        if (!(object instanceof Rezerwacja)) {
            return false;
        }
        Rezerwacja other = (Rezerwacja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String str = klient.getImie().substring(0, 1) + ". " + klient.getNazwisko() + " | miejsc: " + miejsceCollection.size();
        if (kupione)
            str += " | WYKUPIONE";
        return str;
    }
}
