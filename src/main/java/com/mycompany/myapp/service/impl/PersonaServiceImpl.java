package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Persona;
import com.mycompany.myapp.repository.PersonaRepository;
import com.mycompany.myapp.service.PersonaService;
import com.mycompany.myapp.service.dto.PersonaDTO;
import com.mycompany.myapp.service.mapper.PersonaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Persona}.
 */
@Service
@Transactional
public class PersonaServiceImpl implements PersonaService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonaServiceImpl.class);

    private final PersonaRepository personaRepository;

    private final PersonaMapper personaMapper;

    public PersonaServiceImpl(PersonaRepository personaRepository, PersonaMapper personaMapper) {
        this.personaRepository = personaRepository;
        this.personaMapper = personaMapper;
    }

    @Override
    public PersonaDTO save(PersonaDTO personaDTO) {
        LOG.debug("Request to save Persona : {}", personaDTO);
        Persona persona = personaMapper.toEntity(personaDTO);
        persona = personaRepository.save(persona);
        return personaMapper.toDto(persona);
    }

    @Override
    public PersonaDTO update(PersonaDTO personaDTO) {
        LOG.debug("Request to update Persona : {}", personaDTO);
        Persona persona = personaMapper.toEntity(personaDTO);
        persona = personaRepository.save(persona);
        return personaMapper.toDto(persona);
    }

    @Override
    public Optional<PersonaDTO> partialUpdate(PersonaDTO personaDTO) {
        LOG.debug("Request to partially update Persona : {}", personaDTO);

        return personaRepository
            .findById(personaDTO.getId())
            .map(existingPersona -> {
                personaMapper.partialUpdate(existingPersona, personaDTO);

                return existingPersona;
            })
            .map(personaRepository::save)
            .map(personaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Personas");
        return personaRepository.findAll(pageable).map(personaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonaDTO> findOne(Long id) {
        LOG.debug("Request to get Persona : {}", id);
        return personaRepository.findById(id).map(personaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Persona : {}", id);
        personaRepository.deleteById(id);
    }
}
