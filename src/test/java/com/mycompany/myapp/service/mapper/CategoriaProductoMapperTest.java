package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CategoriaProductoAsserts.*;
import static com.mycompany.myapp.domain.CategoriaProductoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoriaProductoMapperTest {

    private CategoriaProductoMapper categoriaProductoMapper;

    @BeforeEach
    void setUp() {
        categoriaProductoMapper = new CategoriaProductoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCategoriaProductoSample1();
        var actual = categoriaProductoMapper.toEntity(categoriaProductoMapper.toDto(expected));
        assertCategoriaProductoAllPropertiesEquals(expected, actual);
    }
}
