package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ServiciosRepository;
import com.mycompany.myapp.service.ServiciosService;
import com.mycompany.myapp.service.dto.ServiciosDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Servicios}.
 */
@RestController
@RequestMapping("/api/servicios")
public class ServiciosResource {

    private static final Logger LOG = LoggerFactory.getLogger(ServiciosResource.class);

    private static final String ENTITY_NAME = "servicios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiciosService serviciosService;

    private final ServiciosRepository serviciosRepository;

    public ServiciosResource(ServiciosService serviciosService, ServiciosRepository serviciosRepository) {
        this.serviciosService = serviciosService;
        this.serviciosRepository = serviciosRepository;
    }

    /**
     * {@code POST  /servicios} : Create a new servicios.
     *
     * @param serviciosDTO the serviciosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviciosDTO, or with status {@code 400 (Bad Request)} if the servicios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiciosDTO> createServicios(@RequestBody ServiciosDTO serviciosDTO) throws URISyntaxException {
        LOG.debug("REST request to save Servicios : {}", serviciosDTO);
        if (serviciosDTO.getId() != null) {
            throw new BadRequestAlertException("A new servicios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviciosDTO = serviciosService.save(serviciosDTO);
        return ResponseEntity.created(new URI("/api/servicios/" + serviciosDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviciosDTO.getId().toString()))
            .body(serviciosDTO);
    }

    /**
     * {@code PUT  /servicios/:id} : Updates an existing servicios.
     *
     * @param id the id of the serviciosDTO to save.
     * @param serviciosDTO the serviciosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviciosDTO,
     * or with status {@code 400 (Bad Request)} if the serviciosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviciosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiciosDTO> updateServicios(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ServiciosDTO serviciosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Servicios : {}, {}", id, serviciosDTO);
        if (serviciosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviciosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviciosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviciosDTO = serviciosService.update(serviciosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviciosDTO.getId().toString()))
            .body(serviciosDTO);
    }

    /**
     * {@code PATCH  /servicios/:id} : Partial updates given fields of an existing servicios, field will ignore if it is null
     *
     * @param id the id of the serviciosDTO to save.
     * @param serviciosDTO the serviciosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviciosDTO,
     * or with status {@code 400 (Bad Request)} if the serviciosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviciosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviciosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiciosDTO> partialUpdateServicios(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ServiciosDTO serviciosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Servicios partially : {}, {}", id, serviciosDTO);
        if (serviciosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviciosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviciosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiciosDTO> result = serviciosService.partialUpdate(serviciosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviciosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /servicios} : get all the servicios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiciosDTO>> getAllServicios(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Servicios");
        Page<ServiciosDTO> page = serviciosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /servicios/:id} : get the "id" servicios.
     *
     * @param id the id of the serviciosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviciosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiciosDTO> getServicios(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Servicios : {}", id);
        Optional<ServiciosDTO> serviciosDTO = serviciosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviciosDTO);
    }

    /**
     * {@code DELETE  /servicios/:id} : delete the "id" servicios.
     *
     * @param id the id of the serviciosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicios(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Servicios : {}", id);
        serviciosService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
