package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PersonaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Persona}.
 */
public interface PersonaService {
    /**
     * Save a persona.
     *
     * @param personaDTO the entity to save.
     * @return the persisted entity.
     */
    PersonaDTO save(PersonaDTO personaDTO);

    /**
     * Updates a persona.
     *
     * @param personaDTO the entity to update.
     * @return the persisted entity.
     */
    PersonaDTO update(PersonaDTO personaDTO);

    /**
     * Partially updates a persona.
     *
     * @param personaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonaDTO> partialUpdate(PersonaDTO personaDTO);

    /**
     * Get all the personas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" persona.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonaDTO> findOne(Long id);

    /**
     * Delete the "id" persona.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
