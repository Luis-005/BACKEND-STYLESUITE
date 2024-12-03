package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Establecimiento;
import com.mycompany.myapp.repository.EstablecimientoRepository;
import com.mycompany.myapp.service.EstablecimientoService;
import com.mycompany.myapp.service.dto.EstablecimientoDTO;
import com.mycompany.myapp.service.mapper.EstablecimientoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Establecimiento}.
 */
@Service
@Transactional
public class EstablecimientoServiceImpl implements EstablecimientoService {

    private static final Logger LOG = LoggerFactory.getLogger(EstablecimientoServiceImpl.class);

    private final EstablecimientoRepository establecimientoRepository;

    private final EstablecimientoMapper establecimientoMapper;

    public EstablecimientoServiceImpl(EstablecimientoRepository establecimientoRepository, EstablecimientoMapper establecimientoMapper) {
        this.establecimientoRepository = establecimientoRepository;
        this.establecimientoMapper = establecimientoMapper;
    }

    @Override
    public EstablecimientoDTO save(EstablecimientoDTO establecimientoDTO) {
        LOG.debug("Request to save Establecimiento : {}", establecimientoDTO);
        Establecimiento establecimiento = establecimientoMapper.toEntity(establecimientoDTO);
        establecimiento = establecimientoRepository.save(establecimiento);
        return establecimientoMapper.toDto(establecimiento);
    }

    @Override
    public EstablecimientoDTO update(EstablecimientoDTO establecimientoDTO) {
        LOG.debug("Request to update Establecimiento : {}", establecimientoDTO);
        Establecimiento establecimiento = establecimientoMapper.toEntity(establecimientoDTO);
        establecimiento = establecimientoRepository.save(establecimiento);
        return establecimientoMapper.toDto(establecimiento);
    }

    @Override
    public Optional<EstablecimientoDTO> partialUpdate(EstablecimientoDTO establecimientoDTO) {
        LOG.debug("Request to partially update Establecimiento : {}", establecimientoDTO);

        return establecimientoRepository
            .findById(establecimientoDTO.getId())
            .map(existingEstablecimiento -> {
                establecimientoMapper.partialUpdate(existingEstablecimiento, establecimientoDTO);

                return existingEstablecimiento;
            })
            .map(establecimientoRepository::save)
            .map(establecimientoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstablecimientoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Establecimientos");
        return establecimientoRepository.findAll(pageable).map(establecimientoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstablecimientoDTO> findOne(Long id) {
        LOG.debug("Request to get Establecimiento : {}", id);
        return establecimientoRepository.findById(id).map(establecimientoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Establecimiento : {}", id);
        establecimientoRepository.deleteById(id);
    }
}
