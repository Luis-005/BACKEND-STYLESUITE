package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.EstablecimientoAsserts.*;
import static com.mycompany.myapp.domain.EstablecimientoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstablecimientoMapperTest {

    private EstablecimientoMapper establecimientoMapper;

    @BeforeEach
    void setUp() {
        establecimientoMapper = new EstablecimientoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEstablecimientoSample1();
        var actual = establecimientoMapper.toEntity(establecimientoMapper.toDto(expected));
        assertEstablecimientoAllPropertiesEquals(expected, actual);
    }
}
