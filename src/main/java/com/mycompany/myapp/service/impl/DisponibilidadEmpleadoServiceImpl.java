package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.DisponibilidadEmpleado;
import com.mycompany.myapp.repository.DisponibilidadEmpleadoRepository;
import com.mycompany.myapp.service.DisponibilidadEmpleadoService;
import com.mycompany.myapp.service.dto.DisponibilidadEmpleadoDTO;
import com.mycompany.myapp.service.mapper.DisponibilidadEmpleadoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.DisponibilidadEmpleado}.
 */
@Service
@Transactional
public class DisponibilidadEmpleadoServiceImpl implements DisponibilidadEmpleadoService {

    private static final Logger LOG = LoggerFactory.getLogger(DisponibilidadEmpleadoServiceImpl.class);

    private final DisponibilidadEmpleadoRepository disponibilidadEmpleadoRepository;

    private final DisponibilidadEmpleadoMapper disponibilidadEmpleadoMapper;

    public DisponibilidadEmpleadoServiceImpl(
        DisponibilidadEmpleadoRepository disponibilidadEmpleadoRepository,
        DisponibilidadEmpleadoMapper disponibilidadEmpleadoMapper
    ) {
        this.disponibilidadEmpleadoRepository = disponibilidadEmpleadoRepository;
        this.disponibilidadEmpleadoMapper = disponibilidadEmpleadoMapper;
    }

    @Override
    public DisponibilidadEmpleadoDTO save(DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO) {
        LOG.debug("Request to save DisponibilidadEmpleado : {}", disponibilidadEmpleadoDTO);
        DisponibilidadEmpleado disponibilidadEmpleado = disponibilidadEmpleadoMapper.toEntity(disponibilidadEmpleadoDTO);
        disponibilidadEmpleado = disponibilidadEmpleadoRepository.save(disponibilidadEmpleado);
        return disponibilidadEmpleadoMapper.toDto(disponibilidadEmpleado);
    }

    @Override
    public DisponibilidadEmpleadoDTO update(DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO) {
        LOG.debug("Request to update DisponibilidadEmpleado : {}", disponibilidadEmpleadoDTO);
        DisponibilidadEmpleado disponibilidadEmpleado = disponibilidadEmpleadoMapper.toEntity(disponibilidadEmpleadoDTO);
        disponibilidadEmpleado = disponibilidadEmpleadoRepository.save(disponibilidadEmpleado);
        return disponibilidadEmpleadoMapper.toDto(disponibilidadEmpleado);
    }

    @Override
    public Optional<DisponibilidadEmpleadoDTO> partialUpdate(DisponibilidadEmpleadoDTO disponibilidadEmpleadoDTO) {
        LOG.debug("Request to partially update DisponibilidadEmpleado : {}", disponibilidadEmpleadoDTO);

        return disponibilidadEmpleadoRepository
            .findById(disponibilidadEmpleadoDTO.getId())
            .map(existingDisponibilidadEmpleado -> {
                disponibilidadEmpleadoMapper.partialUpdate(existingDisponibilidadEmpleado, disponibilidadEmpleadoDTO);

                return existingDisponibilidadEmpleado;
            })
            .map(disponibilidadEmpleadoRepository::save)
            .map(disponibilidadEmpleadoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisponibilidadEmpleadoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all DisponibilidadEmpleados");
        return disponibilidadEmpleadoRepository.findAll(pageable).map(disponibilidadEmpleadoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DisponibilidadEmpleadoDTO> findOne(Long id) {
        LOG.debug("Request to get DisponibilidadEmpleado : {}", id);
        return disponibilidadEmpleadoRepository.findById(id).map(disponibilidadEmpleadoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete DisponibilidadEmpleado : {}", id);
        disponibilidadEmpleadoRepository.deleteById(id);
    }
}
