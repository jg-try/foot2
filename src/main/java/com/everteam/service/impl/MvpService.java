package com.everteam.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.everteam.domain.Player;
import com.everteam.repository.PlayerRepository;

@Service
public class MvpService {
	
	public final PlayerRepository playerRepository;
	
	public MvpService(PlayerRepository matchRepository) {
		this.playerRepository = matchRepository;
	}

	public List<Player> getPlayerOfMatch(Long match_id) {
		List<Player> playerList = getPlayerS(match_id);
		for(Player player: getPlayerC(match_id)) {
			if (!playerList.contains(player)) {
				playerList.add(player);
			}
		}
		return playerList;
	}
	
	private List<Player> getPlayerS(Long match_id){
		return playerRepository.getPlayerS(match_id);
	}
	
	private List<Player> getPlayerC(Long match_id){
		return playerRepository.getPlayerC(match_id);
	}

}
