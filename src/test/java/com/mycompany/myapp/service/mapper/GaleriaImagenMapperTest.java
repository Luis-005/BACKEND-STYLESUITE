package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.GaleriaImagenAsserts.*;
import static com.mycompany.myapp.domain.GaleriaImagenTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GaleriaImagenMapperTest {

    private GaleriaImagenMapper galeriaImagenMapper;

    @BeforeEach
    void setUp() {
        galeriaImagenMapper = new GaleriaImagenMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getGaleriaImagenSample1();
        var actual = galeriaImagenMapper.toEntity(galeriaImagenMapper.toDto(expected));
        assertGaleriaImagenAllPropertiesEquals(expected, actual);
    }
}
