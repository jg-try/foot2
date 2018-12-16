package com.everteam.repository;

import com.everteam.domain.Player;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Player entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

	@Query(value = "SELECT Player.* FROM Match\n" + 
			"INNER JOIN team_s ON team_s.id=match.teams_id\n" + 
			"INNER JOIN teams_players ON team_s.id=teams_players.teams_id\n" + 
			"INNER JOIN player ON teams_players.players_id=player.id\n" + 
			"where match.id=?1", nativeQuery = true)
	List<Player> getPlayerS(Long id);
	
	@Query(value = "SELECT Player.* FROM Match \n" + 
			"INNER JOIN team_c ON team_c.id=match.teamc_id \n" + 
			"INNER JOIN teamc_playerc ON team_c.id=teamc_playerc.teamcs_id\n" + 
			"INNER JOIN player ON teamc_playerc.playercs_id=player.id\n" + 
			"where match.id=?1", nativeQuery = true)
	List<Player> getPlayerC(Long id);
}
