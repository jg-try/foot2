package com.everteam.repository;

import com.everteam.domain.Stadium;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Stadium entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {

}
