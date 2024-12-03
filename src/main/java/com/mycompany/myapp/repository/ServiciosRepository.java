package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Servicios;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Servicios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiciosRepository extends JpaRepository<Servicios, Long> {}
