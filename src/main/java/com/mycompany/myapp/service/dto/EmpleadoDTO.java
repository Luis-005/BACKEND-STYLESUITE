package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EstadoEmpleadoEnum;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Empleado} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpleadoDTO implements Serializable {

    private Long id;

    private String nombre;

    private String apellido;

    private String cargo;

    private Double salario;

    private String urlmg;

    private EstadoEmpleadoEnum estado;

    private Long establecimientoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getUrlmg() {
        return urlmg;
    }

    public void setUrlmg(String urlmg) {
        this.urlmg = urlmg;
    }

    public EstadoEmpleadoEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEmpleadoEnum estado) {
        this.estado = estado;
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
        if (!(o instanceof EmpleadoDTO)) {
            return false;
        }

        EmpleadoDTO empleadoDTO = (EmpleadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, empleadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpleadoDTO{" +
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
