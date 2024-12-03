package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.GaleriaImagenRepository;
import com.mycompany.myapp.service.GaleriaImagenService;
import com.mycompany.myapp.service.dto.GaleriaImagenDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.GaleriaImagen}.
 */
@RestController
@RequestMapping("/api/galeria-imagens")
public class GaleriaImagenResource {

    private static final Logger LOG = LoggerFactory.getLogger(GaleriaImagenResource.class);

    private static final String ENTITY_NAME = "galeriaImagen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GaleriaImagenService galeriaImagenService;

    private final GaleriaImagenRepository galeriaImagenRepository;

    public GaleriaImagenResource(GaleriaImagenService galeriaImagenService, GaleriaImagenRepository galeriaImagenRepository) {
        this.galeriaImagenService = galeriaImagenService;
        this.galeriaImagenRepository = galeriaImagenRepository;
    }

    /**
     * {@code POST  /galeria-imagens} : Create a new galeriaImagen.
     *
     * @param galeriaImagenDTO the galeriaImagenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new galeriaImagenDTO, or with status {@code 400 (Bad Request)} if the galeriaImagen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GaleriaImagenDTO> createGaleriaImagen(@RequestBody GaleriaImagenDTO galeriaImagenDTO) throws URISyntaxException {
        LOG.debug("REST request to save GaleriaImagen : {}", galeriaImagenDTO);
        if (galeriaImagenDTO.getId() != null) {
            throw new BadRequestAlertException("A new galeriaImagen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        galeriaImagenDTO = galeriaImagenService.save(galeriaImagenDTO);
        return ResponseEntity.created(new URI("/api/galeria-imagens/" + galeriaImagenDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, galeriaImagenDTO.getId().toString()))
            .body(galeriaImagenDTO);
    }

    /**
     * {@code PUT  /galeria-imagens/:id} : Updates an existing galeriaImagen.
     *
     * @param id the id of the galeriaImagenDTO to save.
     * @param galeriaImagenDTO the galeriaImagenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated galeriaImagenDTO,
     * or with status {@code 400 (Bad Request)} if the galeriaImagenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the galeriaImagenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GaleriaImagenDTO> updateGaleriaImagen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GaleriaImagenDTO galeriaImagenDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update GaleriaImagen : {}, {}", id, galeriaImagenDTO);
        if (galeriaImagenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, galeriaImagenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!galeriaImagenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        galeriaImagenDTO = galeriaImagenService.update(galeriaImagenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, galeriaImagenDTO.getId().toString()))
            .body(galeriaImagenDTO);
    }

    /**
     * {@code PATCH  /galeria-imagens/:id} : Partial updates given fields of an existing galeriaImagen, field will ignore if it is null
     *
     * @param id the id of the galeriaImagenDTO to save.
     * @param galeriaImagenDTO the galeriaImagenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated galeriaImagenDTO,
     * or with status {@code 400 (Bad Request)} if the galeriaImagenDTO is not valid,
     * or with status {@code 404 (Not Found)} if the galeriaImagenDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the galeriaImagenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GaleriaImagenDTO> partialUpdateGaleriaImagen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GaleriaImagenDTO galeriaImagenDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update GaleriaImagen partially : {}, {}", id, galeriaImagenDTO);
        if (galeriaImagenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, galeriaImagenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!galeriaImagenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GaleriaImagenDTO> result = galeriaImagenService.partialUpdate(galeriaImagenDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, galeriaImagenDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /galeria-imagens} : get all the galeriaImagens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of galeriaImagens in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GaleriaImagenDTO>> getAllGaleriaImagens(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of GaleriaImagens");
        Page<GaleriaImagenDTO> page = galeriaImagenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /galeria-imagens/:id} : get the "id" galeriaImagen.
     *
     * @param id the id of the galeriaImagenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the galeriaImagenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GaleriaImagenDTO> getGaleriaImagen(@PathVariable("id") Long id) {
        LOG.debug("REST request to get GaleriaImagen : {}", id);
        Optional<GaleriaImagenDTO> galeriaImagenDTO = galeriaImagenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(galeriaImagenDTO);
    }

    /**
     * {@code DELETE  /galeria-imagens/:id} : delete the "id" galeriaImagen.
     *
     * @param id the id of the galeriaImagenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGaleriaImagen(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete GaleriaImagen : {}", id);
        galeriaImagenService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
