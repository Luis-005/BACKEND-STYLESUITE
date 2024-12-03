package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.EstadoEmpleadoEnum;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Empleado.
 */
@Entity
@Table(name = "empleado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "salario")
    private Double salario;

    @Column(name = "urlmg")
    private String urlmg;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEmpleadoEnum estado;

    @Column(name = "establecimiento_id")
    private Long establecimientoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Empleado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Empleado nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public Empleado apellido(String apellido) {
        this.setApellido(apellido);
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCargo() {
        return this.cargo;
    }

    public Empleado cargo(String cargo) {
        this.setCargo(cargo);
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getSalario() {
        return this.salario;
    }

    public Empleado salario(Double salario) {
        this.setSalario(salario);
        return this;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getUrlmg() {
        return this.urlmg;
    }

    public Empleado urlmg(String urlmg) {
        this.setUrlmg(urlmg);
        return this;
    }

    public void setUrlmg(String urlmg) {
        this.urlmg = urlmg;
    }

    public EstadoEmpleadoEnum getEstado() {
        return this.estado;
    }

    public Empleado estado(EstadoEmpleadoEnum estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoEmpleadoEnum estado) {
        this.estado = estado;
    }

    public Long getEstablecimientoId() {
        return this.establecimientoId;
    }

    public Empleado establecimientoId(Long establecimientoId) {
        this.setEstablecimientoId(establecimientoId);
        return this;
    }

    public void setEstablecimientoId(Long establecimientoId) {
        this.establecimientoId = establecimientoId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empleado)) {
            return false;
        }
        return getId() != null && getId().equals(((Empleado) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Empleado{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", salario=" + getSalario() +
            ", urlmg='" + getUrlmg() + "'" +
            ", estado='" + getEstado() + "'" +
            ", establecimientoId=" + getEstablecimientoId() +
            "}";
    }
}
