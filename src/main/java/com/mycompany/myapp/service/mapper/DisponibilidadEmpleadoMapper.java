package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.DisponibilidadEmpleado;
import com.mycompany.myapp.service.dto.DisponibilidadEmpleadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DisponibilidadEmpleado} and its DTO {@link DisponibilidadEmpleadoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DisponibilidadEmpleadoMapper extends EntityMapper<DisponibilidadEmpleadoDTO, DisponibilidadEmpleado> {}
