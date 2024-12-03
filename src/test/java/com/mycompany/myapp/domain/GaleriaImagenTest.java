package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.GaleriaImagenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GaleriaImagenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GaleriaImagen.class);
        GaleriaImagen galeriaImagen1 = getGaleriaImagenSample1();
        GaleriaImagen galeriaImagen2 = new GaleriaImagen();
        assertThat(galeriaImagen1).isNotEqualTo(galeriaImagen2);

        galeriaImagen2.setId(galeriaImagen1.getId());
        assertThat(galeriaImagen1).isEqualTo(galeriaImagen2);

        galeriaImagen2 = getGaleriaImagenSample2();
        assertThat(galeriaImagen1).isNotEqualTo(galeriaImagen2);
    }
}
