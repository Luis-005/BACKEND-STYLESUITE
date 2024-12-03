package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.DisponibilidadEmpleadoAsserts.*;
import static com.mycompany.myapp.domain.DisponibilidadEmpleadoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DisponibilidadEmpleadoMapperTest {

    private DisponibilidadEmpleadoMapper disponibilidadEmpleadoMapper;

    @BeforeEach
    void setUp() {
        disponibilidadEmpleadoMapper = new DisponibilidadEmpleadoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDisponibilidadEmpleadoSample1();
        var actual = disponibilidadEmpleadoMapper.toEntity(disponibilidadEmpleadoMapper.toDto(expected));
        assertDisponibilidadEmpleadoAllPropertiesEquals(expected, actual);
    }
}
