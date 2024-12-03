package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProductoAllPropertiesEquals(Producto expected, Producto actual) {
        assertProductoAutoGeneratedPropertiesEquals(expected, actual);
        assertProductoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProductoAllUpdatablePropertiesEquals(Producto expected, Producto actual) {
        assertProductoUpdatableFieldsEquals(expected, actual);
        assertProductoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProductoAutoGeneratedPropertiesEquals(Producto expected, Producto actual) {
        assertThat(expected)
            .as("Verify Producto auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProductoUpdatableFieldsEquals(Producto expected, Producto actual) {
        assertThat(expected)
            .as("Verify Producto relevant properties")
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()))
            .satisfies(e -> assertThat(e.getPrecio()).as("check precio").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getPrecio()))
            .satisfies(e -> assertThat(e.getCantidad()).as("check cantidad").isEqualTo(actual.getCantidad()))
            .satisfies(e -> assertThat(e.getUrlImg()).as("check urlImg").isEqualTo(actual.getUrlImg()))
            .satisfies(e ->
                assertThat(e.getCategoriaProductoId()).as("check categoriaProductoId").isEqualTo(actual.getCategoriaProductoId())
            )
            .satisfies(e -> assertThat(e.getEstablecimientoId()).as("check establecimientoId").isEqualTo(actual.getEstablecimientoId()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProductoUpdatableRelationshipsEquals(Producto expected, Producto actual) {
        // empty method
    }
}
