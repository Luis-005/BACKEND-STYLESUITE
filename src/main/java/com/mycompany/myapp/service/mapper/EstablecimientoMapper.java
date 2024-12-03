package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Establecimiento;
import com.mycompany.myapp.service.dto.EstablecimientoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Establecimiento} and its DTO {@link EstablecimientoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstablecimientoMapper extends EntityMapper<EstablecimientoDTO, Establecimiento> {}
