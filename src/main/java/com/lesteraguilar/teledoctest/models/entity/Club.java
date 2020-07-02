package com.lesteraguilar.teledoctest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "clubs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Club implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClub;

    @Column(name="nombre_club", nullable = false, length = 255)
    private String nombre;

    @Column(name="pais", length = 255)
    private String pais;

    @Column(name="liga", length = 255)
    private String liga;

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Futbolista> futbolistas;


    public Club(Long idClub, String nombre, String pais, String liga) {
        this.idClub = idClub;
        this.nombre = nombre;
        this.pais = pais;
        this.liga = liga;
    }

    public Club(){}

    public Long getIdClub() {
        return idClub;
    }

    public void setIdClub(Long idclub) {
        this.idClub = idclub;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public Set<Futbolista> getFutbolistas() {
        return futbolistas;
    }

    public void setFutbolistas(Set<Futbolista> futbolistas) {
        this.futbolistas = futbolistas;
    }

    @Override
    public String toString() {
        return "Club{" +
                "idclub=" + idClub +
                ", nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                ", liga='" + liga + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Club club = (Club) o;
        return idClub.equals(club.idClub) &&
                nombre.equals(club.nombre) &&
                Objects.equals(pais, club.pais) &&
                Objects.equals(liga, club.liga);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClub, nombre, pais, liga);
    }
}
