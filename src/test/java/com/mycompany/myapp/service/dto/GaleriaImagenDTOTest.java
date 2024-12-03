package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GaleriaImagenDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GaleriaImagenDTO.class);
        GaleriaImagenDTO galeriaImagenDTO1 = new GaleriaImagenDTO();
        galeriaImagenDTO1.setId(1L);
        GaleriaImagenDTO galeriaImagenDTO2 = new GaleriaImagenDTO();
        assertThat(galeriaImagenDTO1).isNotEqualTo(galeriaImagenDTO2);
        galeriaImagenDTO2.setId(galeriaImagenDTO1.getId());
        assertThat(galeriaImagenDTO1).isEqualTo(galeriaImagenDTO2);
        galeriaImagenDTO2.setId(2L);
        assertThat(galeriaImagenDTO1).isNotEqualTo(galeriaImagenDTO2);
        galeriaImagenDTO1.setId(null);
        assertThat(galeriaImagenDTO1).isNotEqualTo(galeriaImagenDTO2);
    }
}
