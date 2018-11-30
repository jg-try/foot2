package com.everteam.service.impl;

import com.everteam.service.TeamSService;
import com.everteam.domain.TeamS;
import com.everteam.repository.TeamSRepository;
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
 * Service Implementation for managing TeamS.
 */
@Service
@Transactional
public class TeamSServiceImpl implements TeamSService {

    private final Logger log = LoggerFactory.getLogger(TeamSServiceImpl.class);

    private final TeamSRepository teamSRepository;

    public TeamSServiceImpl(TeamSRepository teamSRepository) {
        this.teamSRepository = teamSRepository;
    }

    /**
     * Save a teamS.
     *
     * @param teamS the entity to save
     * @return the persisted entity
     */
    @Override
    public TeamS save(TeamS teamS) {
        log.debug("Request to save TeamS : {}", teamS);
        return teamSRepository.save(teamS);
    }

    /**
     * Get all the teamS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamS> findAll(Pageable pageable) {
        log.debug("Request to get all TeamS");
        return teamSRepository.findAll(pageable);
    }

    /**
     * Get all the TeamS with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<TeamS> findAllWithEagerRelationships(Pageable pageable) {
        return teamSRepository.findAllWithEagerRelationships(pageable);
    }
    


    /**
     *  get all the teamS where Match is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TeamS> findAllWhereMatchIsNull() {
        log.debug("Request to get all teamS where Match is null");
        return StreamSupport
            .stream(teamSRepository.findAll().spliterator(), false)
            .filter(teamS -> teamS.getMatch() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one teamS by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TeamS> findOne(Long id) {
        log.debug("Request to get TeamS : {}", id);
        return teamSRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the teamS by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeamS : {}", id);
        teamSRepository.deleteById(id);
    }
}
