package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EstablecimientoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Establecimiento}.
 */
public interface EstablecimientoService {
    /**
     * Save a establecimiento.
     *
     * @param establecimientoDTO the entity to save.
     * @return the persisted entity.
     */
    EstablecimientoDTO save(EstablecimientoDTO establecimientoDTO);

    /**
     * Updates a establecimiento.
     *
     * @param establecimientoDTO the entity to update.
     * @return the persisted entity.
     */
    EstablecimientoDTO update(EstablecimientoDTO establecimientoDTO);

    /**
     * Partially updates a establecimiento.
     *
     * @param establecimientoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstablecimientoDTO> partialUpdate(EstablecimientoDTO establecimientoDTO);

    /**
     * Get all the establecimientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstablecimientoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" establecimiento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstablecimientoDTO> findOne(Long id);

    /**
     * Delete the "id" establecimiento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
