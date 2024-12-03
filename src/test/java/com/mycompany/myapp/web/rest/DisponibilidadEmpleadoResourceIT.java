package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.DisponibilidadEmpleadoAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.DisponibilidadEmpleado;
import com.mycompany.myapp.repository.DisponibilidadEmpleadoRepository;
import com.mycompany.myapp.service.dto.DisponibilidadEmpleadoDTO;
import com.mycompany.myapp.service.mapper.DisponibilidadEmpleadoMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link DisponibilidadEmpleadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DisponibilidadEmpleadoResourceIT {

    private static final String DEFAULT_DIA_SEMANA = "AAAAAAAAAA";
    private static final String UPDATED_DIA_SEMANA = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_EMPLEADO_ID = 1L;
    private static final Long UPDATED_EMPLEADO_ID = 2L;

    private static final String ENTITY_API_URL = "/api/disponibilidad-empleados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DisponibilidadEmpleadoRepository disponibilidadEmpleadoRepository;

    @Autowired
    private DisponibilidadEmpleadoMapper disponibilidadEmpleadoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisponibilidadEmpleadoMockMvc;

    private DisponibilidadEmpleado disponibilidadEmpleado;

    private DisponibilidadEmpleado insertedDisponibilidadEmpleado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisponibilidadEmpleado createEntity() {
        return new DisponibilidadEmpleado()
            .diaSemana(DEFAULT_DIA_SEMANA)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .empleadoId(DEFAULT_EMPLEADO_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisponibilidadEmpleado createUpdatedEntity() {
        return new DisponibilidadEmpleado()
            .diaSemana(UPDATED_DIA_SEMANA)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .empleadoId(UPDATED_EMPLEADO_ID);
    }

    @BeforeEach
    public void initTest() {
        disponibilidadEmpleado = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDisponibilidadEmpleado != null) {
            disponibilidadEmpleadoRepository.delete(insertedDisponibilidadEmpleado);
            insertedDisponibilidadEmpleado = null;
        }
    }

    @Test
    @Transactional
    void createDisponibilidadEmpleado() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DisponibilidadEmpleado
        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO = disponibilidadEmpleadoMapper.toDto(disponibilidadEmpleado);
        var returnedDisponibilidadEmpleadoDTO = om.readValue(
            restDisponibilidadEmpleadoMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(disponibilidadEmpleadoDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DisponibilidadEmpleadoDTO.class
        );

        // Validate the DisponibilidadEmpleado in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDisponibilidadEmpleado = disponibilidadEmpleadoMapper.toEntity(returnedDisponibilidadEmpleadoDTO);
        assertDisponibilidadEmpleadoUpdatableFieldsEquals(
            returnedDisponibilidadEmpleado,
            getPersistedDisponibilidadEmpleado(returnedDisponibilidadEmpleado)
        );

        insertedDisponibilidadEmpleado = returnedDisponibilidadEmpleado;
    }

    @Test
    @Transactional
    void createDisponibilidadEmpleadoWithExistingId() throws Exception {
        // Create the DisponibilidadEmpleado with an existing ID
        disponibilidadEmpleado.setId(1L);
        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO = disponibilidadEmpleadoMapper.toDto(disponibilidadEmpleado);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisponibilidadEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(disponibilidadEmpleadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DisponibilidadEmpleado in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDisponibilidadEmpleados() throws Exception {
        // Initialize the database
        insertedDisponibilidadEmpleado = disponibilidadEmpleadoRepository.saveAndFlush(disponibilidadEmpleado);

        // Get all the disponibilidadEmpleadoList
        restDisponibilidadEmpleadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disponibilidadEmpleado.getId().intValue())))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].empleadoId").value(hasItem(DEFAULT_EMPLEADO_ID.intValue())));
    }

    @Test
    @Transactional
    void getDisponibilidadEmpleado() throws Exception {
        // Initialize the database
        insertedDisponibilidadEmpleado = disponibilidadEmpleadoRepository.saveAndFlush(disponibilidadEmpleado);

        // Get the disponibilidadEmpleado
        restDisponibilidadEmpleadoMockMvc
            .perform(get(ENTITY_API_URL_ID, disponibilidadEmpleado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disponibilidadEmpleado.getId().intValue()))
            .andExpect(jsonPath("$.diaSemana").value(DEFAULT_DIA_SEMANA))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.empleadoId").value(DEFAULT_EMPLEADO_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDisponibilidadEmpleado() throws Exception {
        // Get the disponibilidadEmpleado
        restDisponibilidadEmpleadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDisponibilidadEmpleado() throws Exception {
        // Initialize the database
        insertedDisponibilidadEmpleado = disponibilidadEmpleadoRepository.saveAndFlush(disponibilidadEmpleado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the disponibilidadEmpleado
        DisponibilidadEmpleado updatedDisponibilidadEmpleado = disponibilidadEmpleadoRepository
            .findById(disponibilidadEmpleado.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDisponibilidadEmpleado are not directly saved in db
        em.detach(updatedDisponibilidadEmpleado);
        updatedDisponibilidadEmpleado
            .diaSemana(UPDATED_DIA_SEMANA)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .empleadoId(UPDATED_EMPLEADO_ID);
        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO = disponibilidadEmpleadoMapper.toDto(updatedDisponibilidadEmpleado);

        restDisponibilidadEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, disponibilidadEmpleadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(disponibilidadEmpleadoDTO))
            )
            .andExpect(status().isOk());

        // Validate the DisponibilidadEmpleado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDisponibilidadEmpleadoToMatchAllProperties(updatedDisponibilidadEmpleado);
    }

    @Test
    @Transactional
    void putNonExistingDisponibilidadEmpleado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disponibilidadEmpleado.setId(longCount.incrementAndGet());

        // Create the DisponibilidadEmpleado
        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO = disponibilidadEmpleadoMapper.toDto(disponibilidadEmpleado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisponibilidadEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, disponibilidadEmpleadoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(disponibilidadEmpleadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DisponibilidadEmpleado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDisponibilidadEmpleado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disponibilidadEmpleado.setId(longCount.incrementAndGet());

        // Create the DisponibilidadEmpleado
        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO = disponibilidadEmpleadoMapper.toDto(disponibilidadEmpleado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisponibilidadEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(disponibilidadEmpleadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DisponibilidadEmpleado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDisponibilidadEmpleado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disponibilidadEmpleado.setId(longCount.incrementAndGet());

        // Create the DisponibilidadEmpleado
        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO = disponibilidadEmpleadoMapper.toDto(disponibilidadEmpleado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisponibilidadEmpleadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(disponibilidadEmpleadoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DisponibilidadEmpleado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDisponibilidadEmpleadoWithPatch() throws Exception {
        // Initialize the database
        insertedDisponibilidadEmpleado = disponibilidadEmpleadoRepository.saveAndFlush(disponibilidadEmpleado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the disponibilidadEmpleado using partial update
        DisponibilidadEmpleado partialUpdatedDisponibilidadEmpleado = new DisponibilidadEmpleado();
        partialUpdatedDisponibilidadEmpleado.setId(disponibilidadEmpleado.getId());

        partialUpdatedDisponibilidadEmpleado.diaSemana(UPDATED_DIA_SEMANA).fechaFin(UPDATED_FECHA_FIN).empleadoId(UPDATED_EMPLEADO_ID);

        restDisponibilidadEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisponibilidadEmpleado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDisponibilidadEmpleado))
            )
            .andExpect(status().isOk());

        // Validate the DisponibilidadEmpleado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDisponibilidadEmpleadoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDisponibilidadEmpleado, disponibilidadEmpleado),
            getPersistedDisponibilidadEmpleado(disponibilidadEmpleado)
        );
    }

    @Test
    @Transactional
    void fullUpdateDisponibilidadEmpleadoWithPatch() throws Exception {
        // Initialize the database
        insertedDisponibilidadEmpleado = disponibilidadEmpleadoRepository.saveAndFlush(disponibilidadEmpleado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the disponibilidadEmpleado using partial update
        DisponibilidadEmpleado partialUpdatedDisponibilidadEmpleado = new DisponibilidadEmpleado();
        partialUpdatedDisponibilidadEmpleado.setId(disponibilidadEmpleado.getId());

        partialUpdatedDisponibilidadEmpleado
            .diaSemana(UPDATED_DIA_SEMANA)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .empleadoId(UPDATED_EMPLEADO_ID);

        restDisponibilidadEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisponibilidadEmpleado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDisponibilidadEmpleado))
            )
            .andExpect(status().isOk());

        // Validate the DisponibilidadEmpleado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDisponibilidadEmpleadoUpdatableFieldsEquals(
            partialUpdatedDisponibilidadEmpleado,
            getPersistedDisponibilidadEmpleado(partialUpdatedDisponibilidadEmpleado)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDisponibilidadEmpleado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disponibilidadEmpleado.setId(longCount.incrementAndGet());

        // Create the DisponibilidadEmpleado
        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO = disponibilidadEmpleadoMapper.toDto(disponibilidadEmpleado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisponibilidadEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, disponibilidadEmpleadoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(disponibilidadEmpleadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DisponibilidadEmpleado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDisponibilidadEmpleado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disponibilidadEmpleado.setId(longCount.incrementAndGet());

        // Create the DisponibilidadEmpleado
        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO = disponibilidadEmpleadoMapper.toDto(disponibilidadEmpleado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisponibilidadEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(disponibilidadEmpleadoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DisponibilidadEmpleado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDisponibilidadEmpleado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        disponibilidadEmpleado.setId(longCount.incrementAndGet());

        // Create the DisponibilidadEmpleado
        DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO = disponibilidadEmpleadoMapper.toDto(disponibilidadEmpleado);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDisponibilidadEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(disponibilidadEmpleadoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DisponibilidadEmpleado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDisponibilidadEmpleado() throws Exception {
        // Initialize the database
        insertedDisponibilidadEmpleado = disponibilidadEmpleadoRepository.saveAndFlush(disponibilidadEmpleado);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the disponibilidadEmpleado
        restDisponibilidadEmpleadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, disponibilidadEmpleado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return disponibilidadEmpleadoRepository.count();
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

    protected DisponibilidadEmpleado getPersistedDisponibilidadEmpleado(DisponibilidadEmpleado disponibilidadEmpleado) {
        return disponibilidadEmpleadoRepository.findById(disponibilidadEmpleado.getId()).orElseThrow();
    }

    protected void assertPersistedDisponibilidadEmpleadoToMatchAllProperties(DisponibilidadEmpleado expectedDisponibilidadEmpleado) {
        assertDisponibilidadEmpleadoAllPropertiesEquals(
            expectedDisponibilidadEmpleado,
            getPersistedDisponibilidadEmpleado(expectedDisponibilidadEmpleado)
        );
    }

    protected void assertPersistedDisponibilidadEmpleadoToMatchUpdatableProperties(DisponibilidadEmpleado expectedDisponibilidadEmpleado) {
        assertDisponibilidadEmpleadoAllUpdatablePropertiesEquals(
            expectedDisponibilidadEmpleado,
            getPersistedDisponibilidadEmpleado(expectedDisponibilidadEmpleado)
        );
    }
}
