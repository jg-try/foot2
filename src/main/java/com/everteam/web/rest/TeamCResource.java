package com.everteam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.everteam.domain.TeamC;
import com.everteam.service.TeamCService;
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
 * REST controller for managing TeamC.
 */
@RestController
@RequestMapping("/api")
public class TeamCResource {

    private final Logger log = LoggerFactory.getLogger(TeamCResource.class);

    private static final String ENTITY_NAME = "teamC";

    private final TeamCService teamCService;

    public TeamCResource(TeamCService teamCService) {
        this.teamCService = teamCService;
    }

    /**
     * POST  /team-cs : Create a new teamC.
     *
     * @param teamC the teamC to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teamC, or with status 400 (Bad Request) if the teamC has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/team-cs")
    @Timed
    public ResponseEntity<TeamC> createTeamC(@RequestBody TeamC teamC) throws URISyntaxException {
        log.debug("REST request to save TeamC : {}", teamC);
        if (teamC.getId() != null) {
            throw new BadRequestAlertException("A new teamC cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamC result = teamCService.save(teamC);
        return ResponseEntity.created(new URI("/api/team-cs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /team-cs : Updates an existing teamC.
     *
     * @param teamC the teamC to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teamC,
     * or with status 400 (Bad Request) if the teamC is not valid,
     * or with status 500 (Internal Server Error) if the teamC couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/team-cs")
    @Timed
    public ResponseEntity<TeamC> updateTeamC(@RequestBody TeamC teamC) throws URISyntaxException {
        log.debug("REST request to update TeamC : {}", teamC);
        if (teamC.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeamC result = teamCService.save(teamC);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teamC.getId().toString()))
            .body(result);
    }

    /**
     * GET  /team-cs : get all the teamCS.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of teamCS in body
     */
    @GetMapping("/team-cs")
    @Timed
    public ResponseEntity<List<TeamC>> getAllTeamCS(Pageable pageable, @RequestParam(required = false) String filter, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("match-is-null".equals(filter)) {
            log.debug("REST request to get all TeamCs where match is null");
            return new ResponseEntity<>(teamCService.findAllWhereMatchIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of TeamCS");
        Page<TeamC> page;
        if (eagerload) {
            page = teamCService.findAllWithEagerRelationships(pageable);
        } else {
            page = teamCService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/team-cs?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /team-cs/:id : get the "id" teamC.
     *
     * @param id the id of the teamC to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teamC, or with status 404 (Not Found)
     */
    @GetMapping("/team-cs/{id}")
    @Timed
    public ResponseEntity<TeamC> getTeamC(@PathVariable Long id) {
        log.debug("REST request to get TeamC : {}", id);
        Optional<TeamC> teamC = teamCService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamC);
    }

    /**
     * DELETE  /team-cs/:id : delete the "id" teamC.
     *
     * @param id the id of the teamC to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/team-cs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeamC(@PathVariable Long id) {
        log.debug("REST request to delete TeamC : {}", id);
        teamCService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
