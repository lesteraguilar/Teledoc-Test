package com.lesteraguilar.teledoctest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "futbolistas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Futbolista implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFutbolista;

    @Column(name="nombre_futbolista", nullable = false, length = 255)
    private String nombre;

    @Column(name="apellido", length = 255)
    private String apellido;

    @Column(name="posicion", nullable = false, length = 255)
    private String posicion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_nacimiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_club")
    @JsonIgnore
    private Club club;

    public Futbolista(Long idFutbolista, String nombre, String apellido, String posicion, Date fecha_nacimiento, Club club) {
        this.idFutbolista = idFutbolista;
        this.nombre = nombre;
        this.apellido = apellido;
        this.posicion = posicion;
        this.fecha_nacimiento = fecha_nacimiento;
        this.club = club;
    }

    public Futbolista(){}

    public Long getIdFutbolista() {
        return idFutbolista;
    }

    public void setIdFutbolista(Long idFutbolista) {
        this.idFutbolista = idFutbolista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @Override
    public String toString() {
        return "Futbolista{" +
                "idFutbolista=" + idFutbolista +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", posicion='" + posicion + '\'' +
                ", fecha_nacimiento=" + fecha_nacimiento +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Futbolista that = (Futbolista) o;
        return idFutbolista.equals(that.idFutbolista) &&
                nombre.equals(that.nombre) &&
                Objects.equals(apellido, that.apellido) &&
                posicion.equals(that.posicion) &&
                Objects.equals(fecha_nacimiento, that.fecha_nacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFutbolista, nombre, apellido, posicion, fecha_nacimiento);
    }
}
