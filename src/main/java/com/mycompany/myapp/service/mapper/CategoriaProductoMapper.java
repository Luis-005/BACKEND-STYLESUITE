package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CategoriaProducto;
import com.mycompany.myapp.service.dto.CategoriaProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoriaProducto} and its DTO {@link CategoriaProductoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoriaProductoMapper extends EntityMapper<CategoriaProductoDTO, CategoriaProducto> {}
