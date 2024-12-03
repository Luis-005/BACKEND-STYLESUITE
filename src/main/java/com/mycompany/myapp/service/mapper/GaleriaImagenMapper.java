package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.GaleriaImagen;
import com.mycompany.myapp.service.dto.GaleriaImagenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GaleriaImagen} and its DTO {@link GaleriaImagenDTO}.
 */
@Mapper(componentModel = "spring")
public interface GaleriaImagenMapper extends EntityMapper<GaleriaImagenDTO, GaleriaImagen> {}
