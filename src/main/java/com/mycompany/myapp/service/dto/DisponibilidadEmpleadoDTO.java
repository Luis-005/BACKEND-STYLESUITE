package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.DisponibilidadEmpleado} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DisponibilidadEmpleadoDTO implements Serializable {

    private Long id;

    private String diaSemana;

    private Instant fechaInicio;

    private Instant fechaFin;

    private Long empleadoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisponibilidadEmpleadoDTO)) {
            return false;
        }

        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO = (DisponibilidadEmpleadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, disponibilidadEmpleadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DisponibilidadEmpleadoDTO{" +
            "id=" + getId() +
            ", diaSemana='" + getDiaSemana() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", empleadoId=" + getEmpleadoId() +
            "}";
    }
}
