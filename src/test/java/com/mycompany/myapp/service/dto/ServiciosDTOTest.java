package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiciosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiciosDTO.class);
        ServiciosDTO serviciosDTO1 = new ServiciosDTO();
        serviciosDTO1.setId(1L);
        ServiciosDTO serviciosDTO2 = new ServiciosDTO();
        assertThat(serviciosDTO1).isNotEqualTo(serviciosDTO2);
        serviciosDTO2.setId(serviciosDTO1.getId());
        assertThat(serviciosDTO1).isEqualTo(serviciosDTO2);
        serviciosDTO2.setId(2L);
        assertThat(serviciosDTO1).isNotEqualTo(serviciosDTO2);
        serviciosDTO1.setId(null);
        assertThat(serviciosDTO1).isNotEqualTo(serviciosDTO2);
    }
}
