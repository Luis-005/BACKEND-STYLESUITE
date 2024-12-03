package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.GaleriaImagen} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GaleriaImagenDTO implements Serializable {

    private Long id;

    private String nombre;

    private String descripcion;

    private String urlImagen;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
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
        if (!(o instanceof GaleriaImagenDTO)) {
            return false;
        }

        GaleriaImagenDTO galeriaImagenDTO = (GaleriaImagenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, galeriaImagenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GaleriaImagenDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", urlImagen='" + getUrlImagen() + "'" +
            ", establecimientoId=" + getEstablecimientoId() +
            "}";
    }
}
