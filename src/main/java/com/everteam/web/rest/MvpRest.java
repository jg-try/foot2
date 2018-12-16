package com.everteam.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everteam.domain.Player;
import com.everteam.service.impl.MvpService;

@RestController
@RequestMapping("/api")
public class MvpRest {
	
	@Autowired
	public MvpService mvpService;
	
	@GetMapping(path="/match/{id}/players")
	public ResponseEntity<List<Player>> getPlayersOfMatch(@PathVariable Long id){
		return ResponseEntity.ok().body(mvpService.getPlayerOfMatch(id));
	}

}
