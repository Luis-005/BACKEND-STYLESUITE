package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.GaleriaImagen;
import com.mycompany.myapp.repository.GaleriaImagenRepository;
import com.mycompany.myapp.service.GaleriaImagenService;
import com.mycompany.myapp.service.dto.GaleriaImagenDTO;
import com.mycompany.myapp.service.mapper.GaleriaImagenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.GaleriaImagen}.
 */
@Service
@Transactional
public class GaleriaImagenServiceImpl implements GaleriaImagenService {

    private static final Logger LOG = LoggerFactory.getLogger(GaleriaImagenServiceImpl.class);

    private final GaleriaImagenRepository galeriaImagenRepository;

    private final GaleriaImagenMapper galeriaImagenMapper;

    public GaleriaImagenServiceImpl(GaleriaImagenRepository galeriaImagenRepository, GaleriaImagenMapper galeriaImagenMapper) {
        this.galeriaImagenRepository = galeriaImagenRepository;
        this.galeriaImagenMapper = galeriaImagenMapper;
    }

    @Override
    public GaleriaImagenDTO save(GaleriaImagenDTO galeriaImagenDTO) {
        LOG.debug("Request to save GaleriaImagen : {}", galeriaImagenDTO);
        GaleriaImagen galeriaImagen = galeriaImagenMapper.toEntity(galeriaImagenDTO);
        galeriaImagen = galeriaImagenRepository.save(galeriaImagen);
        return galeriaImagenMapper.toDto(galeriaImagen);
    }

    @Override
    public GaleriaImagenDTO update(GaleriaImagenDTO galeriaImagenDTO) {
        LOG.debug("Request to update GaleriaImagen : {}", galeriaImagenDTO);
        GaleriaImagen galeriaImagen = galeriaImagenMapper.toEntity(galeriaImagenDTO);
        galeriaImagen = galeriaImagenRepository.save(galeriaImagen);
        return galeriaImagenMapper.toDto(galeriaImagen);
    }

    @Override
    public Optional<GaleriaImagenDTO> partialUpdate(GaleriaImagenDTO galeriaImagenDTO) {
        LOG.debug("Request to partially update GaleriaImagen : {}", galeriaImagenDTO);

        return galeriaImagenRepository
            .findById(galeriaImagenDTO.getId())
            .map(existingGaleriaImagen -> {
                galeriaImagenMapper.partialUpdate(existingGaleriaImagen, galeriaImagenDTO);

                return existingGaleriaImagen;
            })
            .map(galeriaImagenRepository::save)
            .map(galeriaImagenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GaleriaImagenDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all GaleriaImagens");
        return galeriaImagenRepository.findAll(pageable).map(galeriaImagenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GaleriaImagenDTO> findOne(Long id) {
        LOG.debug("Request to get GaleriaImagen : {}", id);
        return galeriaImagenRepository.findById(id).map(galeriaImagenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete GaleriaImagen : {}", id);
        galeriaImagenRepository.deleteById(id);
    }
}
