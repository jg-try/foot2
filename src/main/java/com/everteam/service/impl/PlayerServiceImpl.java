package com.everteam.service.impl;

import com.everteam.service.PlayerService;
import com.everteam.domain.Player;
import com.everteam.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Player.
 */
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Save a player.
     *
     * @param player the entity to save
     * @return the persisted entity
     */
    @Override
    public Player save(Player player) {
        log.debug("Request to save Player : {}", player);
        return playerRepository.save(player);
    }

    /**
     * Get all the players.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Player> findAll(Pageable pageable) {
        log.debug("Request to get all Players");
        return playerRepository.findAll(pageable);
    }


    /**
     * Get one player by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Player> findOne(Long id) {
        log.debug("Request to get Player : {}", id);
        return playerRepository.findById(id);
    }

    /**
     * Delete the player by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Player : {}", id);
        playerRepository.deleteById(id);
    }
}
