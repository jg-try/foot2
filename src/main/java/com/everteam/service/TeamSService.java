package com.everteam.service;

import com.everteam.domain.TeamS;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing TeamS.
 */
public interface TeamSService {

    /**
     * Save a teamS.
     *
     * @param teamS the entity to save
     * @return the persisted entity
     */
    TeamS save(TeamS teamS);

    /**
     * Get all the teamS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeamS> findAll(Pageable pageable);
    /**
     * Get all the TeamSDTO where Match is null.
     *
     * @return the list of entities
     */
    List<TeamS> findAllWhereMatchIsNull();

    /**
     * Get all the TeamS with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<TeamS> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" teamS.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TeamS> findOne(Long id);

    /**
     * Delete the "id" teamS.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
