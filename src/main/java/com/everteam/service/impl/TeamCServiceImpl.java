package com.everteam.service.impl;

import com.everteam.service.TeamCService;
import com.everteam.domain.TeamC;
import com.everteam.repository.TeamCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing TeamC.
 */
@Service
@Transactional
public class TeamCServiceImpl implements TeamCService {

    private final Logger log = LoggerFactory.getLogger(TeamCServiceImpl.class);

    private final TeamCRepository teamCRepository;

    public TeamCServiceImpl(TeamCRepository teamCRepository) {
        this.teamCRepository = teamCRepository;
    }

    /**
     * Save a teamC.
     *
     * @param teamC the entity to save
     * @return the persisted entity
     */
    @Override
    public TeamC save(TeamC teamC) {
        log.debug("Request to save TeamC : {}", teamC);
        return teamCRepository.save(teamC);
    }

    /**
     * Get all the teamCS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamC> findAll(Pageable pageable) {
        log.debug("Request to get all TeamCS");
        return teamCRepository.findAll(pageable);
    }

    /**
     * Get all the TeamC with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<TeamC> findAllWithEagerRelationships(Pageable pageable) {
        return teamCRepository.findAllWithEagerRelationships(pageable);
    }
    


    /**
     *  get all the teamCS where Match is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TeamC> findAllWhereMatchIsNull() {
        log.debug("Request to get all teamCS where Match is null");
        return StreamSupport
            .stream(teamCRepository.findAll().spliterator(), false)
            .filter(teamC -> teamC.getMatch() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one teamC by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TeamC> findOne(Long id) {
        log.debug("Request to get TeamC : {}", id);
        return teamCRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the teamC by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeamC : {}", id);
        teamCRepository.deleteById(id);
    }
}
