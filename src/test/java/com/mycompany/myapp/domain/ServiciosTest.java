package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ServiciosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiciosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servicios.class);
        Servicios servicios1 = getServiciosSample1();
        Servicios servicios2 = new Servicios();
        assertThat(servicios1).isNotEqualTo(servicios2);

        servicios2.setId(servicios1.getId());
        assertThat(servicios1).isEqualTo(servicios2);

        servicios2 = getServiciosSample2();
        assertThat(servicios1).isNotEqualTo(servicios2);
    }
}
