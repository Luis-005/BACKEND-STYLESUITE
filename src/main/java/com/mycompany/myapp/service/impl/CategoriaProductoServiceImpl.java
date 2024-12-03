package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CategoriaProducto;
import com.mycompany.myapp.repository.CategoriaProductoRepository;
import com.mycompany.myapp.service.CategoriaProductoService;
import com.mycompany.myapp.service.dto.CategoriaProductoDTO;
import com.mycompany.myapp.service.mapper.CategoriaProductoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CategoriaProducto}.
 */
@Service
@Transactional
public class CategoriaProductoServiceImpl implements CategoriaProductoService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoriaProductoServiceImpl.class);

    private final CategoriaProductoRepository categoriaProductoRepository;

    private final CategoriaProductoMapper categoriaProductoMapper;

    public CategoriaProductoServiceImpl(
        CategoriaProductoRepository categoriaProductoRepository,
        CategoriaProductoMapper categoriaProductoMapper
    ) {
        this.categoriaProductoRepository = categoriaProductoRepository;
        this.categoriaProductoMapper = categoriaProductoMapper;
    }

    @Override
    public CategoriaProductoDTO save(CategoriaProductoDTO categoriaProductoDTO) {
        LOG.debug("Request to save CategoriaProducto : {}", categoriaProductoDTO);
        CategoriaProducto categoriaProducto = categoriaProductoMapper.toEntity(categoriaProductoDTO);
        categoriaProducto = categoriaProductoRepository.save(categoriaProducto);
        return categoriaProductoMapper.toDto(categoriaProducto);
    }

    @Override
    public CategoriaProductoDTO update(CategoriaProductoDTO categoriaProductoDTO) {
        LOG.debug("Request to update CategoriaProducto : {}", categoriaProductoDTO);
        CategoriaProducto categoriaProducto = categoriaProductoMapper.toEntity(categoriaProductoDTO);
        categoriaProducto = categoriaProductoRepository.save(categoriaProducto);
        return categoriaProductoMapper.toDto(categoriaProducto);
    }

    @Override
    public Optional<CategoriaProductoDTO> partialUpdate(CategoriaProductoDTO categoriaProductoDTO) {
        LOG.debug("Request to partially update CategoriaProducto : {}", categoriaProductoDTO);

        return categoriaProductoRepository
            .findById(categoriaProductoDTO.getId())
            .map(existingCategoriaProducto -> {
                categoriaProductoMapper.partialUpdate(existingCategoriaProducto, categoriaProductoDTO);

                return existingCategoriaProducto;
            })
            .map(categoriaProductoRepository::save)
            .map(categoriaProductoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaProductoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CategoriaProductos");
        return categoriaProductoRepository.findAll(pageable).map(categoriaProductoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaProductoDTO> findOne(Long id) {
        LOG.debug("Request to get CategoriaProducto : {}", id);
        return categoriaProductoRepository.findById(id).map(categoriaProductoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CategoriaProducto : {}", id);
        categoriaProductoRepository.deleteById(id);
    }
}
