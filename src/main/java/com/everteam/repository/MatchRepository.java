package com.everteam.repository;

import com.everteam.domain.Match;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Match entity.
 */
@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {


}
