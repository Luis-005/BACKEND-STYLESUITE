package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DisponibilidadEmpleado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DisponibilidadEmpleado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisponibilidadEmpleadoRepository extends JpaRepository<DisponibilidadEmpleado, Long> {}
