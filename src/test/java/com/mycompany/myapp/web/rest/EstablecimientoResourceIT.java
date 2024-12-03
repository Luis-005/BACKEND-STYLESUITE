package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.EstablecimientoAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Establecimiento;
import com.mycompany.myapp.repository.EstablecimientoRepository;
import com.mycompany.myapp.service.dto.EstablecimientoDTO;
import com.mycompany.myapp.service.mapper.EstablecimientoMapper;
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
 * Integration tests for the {@link EstablecimientoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstablecimientoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO_ELECTRONICO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_ELECTRONICO = "BBBBBBBBBB";

    private static final String DEFAULT_URL_IMG = "AAAAAAAAAA";
    private static final String UPDATED_URL_IMG = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_APERTURA = "AAAAA";
    private static final String UPDATED_HORA_APERTURA = "BBBBB";

    private static final String DEFAULT_HORA_CIERRE = "AAAAA";
    private static final String UPDATED_HORA_CIERRE = "BBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String ENTITY_API_URL = "/api/establecimientos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private EstablecimientoMapper establecimientoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstablecimientoMockMvc;

    private Establecimiento establecimiento;

    private Establecimiento insertedEstablecimiento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Establecimiento createEntity() {
        return new Establecimiento()
            .nombre(DEFAULT_NOMBRE)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO)
            .correoElectronico(DEFAULT_CORREO_ELECTRONICO)
            .urlImg(DEFAULT_URL_IMG)
            .horaApertura(DEFAULT_HORA_APERTURA)
            .horaCierre(DEFAULT_HORA_CIERRE)
            .userId(DEFAULT_USER_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Establecimiento createUpdatedEntity() {
        return new Establecimiento()
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .urlImg(UPDATED_URL_IMG)
            .horaApertura(UPDATED_HORA_APERTURA)
            .horaCierre(UPDATED_HORA_CIERRE)
            .userId(UPDATED_USER_ID);
    }

    @BeforeEach
    public void initTest() {
        establecimiento = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEstablecimiento != null) {
            establecimientoRepository.delete(insertedEstablecimiento);
            insertedEstablecimiento = null;
        }
    }

    @Test
    @Transactional
    void createEstablecimiento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Establecimiento
        EstablecimientoDTO establecimientoDTO = establecimientoMapper.toDto(establecimiento);
        var returnedEstablecimientoDTO = om.readValue(
            restEstablecimientoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(establecimientoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EstablecimientoDTO.class
        );

        // Validate the Establecimiento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEstablecimiento = establecimientoMapper.toEntity(returnedEstablecimientoDTO);
        assertEstablecimientoUpdatableFieldsEquals(returnedEstablecimiento, getPersistedEstablecimiento(returnedEstablecimiento));

        insertedEstablecimiento = returnedEstablecimiento;
    }

    @Test
    @Transactional
    void createEstablecimientoWithExistingId() throws Exception {
        // Create the Establecimiento with an existing ID
        establecimiento.setId(1L);
        EstablecimientoDTO establecimientoDTO = establecimientoMapper.toDto(establecimiento);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstablecimientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(establecimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Establecimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstablecimientos() throws Exception {
        // Initialize the database
        insertedEstablecimiento = establecimientoRepository.saveAndFlush(establecimiento);

        // Get all the establecimientoList
        restEstablecimientoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(establecimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO)))
            .andExpect(jsonPath("$.[*].urlImg").value(hasItem(DEFAULT_URL_IMG)))
            .andExpect(jsonPath("$.[*].horaApertura").value(hasItem(DEFAULT_HORA_APERTURA)))
            .andExpect(jsonPath("$.[*].horaCierre").value(hasItem(DEFAULT_HORA_CIERRE)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    void getEstablecimiento() throws Exception {
        // Initialize the database
        insertedEstablecimiento = establecimientoRepository.saveAndFlush(establecimiento);

        // Get the establecimiento
        restEstablecimientoMockMvc
            .perform(get(ENTITY_API_URL_ID, establecimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(establecimiento.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correoElectronico").value(DEFAULT_CORREO_ELECTRONICO))
            .andExpect(jsonPath("$.urlImg").value(DEFAULT_URL_IMG))
            .andExpect(jsonPath("$.horaApertura").value(DEFAULT_HORA_APERTURA))
            .andExpect(jsonPath("$.horaCierre").value(DEFAULT_HORA_CIERRE))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEstablecimiento() throws Exception {
        // Get the establecimiento
        restEstablecimientoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstablecimiento() throws Exception {
        // Initialize the database
        insertedEstablecimiento = establecimientoRepository.saveAndFlush(establecimiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the establecimiento
        Establecimiento updatedEstablecimiento = establecimientoRepository.findById(establecimiento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEstablecimiento are not directly saved in db
        em.detach(updatedEstablecimiento);
        updatedEstablecimiento
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .urlImg(UPDATED_URL_IMG)
            .horaApertura(UPDATED_HORA_APERTURA)
            .horaCierre(UPDATED_HORA_CIERRE)
            .userId(UPDATED_USER_ID);
        EstablecimientoDTO establecimientoDTO = establecimientoMapper.toDto(updatedEstablecimiento);

        restEstablecimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, establecimientoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(establecimientoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Establecimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEstablecimientoToMatchAllProperties(updatedEstablecimiento);
    }

    @Test
    @Transactional
    void putNonExistingEstablecimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        establecimiento.setId(longCount.incrementAndGet());

        // Create the Establecimiento
        EstablecimientoDTO establecimientoDTO = establecimientoMapper.toDto(establecimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablecimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, establecimientoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(establecimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establecimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstablecimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        establecimiento.setId(longCount.incrementAndGet());

        // Create the Establecimiento
        EstablecimientoDTO establecimientoDTO = establecimientoMapper.toDto(establecimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablecimientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(establecimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establecimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstablecimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        establecimiento.setId(longCount.incrementAndGet());

        // Create the Establecimiento
        EstablecimientoDTO establecimientoDTO = establecimientoMapper.toDto(establecimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablecimientoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(establecimientoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Establecimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstablecimientoWithPatch() throws Exception {
        // Initialize the database
        insertedEstablecimiento = establecimientoRepository.saveAndFlush(establecimiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the establecimiento using partial update
        Establecimiento partialUpdatedEstablecimiento = new Establecimiento();
        partialUpdatedEstablecimiento.setId(establecimiento.getId());

        partialUpdatedEstablecimiento
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .urlImg(UPDATED_URL_IMG)
            .horaCierre(UPDATED_HORA_CIERRE);

        restEstablecimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstablecimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEstablecimiento))
            )
            .andExpect(status().isOk());

        // Validate the Establecimiento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEstablecimientoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEstablecimiento, establecimiento),
            getPersistedEstablecimiento(establecimiento)
        );
    }

    @Test
    @Transactional
    void fullUpdateEstablecimientoWithPatch() throws Exception {
        // Initialize the database
        insertedEstablecimiento = establecimientoRepository.saveAndFlush(establecimiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the establecimiento using partial update
        Establecimiento partialUpdatedEstablecimiento = new Establecimiento();
        partialUpdatedEstablecimiento.setId(establecimiento.getId());

        partialUpdatedEstablecimiento
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO)
            .urlImg(UPDATED_URL_IMG)
            .horaApertura(UPDATED_HORA_APERTURA)
            .horaCierre(UPDATED_HORA_CIERRE)
            .userId(UPDATED_USER_ID);

        restEstablecimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstablecimiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEstablecimiento))
            )
            .andExpect(status().isOk());

        // Validate the Establecimiento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEstablecimientoUpdatableFieldsEquals(
            partialUpdatedEstablecimiento,
            getPersistedEstablecimiento(partialUpdatedEstablecimiento)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEstablecimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        establecimiento.setId(longCount.incrementAndGet());

        // Create the Establecimiento
        EstablecimientoDTO establecimientoDTO = establecimientoMapper.toDto(establecimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablecimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, establecimientoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(establecimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establecimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstablecimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        establecimiento.setId(longCount.incrementAndGet());

        // Create the Establecimiento
        EstablecimientoDTO establecimientoDTO = establecimientoMapper.toDto(establecimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablecimientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(establecimientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establecimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstablecimiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        establecimiento.setId(longCount.incrementAndGet());

        // Create the Establecimiento
        EstablecimientoDTO establecimientoDTO = establecimientoMapper.toDto(establecimiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablecimientoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(establecimientoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Establecimiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstablecimiento() throws Exception {
        // Initialize the database
        insertedEstablecimiento = establecimientoRepository.saveAndFlush(establecimiento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the establecimiento
        restEstablecimientoMockMvc
            .perform(delete(ENTITY_API_URL_ID, establecimiento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return establecimientoRepository.count();
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

    protected Establecimiento getPersistedEstablecimiento(Establecimiento establecimiento) {
        return establecimientoRepository.findById(establecimiento.getId()).orElseThrow();
    }

    protected void assertPersistedEstablecimientoToMatchAllProperties(Establecimiento expectedEstablecimiento) {
        assertEstablecimientoAllPropertiesEquals(expectedEstablecimiento, getPersistedEstablecimiento(expectedEstablecimiento));
    }

    protected void assertPersistedEstablecimientoToMatchUpdatableProperties(Establecimiento expectedEstablecimiento) {
        assertEstablecimientoAllUpdatablePropertiesEquals(expectedEstablecimiento, getPersistedEstablecimiento(expectedEstablecimiento));
    }
}
