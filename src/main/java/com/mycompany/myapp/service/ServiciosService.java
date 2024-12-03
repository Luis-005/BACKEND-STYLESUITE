package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ServiciosDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Servicios}.
 */
public interface ServiciosService {
    /**
     * Save a servicios.
     *
     * @param serviciosDTO the entity to save.
     * @return the persisted entity.
     */
    ServiciosDTO save(ServiciosDTO serviciosDTO);

    /**
     * Updates a servicios.
     *
     * @param serviciosDTO the entity to update.
     * @return the persisted entity.
     */
    ServiciosDTO update(ServiciosDTO serviciosDTO);

    /**
     * Partially updates a servicios.
     *
     * @param serviciosDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServiciosDTO> partialUpdate(ServiciosDTO serviciosDTO);

    /**
     * Get all the servicios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiciosDTO> findAll(Pageable pageable);

    /**
     * Get the "id" servicios.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiciosDTO> findOne(Long id);

    /**
     * Delete the "id" servicios.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
