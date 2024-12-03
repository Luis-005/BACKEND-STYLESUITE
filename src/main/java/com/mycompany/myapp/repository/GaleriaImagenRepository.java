package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GaleriaImagen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GaleriaImagen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GaleriaImagenRepository extends JpaRepository<GaleriaImagen, Long> {}
