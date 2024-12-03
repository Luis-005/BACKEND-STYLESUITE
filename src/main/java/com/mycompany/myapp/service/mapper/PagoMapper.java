package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Pago;
import com.mycompany.myapp.service.dto.PagoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pago} and its DTO {@link PagoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PagoMapper extends EntityMapper<PagoDTO, Pago> {}
