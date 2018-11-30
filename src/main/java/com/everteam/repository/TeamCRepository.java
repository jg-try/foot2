package com.everteam.repository;

import com.everteam.domain.TeamC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TeamC entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamCRepository extends JpaRepository<TeamC, Long> {

    @Query(value = "select distinct team_c from TeamC team_c left join fetch team_c.playerCS",
        countQuery = "select count(distinct team_c) from TeamC team_c")
    Page<TeamC> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct team_c from TeamC team_c left join fetch team_c.playerCS")
    List<TeamC> findAllWithEagerRelationships();

    @Query("select team_c from TeamC team_c left join fetch team_c.playerCS where team_c.id =:id")
    Optional<TeamC> findOneWithEagerRelationships(@Param("id") Long id);

}
