package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ServiciosAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Servicios;
import com.mycompany.myapp.repository.ServiciosRepository;
import com.mycompany.myapp.service.dto.ServiciosDTO;
import com.mycompany.myapp.service.mapper.ServiciosMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ServiciosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiciosResourceIT {

    private static final BigDecimal DEFAULT_VALOR_SERVICIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_SERVICIO = new BigDecimal(2);

    private static final String DEFAULT_TIPO_SERVICIO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SERVICIO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Long DEFAULT_ESTABLECIMIENTO_ID = 1L;
    private static final Long UPDATED_ESTABLECIMIENTO_ID = 2L;

    private static final String ENTITY_API_URL = "/api/servicios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiciosRepository serviciosRepository;

    @Autowired
    private ServiciosMapper serviciosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiciosMockMvc;

    private Servicios servicios;

    private Servicios insertedServicios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicios createEntity() {
        return new Servicios()
            .valorServicio(DEFAULT_VALOR_SERVICIO)
            .tipoServicio(DEFAULT_TIPO_SERVICIO)
            .descripcion(DEFAULT_DESCRIPCION)
            .establecimientoId(DEFAULT_ESTABLECIMIENTO_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicios createUpdatedEntity() {
        return new Servicios()
            .valorServicio(UPDATED_VALOR_SERVICIO)
            .tipoServicio(UPDATED_TIPO_SERVICIO)
            .descripcion(UPDATED_DESCRIPCION)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
    }

    @BeforeEach
    public void initTest() {
        servicios = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedServicios != null) {
            serviciosRepository.delete(insertedServicios);
            insertedServicios = null;
        }
    }

    @Test
    @Transactional
    void createServicios() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);
        var returnedServiciosDTO = om.readValue(
            restServiciosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviciosDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiciosDTO.class
        );

        // Validate the Servicios in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedServicios = serviciosMapper.toEntity(returnedServiciosDTO);
        assertServiciosUpdatableFieldsEquals(returnedServicios, getPersistedServicios(returnedServicios));

        insertedServicios = returnedServicios;
    }

    @Test
    @Transactional
    void createServiciosWithExistingId() throws Exception {
        // Create the Servicios with an existing ID
        servicios.setId(1L);
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiciosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviciosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServicios() throws Exception {
        // Initialize the database
        insertedServicios = serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList
        restServiciosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorServicio").value(hasItem(sameNumber(DEFAULT_VALOR_SERVICIO))))
            .andExpect(jsonPath("$.[*].tipoServicio").value(hasItem(DEFAULT_TIPO_SERVICIO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].establecimientoId").value(hasItem(DEFAULT_ESTABLECIMIENTO_ID.intValue())));
    }

    @Test
    @Transactional
    void getServicios() throws Exception {
        // Initialize the database
        insertedServicios = serviciosRepository.saveAndFlush(servicios);

        // Get the servicios
        restServiciosMockMvc
            .perform(get(ENTITY_API_URL_ID, servicios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicios.getId().intValue()))
            .andExpect(jsonPath("$.valorServicio").value(sameNumber(DEFAULT_VALOR_SERVICIO)))
            .andExpect(jsonPath("$.tipoServicio").value(DEFAULT_TIPO_SERVICIO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.establecimientoId").value(DEFAULT_ESTABLECIMIENTO_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingServicios() throws Exception {
        // Get the servicios
        restServiciosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServicios() throws Exception {
        // Initialize the database
        insertedServicios = serviciosRepository.saveAndFlush(servicios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicios
        Servicios updatedServicios = serviciosRepository.findById(servicios.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServicios are not directly saved in db
        em.detach(updatedServicios);
        updatedServicios
            .valorServicio(UPDATED_VALOR_SERVICIO)
            .tipoServicio(UPDATED_TIPO_SERVICIO)
            .descripcion(UPDATED_DESCRIPCION)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(updatedServicios);

        restServiciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviciosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviciosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Servicios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiciosToMatchAllProperties(updatedServicios);
    }

    @Test
    @Transactional
    void putNonExistingServicios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicios.setId(longCount.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviciosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicios.setId(longCount.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicios.setId(longCount.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviciosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiciosWithPatch() throws Exception {
        // Initialize the database
        insertedServicios = serviciosRepository.saveAndFlush(servicios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicios using partial update
        Servicios partialUpdatedServicios = new Servicios();
        partialUpdatedServicios.setId(servicios.getId());

        partialUpdatedServicios.valorServicio(UPDATED_VALOR_SERVICIO).tipoServicio(UPDATED_TIPO_SERVICIO);

        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicios))
            )
            .andExpect(status().isOk());

        // Validate the Servicios in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiciosUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServicios, servicios),
            getPersistedServicios(servicios)
        );
    }

    @Test
    @Transactional
    void fullUpdateServiciosWithPatch() throws Exception {
        // Initialize the database
        insertedServicios = serviciosRepository.saveAndFlush(servicios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicios using partial update
        Servicios partialUpdatedServicios = new Servicios();
        partialUpdatedServicios.setId(servicios.getId());

        partialUpdatedServicios
            .valorServicio(UPDATED_VALOR_SERVICIO)
            .tipoServicio(UPDATED_TIPO_SERVICIO)
            .descripcion(UPDATED_DESCRIPCION)
            .establecimientoId(UPDATED_ESTABLECIMIENTO_ID);

        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicios))
            )
            .andExpect(status().isOk());

        // Validate the Servicios in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiciosUpdatableFieldsEquals(partialUpdatedServicios, getPersistedServicios(partialUpdatedServicios));
    }

    @Test
    @Transactional
    void patchNonExistingServicios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicios.setId(longCount.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviciosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicios.setId(longCount.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviciosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicios.setId(longCount.incrementAndGet());

        // Create the Servicios
        ServiciosDTO serviciosDTO = serviciosMapper.toDto(servicios);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiciosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviciosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicios() throws Exception {
        // Initialize the database
        insertedServicios = serviciosRepository.saveAndFlush(servicios);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the servicios
        restServiciosMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicios.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviciosRepository.count();
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

    protected Servicios getPersistedServicios(Servicios servicios) {
        return serviciosRepository.findById(servicios.getId()).orElseThrow();
    }

    protected void assertPersistedServiciosToMatchAllProperties(Servicios expectedServicios) {
        assertServiciosAllPropertiesEquals(expectedServicios, getPersistedServicios(expectedServicios));
    }

    protected void assertPersistedServiciosToMatchUpdatableProperties(Servicios expectedServicios) {
        assertServiciosAllUpdatablePropertiesEquals(expectedServicios, getPersistedServicios(expectedServicios));
    }
}
