package com.everteam.repository;

import com.everteam.domain.TeamS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TeamS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamSRepository extends JpaRepository<TeamS, Long> {

    @Query(value = "select distinct team_s from TeamS team_s left join fetch team_s.playerS",
        countQuery = "select count(distinct team_s) from TeamS team_s")
    Page<TeamS> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct team_s from TeamS team_s left join fetch team_s.playerS")
    List<TeamS> findAllWithEagerRelationships();

    @Query("select team_s from TeamS team_s left join fetch team_s.playerS where team_s.id =:id")
    Optional<TeamS> findOneWithEagerRelationships(@Param("id") Long id);

}
