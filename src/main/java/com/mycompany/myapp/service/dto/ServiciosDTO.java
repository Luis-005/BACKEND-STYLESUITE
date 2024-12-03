package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Servicios} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiciosDTO implements Serializable {

    private Long id;

    private BigDecimal valorServicio;

    private String tipoServicio;

    private String descripcion;

    private Long establecimientoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorServicio() {
        return valorServicio;
    }

    public void setValorServicio(BigDecimal valorServicio) {
        this.valorServicio = valorServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEstablecimientoId() {
        return establecimientoId;
    }

    public void setEstablecimientoId(Long establecimientoId) {
        this.establecimientoId = establecimientoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiciosDTO)) {
            return false;
        }

        ServiciosDTO serviciosDTO = (ServiciosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviciosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiciosDTO{" +
            "id=" + getId() +
            ", valorServicio=" + getValorServicio() +
            ", tipoServicio='" + getTipoServicio() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", establecimientoId=" + getEstablecimientoId() +
            "}";
    }
}
