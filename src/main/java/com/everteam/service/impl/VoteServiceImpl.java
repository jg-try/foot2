package com.everteam.service.impl;

import com.everteam.service.VoteService;
import com.everteam.domain.Vote;
import com.everteam.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Vote.
 */
@Service
@Transactional
public class VoteServiceImpl implements VoteService {

    private final Logger log = LoggerFactory.getLogger(VoteServiceImpl.class);

    private final VoteRepository voteRepository;

    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    /**
     * Save a vote.
     *
     * @param vote the entity to save
     * @return the persisted entity
     */
    @Override
    public Vote save(Vote vote) {
        log.debug("Request to save Vote : {}", vote);
        return voteRepository.save(vote);
    }

    /**
     * Get all the votes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Vote> findAll(Pageable pageable) {
        log.debug("Request to get all Votes");
        return voteRepository.findAll(pageable);
    }


    /**
     * Get one vote by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vote> findOne(Long id) {
        log.debug("Request to get Vote : {}", id);
        return voteRepository.findById(id);
    }

    /**
     * Delete the vote by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vote : {}", id);
        voteRepository.deleteById(id);
    }
}
