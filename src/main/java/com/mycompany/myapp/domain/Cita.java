package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.EstadoCitaEnum;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cita.
 */
@Entity
@Table(name = "cita")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_cita")
    private Instant fechaCita;

    @Column(name = "duracion")
    private Integer duracion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCitaEnum estado;

    @Column(name = "persona_id")
    private Long personaId;

    @Column(name = "nombre_persona")
    private String nombrePersona;

    @Column(name = "establecimiento_id")
    private Long establecimientoId;

    @Column(name = "nombre_establecimiento")
    private String nombreEstablecimiento;

    @Column(name = "servicio_id")
    private Long servicioId;

    @Column(name = "empleado_id")
    private Long empleadoId;

    @Column(name = "nombre_empleado")
    private String nombreEmpleado;

    @Column(name = "valor_servicio", precision = 21, scale = 2)
    private BigDecimal valorServicio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cita id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaCita() {
        return this.fechaCita;
    }

    public Cita fechaCita(Instant fechaCita) {
        this.setFechaCita(fechaCita);
        return this;
    }

    public void setFechaCita(Instant fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Integer getDuracion() {
        return this.duracion;
    }

    public Cita duracion(Integer duracion) {
        this.setDuracion(duracion);
        return this;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public EstadoCitaEnum getEstado() {
        return this.estado;
    }

    public Cita estado(EstadoCitaEnum estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoCitaEnum estado) {
        this.estado = estado;
    }

    public Long getPersonaId() {
        return this.personaId;
    }

    public Cita personaId(Long personaId) {
        this.setPersonaId(personaId);
        return this;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public String getNombrePersona() {
        return this.nombrePersona;
    }

    public Cita nombrePersona(String nombrePersona) {
        this.setNombrePersona(nombrePersona);
        return this;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public Long getEstablecimientoId() {
        return this.establecimientoId;
    }

    public Cita establecimientoId(Long establecimientoId) {
        this.setEstablecimientoId(establecimientoId);
        return this;
    }

    public void setEstablecimientoId(Long establecimientoId) {
        this.establecimientoId = establecimientoId;
    }

    public String getNombreEstablecimiento() {
        return this.nombreEstablecimiento;
    }

    public Cita nombreEstablecimiento(String nombreEstablecimiento) {
        this.setNombreEstablecimiento(nombreEstablecimiento);
        return this;
    }

    public void setNombreEstablecimiento(String nombreEstablecimiento) {
        this.nombreEstablecimiento = nombreEstablecimiento;
    }

    public Long getServicioId() {
        return this.servicioId;
    }

    public Cita servicioId(Long servicioId) {
        this.setServicioId(servicioId);
        return this;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public Long getEmpleadoId() {
        return this.empleadoId;
    }

    public Cita empleadoId(Long empleadoId) {
        this.setEmpleadoId(empleadoId);
        return this;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombreEmpleado() {
        return this.nombreEmpleado;
    }

    public Cita nombreEmpleado(String nombreEmpleado) {
        this.setNombreEmpleado(nombreEmpleado);
        return this;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public BigDecimal getValorServicio() {
        return this.valorServicio;
    }

    public Cita valorServicio(BigDecimal valorServicio) {
        this.setValorServicio(valorServicio);
        return this;
    }

    public void setValorServicio(BigDecimal valorServicio) {
        this.valorServicio = valorServicio;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cita)) {
            return false;
        }
        return getId() != null && getId().equals(((Cita) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cita{" +
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
