package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class ServiciosAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiciosAllPropertiesEquals(Servicios expected, Servicios actual) {
        assertServiciosAutoGeneratedPropertiesEquals(expected, actual);
        assertServiciosAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiciosAllUpdatablePropertiesEquals(Servicios expected, Servicios actual) {
        assertServiciosUpdatableFieldsEquals(expected, actual);
        assertServiciosUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiciosAutoGeneratedPropertiesEquals(Servicios expected, Servicios actual) {
        assertThat(expected)
            .as("Verify Servicios auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiciosUpdatableFieldsEquals(Servicios expected, Servicios actual) {
        assertThat(expected)
            .as("Verify Servicios relevant properties")
            .satisfies(e ->
                assertThat(e.getValorServicio())
                    .as("check valorServicio")
                    .usingComparator(bigDecimalCompareTo)
                    .isEqualTo(actual.getValorServicio())
            )
            .satisfies(e -> assertThat(e.getTipoServicio()).as("check tipoServicio").isEqualTo(actual.getTipoServicio()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()))
            .satisfies(e -> assertThat(e.getEstablecimientoId()).as("check establecimientoId").isEqualTo(actual.getEstablecimientoId()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertServiciosUpdatableRelationshipsEquals(Servicios expected, Servicios actual) {
        // empty method
    }
}