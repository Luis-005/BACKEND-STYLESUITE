package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DisponibilidadEmpleadoRepository;
import com.mycompany.myapp.service.DisponibilidadEmpleadoService;
import com.mycompany.myapp.service.dto.DisponibilidadEmpleadoDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DisponibilidadEmpleado}.
 */
@RestController
@RequestMapping("/api/disponibilidad-empleados")
public class DisponibilidadEmpleadoResource {

    private static final Logger LOG = LoggerFactory.getLogger(DisponibilidadEmpleadoResource.class);

    private static final String ENTITY_NAME = "disponibilidadEmpleado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisponibilidadEmpleadoService disponibilidadEmpleadoService;

    private final DisponibilidadEmpleadoRepository disponibilidadEmpleadoRepository;

    public DisponibilidadEmpleadoResource(
        DisponibilidadEmpleadoService disponibilidadEmpleadoService,
        DisponibilidadEmpleadoRepository disponibilidadEmpleadoRepository
    ) {
        this.disponibilidadEmpleadoService = disponibilidadEmpleadoService;
        this.disponibilidadEmpleadoRepository = disponibilidadEmpleadoRepository;
    }

    /**
     * {@code POST  /disponibilidad-empleados} : Create a new disponibilidadEmpleado.
     *
     * @param disponibilidadEmpleadoDTO the disponibilidadEmpleadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disponibilidadEmpleadoDTO, or with status {@code 400 (Bad Request)} if the disponibilidadEmpleado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DisponibilidadEmpleadoDTO> createDisponibilidadEmpleado(
        @RequestBody DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save DisponibilidadEmpleado : {}", disponibilidadEmpleadoDTO);
        if (disponibilidadEmpleadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new disponibilidadEmpleado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        disponibilidadEmpleadoDTO = disponibilidadEmpleadoService.save(disponibilidadEmpleadoDTO);
        return ResponseEntity.created(new URI("/api/disponibilidad-empleados/" + disponibilidadEmpleadoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, disponibilidadEmpleadoDTO.getId().toString()))
            .body(disponibilidadEmpleadoDTO);
    }

    /**
     * {@code PUT  /disponibilidad-empleados/:id} : Updates an existing disponibilidadEmpleado.
     *
     * @param id the id of the disponibilidadEmpleadoDTO to save.
     * @param disponibilidadEmpleadoDTO the disponibilidadEmpleadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disponibilidadEmpleadoDTO,
     * or with status {@code 400 (Bad Request)} if the disponibilidadEmpleadoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disponibilidadEmpleadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadEmpleadoDTO> updateDisponibilidadEmpleado(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DisponibilidadEmpleado : {}, {}", id, disponibilidadEmpleadoDTO);
        if (disponibilidadEmpleadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disponibilidadEmpleadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disponibilidadEmpleadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        disponibilidadEmpleadoDTO = disponibilidadEmpleadoService.update(disponibilidadEmpleadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disponibilidadEmpleadoDTO.getId().toString()))
            .body(disponibilidadEmpleadoDTO);
    }

    /**
     * {@code PATCH  /disponibilidad-empleados/:id} : Partial updates given fields of an existing disponibilidadEmpleado, field will ignore if it is null
     *
     * @param id the id of the disponibilidadEmpleadoDTO to save.
     * @param disponibilidadEmpleadoDTO the disponibilidadEmpleadoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disponibilidadEmpleadoDTO,
     * or with status {@code 400 (Bad Request)} if the disponibilidadEmpleadoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the disponibilidadEmpleadoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the disponibilidadEmpleadoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DisponibilidadEmpleadoDTO> partialUpdateDisponibilidadEmpleado(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DisponibilidadEmpleado partially : {}, {}", id, disponibilidadEmpleadoDTO);
        if (disponibilidadEmpleadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disponibilidadEmpleadoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disponibilidadEmpleadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DisponibilidadEmpleadoDTO> result = disponibilidadEmpleadoService.partialUpdate(disponibilidadEmpleadoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disponibilidadEmpleadoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /disponibilidad-empleados} : get all the disponibilidadEmpleados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disponibilidadEmpleados in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DisponibilidadEmpleadoDTO>> getAllDisponibilidadEmpleados(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of DisponibilidadEmpleados");
        Page<DisponibilidadEmpleadoDTO> page = disponibilidadEmpleadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disponibilidad-empleados/:id} : get the "id" disponibilidadEmpleado.
     *
     * @param id the id of the disponibilidadEmpleadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disponibilidadEmpleadoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadEmpleadoDTO> getDisponibilidadEmpleado(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DisponibilidadEmpleado : {}", id);
        Optional<DisponibilidadEmpleadoDTO> disponibilidadEmpleadoDTO = disponibilidadEmpleadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disponibilidadEmpleadoDTO);
    }

    /**
     * {@code DELETE  /disponibilidad-empleados/:id} : delete the "id" disponibilidadEmpleado.
     *
     * @param id the id of the disponibilidadEmpleadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisponibilidadEmpleado(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DisponibilidadEmpleado : {}", id);
        disponibilidadEmpleadoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
