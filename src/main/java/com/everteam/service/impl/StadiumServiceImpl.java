package com.everteam.service.impl;

import com.everteam.service.StadiumService;
import com.everteam.domain.Stadium;
import com.everteam.repository.StadiumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Stadium.
 */
@Service
@Transactional
public class StadiumServiceImpl implements StadiumService {

    private final Logger log = LoggerFactory.getLogger(StadiumServiceImpl.class);

    private final StadiumRepository stadiumRepository;

    public StadiumServiceImpl(StadiumRepository stadiumRepository) {
        this.stadiumRepository = stadiumRepository;
    }

    /**
     * Save a stadium.
     *
     * @param stadium the entity to save
     * @return the persisted entity
     */
    @Override
    public Stadium save(Stadium stadium) {
        log.debug("Request to save Stadium : {}", stadium);
        return stadiumRepository.save(stadium);
    }

    /**
     * Get all the stadiums.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Stadium> findAll(Pageable pageable) {
        log.debug("Request to get all Stadiums");
        return stadiumRepository.findAll(pageable);
    }


    /**
     * Get one stadium by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Stadium> findOne(Long id) {
        log.debug("Request to get Stadium : {}", id);
        return stadiumRepository.findById(id);
    }

    /**
     * Delete the stadium by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stadium : {}", id);
        stadiumRepository.deleteById(id);
    }
}
