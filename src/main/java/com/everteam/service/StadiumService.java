package com.everteam.service;

import com.everteam.domain.Stadium;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Stadium.
 */
public interface StadiumService {

    /**
     * Save a stadium.
     *
     * @param stadium the entity to save
     * @return the persisted entity
     */
    Stadium save(Stadium stadium);

    /**
     * Get all the stadiums.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Stadium> findAll(Pageable pageable);


    /**
     * Get the "id" stadium.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Stadium> findOne(Long id);

    /**
     * Delete the "id" stadium.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
