package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Empleado;
import com.mycompany.myapp.service.dto.EmpleadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empleado} and its DTO {@link EmpleadoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmpleadoMapper extends EntityMapper<EmpleadoDTO, Empleado> {}
