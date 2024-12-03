package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.DisponibilidadEmpleadoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DisponibilidadEmpleadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisponibilidadEmpleado.class);
        DisponibilidadEmpleado disponibilidadEmpleado1 = getDisponibilidadEmpleadoSample1();
        DisponibilidadEmpleado disponibilidadEmpleado2 = new DisponibilidadEmpleado();
        assertThat(disponibilidadEmpleado1).isNotEqualTo(disponibilidadEmpleado2);

        disponibilidadEmpleado2.setId(disponibilidadEmpleado1.getId());
        assertThat(disponibilidadEmpleado1).isEqualTo(disponibilidadEmpleado2);

        disponibilidadEmpleado2 = getDisponibilidadEmpleadoSample2();
        assertThat(disponibilidadEmpleado1).isNotEqualTo(disponibilidadEmpleado2);
    }
}
