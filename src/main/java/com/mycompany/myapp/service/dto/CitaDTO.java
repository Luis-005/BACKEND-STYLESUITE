package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EstadoCitaEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Cita} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CitaDTO implements Serializable {

    private Long id;

    private Instant fechaCita;

    private Integer duracion;

    private EstadoCitaEnum estado;

    private Long personaId;

    private String nombrePersona;

    private Long establecimientoId;

    private String nombreEstablecimiento;

    private Long servicioId;

    private Long empleadoId;

    private String nombreEmpleado;

    private BigDecimal valorServicio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Instant fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public EstadoCitaEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoCitaEnum estado) {
        this.estado = estado;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public Long getEstablecimientoId() {
        return establecimientoId;
    }

    public void setEstablecimientoId(Long establecimientoId) {
        this.establecimientoId = establecimientoId;
    }

    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }

    public void setNombreEstablecimiento(String nombreEstablecimiento) {
        this.nombreEstablecimiento = nombreEstablecimiento;
    }

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public BigDecimal getValorServicio() {
        return valorServicio;
    }

    public void setValorServicio(BigDecimal valorServicio) {
        this.valorServicio = valorServicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CitaDTO)) {
            return false;
        }

        CitaDTO citaDTO = (CitaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, citaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitaDTO{" +
            "id=" + getId() +
            ", fechaCita='" + getFechaCita() + "'" +
            ", duracion=" + getDuracion() +
            ", estado='" + getEstado() + "'" +
            ", personaId=" + getPersonaId() +
            ", nombrePersona='" + getNombrePersona() + "'" +
            ", establecimientoId=" + getEstablecimientoId() +
            ", nombreEstablecimiento='" + getNombreEstablecimiento() + "'" +
            ", servicioId=" + getServicioId() +
            ", empleadoId=" + getEmpleadoId() +
            ", nombreEmpleado='" + getNombreEmpleado() + "'" +
            ", valorServicio=" + getValorServicio() +
            "}";
    }
}
