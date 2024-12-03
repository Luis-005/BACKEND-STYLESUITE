package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DisponibilidadEmpleadoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.DisponibilidadEmpleado}.
 */
public interface DisponibilidadEmpleadoService {
    /**
     * Save a disponibilidadEmpleado.
     *
     * @param disponibilidadEmpleadoDTO the entity to save.
     * @return the persisted entity.
     */
    DisponibilidadEmpleadoDTO save(DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO);

    /**
     * Updates a disponibilidadEmpleado.
     *
     * @param disponibilidadEmpleadoDTO the entity to update.
     * @return the persisted entity.
     */
    DisponibilidadEmpleadoDTO update(DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO);

    /**
     * Partially updates a disponibilidadEmpleado.
     *
     * @param disponibilidadEmpleadoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DisponibilidadEmpleadoDTO> partialUpdate(DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO);

    /**
     * Get all the disponibilidadEmpleados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisponibilidadEmpleadoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" disponibilidadEmpleado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DisponibilidadEmpleadoDTO> findOne(Long id);

    /**
     * Delete the "id" disponibilidadEmpleado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
