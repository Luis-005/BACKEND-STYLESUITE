package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DisponibilidadEmpleadoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisponibilidadEmpleadoDTO.class);
        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO1 = new DisponibilidadEmpleadoDTO();
        disponibilidadEmpleadoDTO1.setId(1L);
        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO2 = new DisponibilidadEmpleadoDTO();
        assertThat(disponibilidadEmpleadoDTO1).isNotEqualTo(disponibilidadEmpleadoDTO2);
        disponibilidadEmpleadoDTO2.setId(disponibilidadEmpleadoDTO1.getId());
        assertThat(disponibilidadEmpleadoDTO1).isEqualTo(disponibilidadEmpleadoDTO2);
        disponibilidadEmpleadoDTO2.setId(2L);
        assertThat(disponibilidadEmpleadoDTO1).isNotEqualTo(disponibilidadEmpleadoDTO2);
        disponibilidadEmpleadoDTO1.setId(null);
        assertThat(disponibilidadEmpleadoDTO1).isNotEqualTo(disponibilidadEmpleadoDTO2);
    }
}
