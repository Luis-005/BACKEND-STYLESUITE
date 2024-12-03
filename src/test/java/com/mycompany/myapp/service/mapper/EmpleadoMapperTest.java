package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.EmpleadoAsserts.*;
import static com.mycompany.myapp.domain.EmpleadoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmpleadoMapperTest {

    private EmpleadoMapper empleadoMapper;

    @BeforeEach
    void setUp() {
        empleadoMapper = new EmpleadoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEmpleadoSample1();
        var actual = empleadoMapper.toEntity(empleadoMapper.toDto(expected));
        assertEmpleadoAllPropertiesEquals(expected, actual);
    }
}
