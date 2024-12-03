package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CitaAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cita;
import com.mycompany.myapp.domain.enumeration.EstadoCitaEnum;
import com.mycompany.myapp.repository.CitaRepository;
import com.mycompany.myapp.service.dto.CitaDTO;
import com.mycompany.myapp.service.mapper.CitaMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CitaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitaResourceIT {

    private static final Instant DEFAULT_FECHA_CITA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CITA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DURACION = 1;
    private static final Integer UPDATED_DURACION = 2;

    private static final EstadoCitaEnum DEFAULT_ESTADO = EstadoCitaEnum.PENDIENTE;
    private static final EstadoCitaEnum UPDATED_ESTADO = EstadoCitaEnum.CONFIRMADA;

    private static final Long DEFAULT_PERSONA_ID = 1L;
    private static final Long UPDATED_PERSONA_ID = 2L;

    private static final String DEFAULT_NOMBRE_PERSONA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PERSONA = "BBBBBBBBBB";

    private static final Long DEFAULT_ESTABLECIMIENTO_ID = 1L;
    private static final Long UPDATED_ESTABLECIMIENTO_ID = 2L;

    private static final String DEFAULT_NOMBRE_ESTABLECIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ESTABLECIMIENTO = "BBBBBBBBBB";

    private static final Long DEFAULT_SERVICIO_ID = 1L;
    private static final Long UPDATED_SERVICIO_ID = 2L;

    private static final Long DEFAULT_EMPLEADO_ID = 1L;
    private static final Long UPDATED_EMPLEADO_ID = 2L;

    private static final String DEFAULT_NOMBRE_EMPLEADO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EMPLEADO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR_SERVICIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_SERVICIO = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/citas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitaMockMvc;

    private Cita cita;

    private Cita insertedCita;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cita createEntity() {
        return new Cita()
            .fechaCita(DEFAULT_FECHA_CITA)
            .duracion(DEFAULT_DURACION)
            .estado(DEFAULT_ESTADO)
            .personaId(DEFAULT_PERSONA_ID)
            .nombrePersona(DEFAULT_NOMBRE_PERSONA)
            .establecimientoId(DEFAULT_ESTABLECIMIENTO_ID)
            .nombreEstablecimiento(DEFAULT_NOMBRE_ESTABLECIMIENTO)
            .servicioId(DEFAULT_SERVICIO_ID)
            .empleadoId(DEFAULT_EMPLEADO_ID)
            .nombreEmpleado(DEFAULT_NOMBRE_EMPLEADO)
            .valorServicio(DEFAULT_VALOR_SERVICIO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cita createUpdatedEntity() {
        return new Cita()
            .fechaCita(UPDATED_FECHA_CITA)
            .duracion(UPDATED_DURACION)
            .estado(UPDATED_ESTADO)
            .personaId(UPDATED_PERSONA_ID)
            .nombrePersona(UPDATED_NOMBRE_PERSONA)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID)
            .nombreEstablecimiento(UPDATED_NOMBRE_ESTABLECIMIENTO)
            .servicioId(UPDATED_SERVICIO_ID)
            .empleadoId(UPDATED_EMPLEADO_ID)
            .nombreEmpleado(UPDATED_NOMBRE_EMPLEADO)
            .valorServicio(UPDATED_VALOR_SERVICIO);
    }

    @BeforeEach
    public void initTest() {
        cita = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCita != null) {
            citaRepository.delete(insertedCita);
            insertedCita = null;
        }
    }

    @Test
    @Transactional
    void createCita() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);
        var returnedCitaDTO = om.readValue(
            restCitaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CitaDTO.class
        );

        // Validate the Cita in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCita = citaMapper.toEntity(returnedCitaDTO);
        assertCitaUpdatableFieldsEquals(returnedCita, getPersistedCita(returnedCita));

        insertedCita = returnedCita;
    }

    @Test
    @Transactional
    void createCitaWithExistingId() throws Exception {
        // Create the Cita with an existing ID
        cita.setId(1L);
        CitaDTO citaDTO = citaMapper.toDto(cita);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCitas() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        // Get all the citaList
        restCitaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cita.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaCita").value(hasItem(DEFAULT_FECHA_CITA.toString())))
            .andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].personaId").value(hasItem(DEFAULT_PERSONA_ID.intValue())))
            .andExpect(jsonPath("$.[*].nombrePersona").value(hasItem(DEFAULT_NOMBRE_PERSONA)))
            .andExpect(jsonPath("$.[*].establecimientoId").value(hasItem(DEFAULT_ESTABLECIMIENTO_ID.intValue())))
            .andExpect(jsonPath("$.[*].nombreEstablecimiento").value(hasItem(DEFAULT_NOMBRE_ESTABLECIMIENTO)))
            .andExpect(jsonPath("$.[*].servicioId").value(hasItem(DEFAULT_SERVICIO_ID.intValue())))
            .andExpect(jsonPath("$.[*].empleadoId").value(hasItem(DEFAULT_EMPLEADO_ID.intValue())))
            .andExpect(jsonPath("$.[*].nombreEmpleado").value(hasItem(DEFAULT_NOMBRE_EMPLEADO)))
            .andExpect(jsonPath("$.[*].valorServicio").value(hasItem(sameNumber(DEFAULT_VALOR_SERVICIO))));
    }

    @Test
    @Transactional
    void getCita() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        // Get the cita
        restCitaMockMvc
            .perform(get(ENTITY_API_URL_ID, cita.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cita.getId().intValue()))
            .andExpect(jsonPath("$.fechaCita").value(DEFAULT_FECHA_CITA.toString()))
            .andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.personaId").value(DEFAULT_PERSONA_ID.intValue()))
            .andExpect(jsonPath("$.nombrePersona").value(DEFAULT_NOMBRE_PERSONA))
            .andExpect(jsonPath("$.establecimientoId").value(DEFAULT_ESTABLECIMIENTO_ID.intValue()))
            .andExpect(jsonPath("$.nombreEstablecimiento").value(DEFAULT_NOMBRE_ESTABLECIMIENTO))
            .andExpect(jsonPath("$.servicioId").value(DEFAULT_SERVICIO_ID.intValue()))
            .andExpect(jsonPath("$.empleadoId").value(DEFAULT_EMPLEADO_ID.intValue()))
            .andExpect(jsonPath("$.nombreEmpleado").value(DEFAULT_NOMBRE_EMPLEADO))
            .andExpect(jsonPath("$.valorServicio").value(sameNumber(DEFAULT_VALOR_SERVICIO)));
    }

    @Test
    @Transactional
    void getNonExistingCita() throws Exception {
        // Get the cita
        restCitaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCita() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cita
        Cita updatedCita = citaRepository.findById(cita.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCita are not directly saved in db
        em.detach(updatedCita);
        updatedCita
            .fechaCita(UPDATED_FECHA_CITA)
            .duracion(UPDATED_DURACION)
            .estado(UPDATED_ESTADO)
            .personaId(UPDATED_PERSONA_ID)
            .nombrePersona(UPDATED_NOMBRE_PERSONA)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID)
            .nombreEstablecimiento(UPDATED_NOMBRE_ESTABLECIMIENTO)
            .servicioId(UPDATED_SERVICIO_ID)
            .empleadoId(UPDATED_EMPLEADO_ID)
            .nombreEmpleado(UPDATED_NOMBRE_EMPLEADO)
            .valorServicio(UPDATED_VALOR_SERVICIO);
        CitaDTO citaDTO = citaMapper.toDto(updatedCita);

        restCitaMockMvc
            .perform(put(ENTITY_API_URL_ID, citaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isOk());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCitaToMatchAllProperties(updatedCita);
    }

    @Test
    @Transactional
    void putNonExistingCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(put(ENTITY_API_URL_ID, citaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(citaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitaWithPatch() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cita using partial update
        Cita partialUpdatedCita = new Cita();
        partialUpdatedCita.setId(cita.getId());

        partialUpdatedCita
            .estado(UPDATED_ESTADO)
            .personaId(UPDATED_PERSONA_ID)
            .nombrePersona(UPDATED_NOMBRE_PERSONA)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID)
            .nombreEstablecimiento(UPDATED_NOMBRE_ESTABLECIMIENTO)
            .servicioId(UPDATED_SERVICIO_ID)
            .valorServicio(UPDATED_VALOR_SERVICIO);

        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCita.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCita))
            )
            .andExpect(status().isOk());

        // Validate the Cita in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCitaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCita, cita), getPersistedCita(cita));
    }

    @Test
    @Transactional
    void fullUpdateCitaWithPatch() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cita using partial update
        Cita partialUpdatedCita = new Cita();
        partialUpdatedCita.setId(cita.getId());

        partialUpdatedCita
            .fechaCita(UPDATED_FECHA_CITA)
            .duracion(UPDATED_DURACION)
            .estado(UPDATED_ESTADO)
            .personaId(UPDATED_PERSONA_ID)
            .nombrePersona(UPDATED_NOMBRE_PERSONA)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID)
            .nombreEstablecimiento(UPDATED_NOMBRE_ESTABLECIMIENTO)
            .servicioId(UPDATED_SERVICIO_ID)
            .empleadoId(UPDATED_EMPLEADO_ID)
            .nombreEmpleado(UPDATED_NOMBRE_EMPLEADO)
            .valorServicio(UPDATED_VALOR_SERVICIO);

        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCita.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCita))
            )
            .andExpect(status().isOk());

        // Validate the Cita in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCitaUpdatableFieldsEquals(partialUpdatedCita, getPersistedCita(partialUpdatedCita));
    }

    @Test
    @Transactional
    void patchNonExistingCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citaDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(citaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(citaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCita() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cita
        restCitaMockMvc
            .perform(delete(ENTITY_API_URL_ID, cita.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return citaRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Cita getPersistedCita(Cita cita) {
        return citaRepository.findById(cita.getId()).orElseThrow();
    }

    protected void assertPersistedCitaToMatchAllProperties(Cita expectedCita) {
        assertCitaAllPropertiesEquals(expectedCita, getPersistedCita(expectedCita));
    }

    protected void assertPersistedCitaToMatchUpdatableProperties(Cita expectedCita) {
        assertCitaAllUpdatablePropertiesEquals(expectedCita, getPersistedCita(expectedCita));
    }
}
