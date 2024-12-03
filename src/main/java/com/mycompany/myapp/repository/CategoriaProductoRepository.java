package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CategoriaProducto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategoriaProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {}
