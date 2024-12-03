package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CategoriaProductoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CategoriaProducto}.
 */
public interface CategoriaProductoService {
    /**
     * Save a categoriaProducto.
     *
     * @param categoriaProductoDTO the entity to save.
     * @return the persisted entity.
     */
    CategoriaProductoDTO save(CategoriaProductoDTO categoriaProductoDTO);

    /**
     * Updates a categoriaProducto.
     *
     * @param categoriaProductoDTO the entity to update.
     * @return the persisted entity.
     */
    CategoriaProductoDTO update(CategoriaProductoDTO categoriaProductoDTO);

    /**
     * Partially updates a categoriaProducto.
     *
     * @param categoriaProductoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategoriaProductoDTO> partialUpdate(CategoriaProductoDTO categoriaProductoDTO);

    /**
     * Get all the categoriaProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoriaProductoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categoriaProducto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoriaProductoDTO> findOne(Long id);

    /**
     * Delete the "id" categoriaProducto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
