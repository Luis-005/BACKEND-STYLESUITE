package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Servicios.
 */
@Entity
@Table(name = "servicios")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Servicios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "valor_servicio", precision = 21, scale = 2)
    private BigDecimal valorServicio;

    @Column(name = "tipo_servicio")
    private String tipoServicio;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "establecimiento_id")
    private Long establecimientoId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Servicios id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorServicio() {
        return this.valorServicio;
    }

    public Servicios valorServicio(BigDecimal valorServicio) {
        this.setValorServicio(valorServicio);
        return this;
    }

    public void setValorServicio(BigDecimal valorServicio) {
        this.valorServicio = valorServicio;
    }

    public String getTipoServicio() {
        return this.tipoServicio;
    }

    public Servicios tipoServicio(String tipoServicio) {
        this.setTipoServicio(tipoServicio);
        return this;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Servicios descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEstablecimientoId() {
        return this.establecimientoId;
    }

    public Servicios establecimientoId(Long establecimientoId) {
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
        if (!(o instanceof Servicios)) {
            return false;
        }
        return getId() != null && getId().equals(((Servicios) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Servicios{" +
            "id=" + getId() +
            ", valorServicio=" + getValorServicio() +
            ", tipoServicio='" + getTipoServicio() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", establecimientoId=" + getEstablecimientoId() +
            "}";
    }
}
