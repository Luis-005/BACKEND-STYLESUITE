package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CategoriaProductoAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CategoriaProducto;
import com.mycompany.myapp.repository.CategoriaProductoRepository;
import com.mycompany.myapp.service.dto.CategoriaProductoDTO;
import com.mycompany.myapp.service.mapper.CategoriaProductoMapper;
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
 * Integration tests for the {@link CategoriaProductoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoriaProductoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Long DEFAULT_ESTABLECIMIENTO_ID = 1L;
    private static final Long UPDATED_ESTABLECIMIENTO_ID = 2L;

    private static final String ENTITY_API_URL = "/api/categoria-productos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    @Autowired
    private CategoriaProductoMapper categoriaProductoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriaProductoMockMvc;

    private CategoriaProducto categoriaProducto;

    private CategoriaProducto insertedCategoriaProducto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaProducto createEntity() {
        return new CategoriaProducto().nombre(DEFAULT_NOMBRE).establecimientoId(DEFAULT_ESTABLECIMIENTO_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaProducto createUpdatedEntity() {
        return new CategoriaProducto().nombre(UPDATED_NOMBRE).establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
    }

    @BeforeEach
    public void initTest() {
        categoriaProducto = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCategoriaProducto != null) {
            categoriaProductoRepository.delete(insertedCategoriaProducto);
            insertedCategoriaProducto = null;
        }
    }

    @Test
    @Transactional
    void createCategoriaProducto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);
        var returnedCategoriaProductoDTO = om.readValue(
            restCategoriaProductoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriaProductoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CategoriaProductoDTO.class
        );

        // Validate the CategoriaProducto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCategoriaProducto = categoriaProductoMapper.toEntity(returnedCategoriaProductoDTO);
        assertCategoriaProductoUpdatableFieldsEquals(returnedCategoriaProducto, getPersistedCategoriaProducto(returnedCategoriaProducto));

        insertedCategoriaProducto = returnedCategoriaProducto;
    }

    @Test
    @Transactional
    void createCategoriaProductoWithExistingId() throws Exception {
        // Create the CategoriaProducto with an existing ID
        categoriaProducto.setId(1L);
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriaProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategoriaProductos() throws Exception {
        // Initialize the database
        insertedCategoriaProducto = categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList
        restCategoriaProductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].establecimientoId").value(hasItem(DEFAULT_ESTABLECIMIENTO_ID.intValue())));
    }

    @Test
    @Transactional
    void getCategoriaProducto() throws Exception {
        // Initialize the database
        insertedCategoriaProducto = categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get the categoriaProducto
        restCategoriaProductoMockMvc
            .perform(get(ENTITY_API_URL_ID, categoriaProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaProducto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.establecimientoId").value(DEFAULT_ESTABLECIMIENTO_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCategoriaProducto() throws Exception {
        // Get the categoriaProducto
        restCategoriaProductoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategoriaProducto() throws Exception {
        // Initialize the database
        insertedCategoriaProducto = categoriaProductoRepository.saveAndFlush(categoriaProducto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categoriaProducto
        CategoriaProducto updatedCategoriaProducto = categoriaProductoRepository.findById(categoriaProducto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCategoriaProducto are not directly saved in db
        em.detach(updatedCategoriaProducto);
        updatedCategoriaProducto.nombre(UPDATED_NOMBRE).establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(updatedCategoriaProducto);

        restCategoriaProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaProductoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categoriaProductoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCategoriaProductoToMatchAllProperties(updatedCategoriaProducto);
    }

    @Test
    @Transactional
    void putNonExistingCategoriaProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriaProducto.setId(longCount.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriaProductoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categoriaProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategoriaProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriaProducto.setId(longCount.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categoriaProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategoriaProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriaProducto.setId(longCount.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriaProductoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriaProductoWithPatch() throws Exception {
        // Initialize the database
        insertedCategoriaProducto = categoriaProductoRepository.saveAndFlush(categoriaProducto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categoriaProducto using partial update
        CategoriaProducto partialUpdatedCategoriaProducto = new CategoriaProducto();
        partialUpdatedCategoriaProducto.setId(categoriaProducto.getId());

        partialUpdatedCategoriaProducto.nombre(UPDATED_NOMBRE);

        restCategoriaProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCategoriaProducto))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaProducto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategoriaProductoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCategoriaProducto, categoriaProducto),
            getPersistedCategoriaProducto(categoriaProducto)
        );
    }

    @Test
    @Transactional
    void fullUpdateCategoriaProductoWithPatch() throws Exception {
        // Initialize the database
        insertedCategoriaProducto = categoriaProductoRepository.saveAndFlush(categoriaProducto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categoriaProducto using partial update
        CategoriaProducto partialUpdatedCategoriaProducto = new CategoriaProducto();
        partialUpdatedCategoriaProducto.setId(categoriaProducto.getId());

        partialUpdatedCategoriaProducto.nombre(UPDATED_NOMBRE).establecimientoId(UPDATED_ESTABLECIMIENTO_ID);

        restCategoriaProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategoriaProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCategoriaProducto))
            )
            .andExpect(status().isOk());

        // Validate the CategoriaProducto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategoriaProductoUpdatableFieldsEquals(
            partialUpdatedCategoriaProducto,
            getPersistedCategoriaProducto(partialUpdatedCategoriaProducto)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCategoriaProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriaProducto.setId(longCount.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoriaProductoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(categoriaProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategoriaProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriaProducto.setId(longCount.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(categoriaProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategoriaProducto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categoriaProducto.setId(longCount.incrementAndGet());

        // Create the CategoriaProducto
        CategoriaProductoDTO categoriaProductoDTO = categoriaProductoMapper.toDto(categoriaProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(categoriaProductoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategoriaProducto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategoriaProducto() throws Exception {
        // Initialize the database
        insertedCategoriaProducto = categoriaProductoRepository.saveAndFlush(categoriaProducto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the categoriaProducto
        restCategoriaProductoMockMvc
            .perform(delete(ENTITY_API_URL_ID, categoriaProducto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return categoriaProductoRepository.count();
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

    protected CategoriaProducto getPersistedCategoriaProducto(CategoriaProducto categoriaProducto) {
        return categoriaProductoRepository.findById(categoriaProducto.getId()).orElseThrow();
    }

    protected void assertPersistedCategoriaProductoToMatchAllProperties(CategoriaProducto expectedCategoriaProducto) {
        assertCategoriaProductoAllPropertiesEquals(expectedCategoriaProducto, getPersistedCategoriaProducto(expectedCategoriaProducto));
    }

    protected void assertPersistedCategoriaProductoToMatchUpdatableProperties(CategoriaProducto expectedCategoriaProducto) {
        assertCategoriaProductoAllUpdatablePropertiesEquals(
            expectedCategoriaProducto,
            getPersistedCategoriaProducto(expectedCategoriaProducto)
        );
    }
}
