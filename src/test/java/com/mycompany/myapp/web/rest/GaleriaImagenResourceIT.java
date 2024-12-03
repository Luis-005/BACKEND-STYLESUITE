package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.GaleriaImagenAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.GaleriaImagen;
import com.mycompany.myapp.repository.GaleriaImagenRepository;
import com.mycompany.myapp.service.dto.GaleriaImagenDTO;
import com.mycompany.myapp.service.mapper.GaleriaImagenMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link GaleriaImagenResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GaleriaImagenResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_URL_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_URL_IMAGEN = "BBBBBBBBBB";

    private static final Long DEFAULT_ESTABLECIMIENTO_ID = 1L;
    private static final Long UPDATED_ESTABLECIMIENTO_ID = 2L;

    private static final String ENTITY_API_URL = "/api/galeria-imagens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GaleriaImagenRepository galeriaImagenRepository;

    @Autowired
    private GaleriaImagenMapper galeriaImagenMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGaleriaImagenMockMvc;

    private GaleriaImagen galeriaImagen;

    private GaleriaImagen insertedGaleriaImagen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GaleriaImagen createEntity() {
        return new GaleriaImagen()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .urlImagen(DEFAULT_URL_IMAGEN)
            .establecimientoId(DEFAULT_ESTABLECIMIENTO_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GaleriaImagen createUpdatedEntity() {
        return new GaleriaImagen()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .urlImagen(UPDATED_URL_IMAGEN)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
    }

    @BeforeEach
    public void initTest() {
        galeriaImagen = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedGaleriaImagen != null) {
            galeriaImagenRepository.delete(insertedGaleriaImagen);
            insertedGaleriaImagen = null;
        }
    }

    @Test
    @Transactional
    void createGaleriaImagen() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);
        var returnedGaleriaImagenDTO = om.readValue(
            restGaleriaImagenMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(galeriaImagenDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GaleriaImagenDTO.class
        );

        // Validate the GaleriaImagen in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedGaleriaImagen = galeriaImagenMapper.toEntity(returnedGaleriaImagenDTO);
        assertGaleriaImagenUpdatableFieldsEquals(returnedGaleriaImagen, getPersistedGaleriaImagen(returnedGaleriaImagen));

        insertedGaleriaImagen = returnedGaleriaImagen;
    }

    @Test
    @Transactional
    void createGaleriaImagenWithExistingId() throws Exception {
        // Create the GaleriaImagen with an existing ID
        galeriaImagen.setId(1L);
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGaleriaImagenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(galeriaImagenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GaleriaImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGaleriaImagens() throws Exception {
        // Initialize the database
        insertedGaleriaImagen = galeriaImagenRepository.saveAndFlush(galeriaImagen);

        // Get all the galeriaImagenList
        restGaleriaImagenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(galeriaImagen.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].urlImagen").value(hasItem(DEFAULT_URL_IMAGEN)))
            .andExpect(jsonPath("$.[*].establecimientoId").value(hasItem(DEFAULT_ESTABLECIMIENTO_ID.intValue())));
    }

    @Test
    @Transactional
    void getGaleriaImagen() throws Exception {
        // Initialize the database
        insertedGaleriaImagen = galeriaImagenRepository.saveAndFlush(galeriaImagen);

        // Get the galeriaImagen
        restGaleriaImagenMockMvc
            .perform(get(ENTITY_API_URL_ID, galeriaImagen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(galeriaImagen.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.urlImagen").value(DEFAULT_URL_IMAGEN))
            .andExpect(jsonPath("$.establecimientoId").value(DEFAULT_ESTABLECIMIENTO_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGaleriaImagen() throws Exception {
        // Get the galeriaImagen
        restGaleriaImagenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGaleriaImagen() throws Exception {
        // Initialize the database
        insertedGaleriaImagen = galeriaImagenRepository.saveAndFlush(galeriaImagen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the galeriaImagen
        GaleriaImagen updatedGaleriaImagen = galeriaImagenRepository.findById(galeriaImagen.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGaleriaImagen are not directly saved in db
        em.detach(updatedGaleriaImagen);
        updatedGaleriaImagen
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .urlImagen(UPDATED_URL_IMAGEN)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(updatedGaleriaImagen);

        restGaleriaImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, galeriaImagenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(galeriaImagenDTO))
            )
            .andExpect(status().isOk());

        // Validate the GaleriaImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGaleriaImagenToMatchAllProperties(updatedGaleriaImagen);
    }

    @Test
    @Transactional
    void putNonExistingGaleriaImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        galeriaImagen.setId(longCount.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, galeriaImagenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(galeriaImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GaleriaImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGaleriaImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        galeriaImagen.setId(longCount.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(galeriaImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GaleriaImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGaleriaImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        galeriaImagen.setId(longCount.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(galeriaImagenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GaleriaImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGaleriaImagenWithPatch() throws Exception {
        // Initialize the database
        insertedGaleriaImagen = galeriaImagenRepository.saveAndFlush(galeriaImagen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the galeriaImagen using partial update
        GaleriaImagen partialUpdatedGaleriaImagen = new GaleriaImagen();
        partialUpdatedGaleriaImagen.setId(galeriaImagen.getId());

        partialUpdatedGaleriaImagen.nombre(UPDATED_NOMBRE);

        restGaleriaImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGaleriaImagen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGaleriaImagen))
            )
            .andExpect(status().isOk());

        // Validate the GaleriaImagen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGaleriaImagenUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGaleriaImagen, galeriaImagen),
            getPersistedGaleriaImagen(galeriaImagen)
        );
    }

    @Test
    @Transactional
    void fullUpdateGaleriaImagenWithPatch() throws Exception {
        // Initialize the database
        insertedGaleriaImagen = galeriaImagenRepository.saveAndFlush(galeriaImagen);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the galeriaImagen using partial update
        GaleriaImagen partialUpdatedGaleriaImagen = new GaleriaImagen();
        partialUpdatedGaleriaImagen.setId(galeriaImagen.getId());

        partialUpdatedGaleriaImagen
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .urlImagen(UPDATED_URL_IMAGEN)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);

        restGaleriaImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGaleriaImagen.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGaleriaImagen))
            )
            .andExpect(status().isOk());

        // Validate the GaleriaImagen in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGaleriaImagenUpdatableFieldsEquals(partialUpdatedGaleriaImagen, getPersistedGaleriaImagen(partialUpdatedGaleriaImagen));
    }

    @Test
    @Transactional
    void patchNonExistingGaleriaImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        galeriaImagen.setId(longCount.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, galeriaImagenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(galeriaImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GaleriaImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGaleriaImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        galeriaImagen.setId(longCount.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(galeriaImagenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GaleriaImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGaleriaImagen() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        galeriaImagen.setId(longCount.incrementAndGet());

        // Create the GaleriaImagen
        GaleriaImagenDTO galeriaImagenDTO = galeriaImagenMapper.toDto(galeriaImagen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGaleriaImagenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(galeriaImagenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GaleriaImagen in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGaleriaImagen() throws Exception {
        // Initialize the database
        insertedGaleriaImagen = galeriaImagenRepository.saveAndFlush(galeriaImagen);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the galeriaImagen
        restGaleriaImagenMockMvc
            .perform(delete(ENTITY_API_URL_ID, galeriaImagen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return galeriaImagenRepository.count();
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

    protected GaleriaImagen getPersistedGaleriaImagen(GaleriaImagen galeriaImagen) {
        return galeriaImagenRepository.findById(galeriaImagen.getId()).orElseThrow();
    }

    protected void assertPersistedGaleriaImagenToMatchAllProperties(GaleriaImagen expectedGaleriaImagen) {
        assertGaleriaImagenAllPropertiesEquals(expectedGaleriaImagen, getPersistedGaleriaImagen(expectedGaleriaImagen));
    }

    protected void assertPersistedGaleriaImagenToMatchUpdatableProperties(GaleriaImagen expectedGaleriaImagen) {
        assertGaleriaImagenAllUpdatablePropertiesEquals(expectedGaleriaImagen, getPersistedGaleriaImagen(expectedGaleriaImagen));
    }
}
