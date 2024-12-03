package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.PersonaAsserts.*;
import static com.mycompany.myapp.domain.PersonaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaMapperTest {

    private PersonaMapper personaMapper;

    @BeforeEach
    void setUp() {
        personaMapper = new PersonaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPersonaSample1();
        var actual = personaMapper.toEntity(personaMapper.toDto(expected));
        assertPersonaAllPropertiesEquals(expected, actual);
    }
}
