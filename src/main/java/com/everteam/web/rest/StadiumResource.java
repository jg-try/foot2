package com.everteam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.everteam.domain.Stadium;
import com.everteam.service.StadiumService;
import com.everteam.web.rest.errors.BadRequestAlertException;
import com.everteam.web.rest.util.HeaderUtil;
import com.everteam.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Stadium.
 */
@RestController
@RequestMapping("/api")
public class StadiumResource {

    private final Logger log = LoggerFactory.getLogger(StadiumResource.class);

    private static final String ENTITY_NAME = "stadium";

    private final StadiumService stadiumService;

    public StadiumResource(StadiumService stadiumService) {
        this.stadiumService = stadiumService;
    }

    /**
     * POST  /stadiums : Create a new stadium.
     *
     * @param stadium the stadium to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stadium, or with status 400 (Bad Request) if the stadium has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stadiums")
    @Timed
    public ResponseEntity<Stadium> createStadium(@RequestBody Stadium stadium) throws URISyntaxException {
        log.debug("REST request to save Stadium : {}", stadium);
        if (stadium.getId() != null) {
            throw new BadRequestAlertException("A new stadium cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Stadium result = stadiumService.save(stadium);
        return ResponseEntity.created(new URI("/api/stadiums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stadiums : Updates an existing stadium.
     *
     * @param stadium the stadium to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stadium,
     * or with status 400 (Bad Request) if the stadium is not valid,
     * or with status 500 (Internal Server Error) if the stadium couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stadiums")
    @Timed
    public ResponseEntity<Stadium> updateStadium(@RequestBody Stadium stadium) throws URISyntaxException {
        log.debug("REST request to update Stadium : {}", stadium);
        if (stadium.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Stadium result = stadiumService.save(stadium);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stadium.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stadiums : get all the stadiums.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of stadiums in body
     */
    @GetMapping("/stadiums")
    @Timed
    public ResponseEntity<List<Stadium>> getAllStadiums(Pageable pageable) {
        log.debug("REST request to get a page of Stadiums");
        Page<Stadium> page = stadiumService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stadiums");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /stadiums/:id : get the "id" stadium.
     *
     * @param id the id of the stadium to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stadium, or with status 404 (Not Found)
     */
    @GetMapping("/stadiums/{id}")
    @Timed
    public ResponseEntity<Stadium> getStadium(@PathVariable Long id) {
        log.debug("REST request to get Stadium : {}", id);
        Optional<Stadium> stadium = stadiumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stadium);
    }

    /**
     * DELETE  /stadiums/:id : delete the "id" stadium.
     *
     * @param id the id of the stadium to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stadiums/{id}")
    @Timed
    public ResponseEntity<Void> deleteStadium(@PathVariable Long id) {
        log.debug("REST request to delete Stadium : {}", id);
        stadiumService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
