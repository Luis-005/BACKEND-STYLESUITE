package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Establecimiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Establecimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Long> {}
