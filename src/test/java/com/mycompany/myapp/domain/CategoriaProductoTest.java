package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CategoriaProductoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriaProductoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaProducto.class);
        CategoriaProducto categoriaProducto1 = getCategoriaProductoSample1();
        CategoriaProducto categoriaProducto2 = new CategoriaProducto();
        assertThat(categoriaProducto1).isNotEqualTo(categoriaProducto2);

        categoriaProducto2.setId(categoriaProducto1.getId());
        assertThat(categoriaProducto1).isEqualTo(categoriaProducto2);

        categoriaProducto2 = getCategoriaProductoSample2();
        assertThat(categoriaProducto1).isNotEqualTo(categoriaProducto2);
    }
}
