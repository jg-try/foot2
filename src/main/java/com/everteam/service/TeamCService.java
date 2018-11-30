package com.everteam.service;

import com.everteam.domain.TeamC;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing TeamC.
 */
public interface TeamCService {

    /**
     * Save a teamC.
     *
     * @param teamC the entity to save
     * @return the persisted entity
     */
    TeamC save(TeamC teamC);

    /**
     * Get all the teamCS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeamC> findAll(Pageable pageable);
    /**
     * Get all the TeamCDTO where Match is null.
     *
     * @return the list of entities
     */
    List<TeamC> findAllWhereMatchIsNull();

    /**
     * Get all the TeamC with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<TeamC> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" teamC.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TeamC> findOne(Long id);

    /**
     * Delete the "id" teamC.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
