package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CategoriaProducto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriaProductoDTO implements Serializable {

    private Long id;

    private String nombre;

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
        if (!(o instanceof CategoriaProductoDTO)) {
            return false;
        }

        CategoriaProductoDTO categoriaProductoDTO = (CategoriaProductoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoriaProductoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriaProductoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", establecimientoId=" + getEstablecimientoId() +
            "}";
    }
}
