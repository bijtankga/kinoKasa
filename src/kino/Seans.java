/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kino;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marcin
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seans.findAll", query = "SELECT s FROM Seans s")
    , @NamedQuery(name = "Seans.findById", query = "SELECT s FROM Seans s WHERE s.id = :id")
    , @NamedQuery(name = "Seans.findByData", query = "SELECT s FROM Seans s WHERE s.data = :data")
    , @NamedQuery(name = "Seans.findByCena", query = "SELECT s FROM Seans s WHERE s.cena = :cena")
    , @NamedQuery(name = "Seans.findByD3", query = "SELECT s FROM Seans s WHERE s.d3 = :d3")
    , @NamedQuery(name = "Seans.findByFilm", query = "SELECT s FROM Seans s WHERE s.film = :film")})
public class Seans implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    //@Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    private BigDecimal cena;
    @Basic(optional = false)
    private boolean d3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seans")
    private Collection<Miejsce> miejsceCollection;
    @JoinColumn(name = "film", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Film film;

    public Seans() {
    }

    public Seans(Integer id) {
        this.id = id;
    }

    public Seans(Integer id, Date data, BigDecimal cena, boolean d3) {
        this.id = id;
        this.data = data;
        this.cena = cena;
        this.d3 = d3;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public boolean getD3() {
        return d3;
    }

    public void setD3(boolean d3) {
        this.d3 = d3;
    }

    @XmlTransient
    public Collection<Miejsce> getMiejsceCollection() {
        return miejsceCollection;
    }

    public void setMiejsceCollection(Collection<Miejsce> miejsceCollection) {
        this.miejsceCollection = miejsceCollection;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
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
        if (!(object instanceof Seans)) {
            return false;
        }
        Seans other = (Seans) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "kino.Seans[ id=" + id + " ]";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String wynik = dateFormat.format(data);
        if (d3)
            wynik += " | 3D";
        else
            wynik += " | 2D";
        wynik += " | cena: " + cena.setScale(2);
        int ile = 0;
        for(Miejsce miejsce: miejsceCollection)
        {
            if(miejsce.getRezerwacja() != null)
            {
                ile++;
            }
        }
        wynik += " | zajete: " + ile + "/" + miejsceCollection.size();
        
        return wynik;
    }
    
}
