package com.everteam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.everteam.domain.TeamS;
import com.everteam.service.TeamSService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing TeamS.
 */
@RestController
@RequestMapping("/api")
public class TeamSResource {

    private final Logger log = LoggerFactory.getLogger(TeamSResource.class);

    private static final String ENTITY_NAME = "teamS";

    private final TeamSService teamSService;

    public TeamSResource(TeamSService teamSService) {
        this.teamSService = teamSService;
    }

    /**
     * POST  /team-s : Create a new teamS.
     *
     * @param teamS the teamS to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teamS, or with status 400 (Bad Request) if the teamS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/team-s")
    @Timed
    public ResponseEntity<TeamS> createTeamS(@RequestBody TeamS teamS) throws URISyntaxException {
        log.debug("REST request to save TeamS : {}", teamS);
        if (teamS.getId() != null) {
            throw new BadRequestAlertException("A new teamS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamS result = teamSService.save(teamS);
        return ResponseEntity.created(new URI("/api/team-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /team-s : Updates an existing teamS.
     *
     * @param teamS the teamS to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teamS,
     * or with status 400 (Bad Request) if the teamS is not valid,
     * or with status 500 (Internal Server Error) if the teamS couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/team-s")
    @Timed
    public ResponseEntity<TeamS> updateTeamS(@RequestBody TeamS teamS) throws URISyntaxException {
        log.debug("REST request to update TeamS : {}", teamS);
        if (teamS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeamS result = teamSService.save(teamS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teamS.getId().toString()))
            .body(result);
    }

    /**
     * GET  /team-s : get all the teamS.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of teamS in body
     */
    @GetMapping("/team-s")
    @Timed
    public ResponseEntity<List<TeamS>> getAllTeamS(Pageable pageable, @RequestParam(required = false) String filter, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("match-is-null".equals(filter)) {
            log.debug("REST request to get all TeamSs where match is null");
            return new ResponseEntity<>(teamSService.findAllWhereMatchIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of TeamS");
        Page<TeamS> page;
        if (eagerload) {
            page = teamSService.findAllWithEagerRelationships(pageable);
        } else {
            page = teamSService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/team-s?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /team-s/:id : get the "id" teamS.
     *
     * @param id the id of the teamS to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teamS, or with status 404 (Not Found)
     */
    @GetMapping("/team-s/{id}")
    @Timed
    public ResponseEntity<TeamS> getTeamS(@PathVariable Long id) {
        log.debug("REST request to get TeamS : {}", id);
        Optional<TeamS> teamS = teamSService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamS);
    }

    /**
     * DELETE  /team-s/:id : delete the "id" teamS.
     *
     * @param id the id of the teamS to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/team-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeamS(@PathVariable Long id) {
        log.debug("REST request to delete TeamS : {}", id);
        teamSService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
