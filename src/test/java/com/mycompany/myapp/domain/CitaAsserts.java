package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class CitaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCitaAllPropertiesEquals(Cita expected, Cita actual) {
        assertCitaAutoGeneratedPropertiesEquals(expected, actual);
        assertCitaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCitaAllUpdatablePropertiesEquals(Cita expected, Cita actual) {
        assertCitaUpdatableFieldsEquals(expected, actual);
        assertCitaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCitaAutoGeneratedPropertiesEquals(Cita expected, Cita actual) {
        assertThat(expected)
            .as("Verify Cita auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCitaUpdatableFieldsEquals(Cita expected, Cita actual) {
        assertThat(expected)
            .as("Verify Cita relevant properties")
            .satisfies(e -> assertThat(e.getFechaCita()).as("check fechaCita").isEqualTo(actual.getFechaCita()))
            .satisfies(e -> assertThat(e.getDuracion()).as("check duracion").isEqualTo(actual.getDuracion()))
            .satisfies(e -> assertThat(e.getEstado()).as("check estado").isEqualTo(actual.getEstado()))
            .satisfies(e -> assertThat(e.getPersonaId()).as("check personaId").isEqualTo(actual.getPersonaId()))
            .satisfies(e -> assertThat(e.getNombrePersona()).as("check nombrePersona").isEqualTo(actual.getNombrePersona()))
            .satisfies(e -> assertThat(e.getEstablecimientoId()).as("check establecimientoId").isEqualTo(actual.getEstablecimientoId()))
            .satisfies(e ->
                assertThat(e.getNombreEstablecimiento()).as("check nombreEstablecimiento").isEqualTo(actual.getNombreEstablecimiento())
            )
            .satisfies(e -> assertThat(e.getServicioId()).as("check servicioId").isEqualTo(actual.getServicioId()))
            .satisfies(e -> assertThat(e.getEmpleadoId()).as("check empleadoId").isEqualTo(actual.getEmpleadoId()))
            .satisfies(e -> assertThat(e.getNombreEmpleado()).as("check nombreEmpleado").isEqualTo(actual.getNombreEmpleado()))
            .satisfies(e ->
                assertThat(e.getValorServicio())
                    .as("check valorServicio")
                    .usingComparator(bigDecimalCompareTo)
                    .isEqualTo(actual.getValorServicio())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCitaUpdatableRelationshipsEquals(Cita expected, Cita actual) {
        // empty method
    }
}