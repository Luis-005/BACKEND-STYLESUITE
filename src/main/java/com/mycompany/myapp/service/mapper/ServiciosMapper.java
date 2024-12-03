package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Servicios;
import com.mycompany.myapp.service.dto.ServiciosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Servicios} and its DTO {@link ServiciosDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiciosMapper extends EntityMapper<ServiciosDTO, Servicios> {}
