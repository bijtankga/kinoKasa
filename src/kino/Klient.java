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
    @NamedQuery(name = "Klient.findAll", query = "SELECT k FROM Klient k")
    , @NamedQuery(name = "Klient.findById", query = "SELECT k FROM Klient k WHERE k.id = :id")
    , @NamedQuery(name = "Klient.findByImie", query = "SELECT k FROM Klient k WHERE k.imie = :imie")
    , @NamedQuery(name = "Klient.findByNazwisko", query = "SELECT k FROM Klient k WHERE k.nazwisko = :nazwisko")
    , @NamedQuery(name = "Klient.findByEmail", query = "SELECT k FROM Klient k WHERE k.email = :email")
    , @NamedQuery(name = "Klient.findByHaslo", query = "SELECT k FROM Klient k WHERE k.haslo = :haslo")})
public class Klient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String imie;
    @Basic(optional = false)
    private String nazwisko;
    @Basic(optional = false)
    private String email;
    @Basic(optional = false)
    private String haslo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klient")
    private Collection<Rezerwacja> rezerwacjaCollection;

    public Klient() {
    }

    public Klient(Integer id) {
        this.id = id;
    }

    public Klient(Integer id, String imie, String nazwisko, String email, String haslo) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.haslo = haslo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    @XmlTransient
    public Collection<Rezerwacja> getRezerwacjaCollection() {
        return rezerwacjaCollection;
    }

    public void setRezerwacjaCollection(Collection<Rezerwacja> rezerwacjaCollection) {
        this.rezerwacjaCollection = rezerwacjaCollection;
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
        if (!(object instanceof Klient)) {
            return false;
        }
        Klient other = (Klient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kino.Klient[ id=" + id + " ]";
    }
    
}
