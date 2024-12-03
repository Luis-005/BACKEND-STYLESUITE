package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.ServiciosAsserts.*;
import static com.mycompany.myapp.domain.ServiciosTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiciosMapperTest {

    private ServiciosMapper serviciosMapper;

    @BeforeEach
    void setUp() {
        serviciosMapper = new ServiciosMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getServiciosSample1();
        var actual = serviciosMapper.toEntity(serviciosMapper.toDto(expected));
        assertServiciosAllPropertiesEquals(expected, actual);
    }
}
