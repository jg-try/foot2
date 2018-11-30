package com.everteam.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TeamS.
 */
@Entity
@Table(name = "team_s")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeamS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "teams_players",
               joinColumns = @JoinColumn(name = "teams_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "players_id", referencedColumnName = "id"))
    private Set<Player> playerS = new HashSet<>();

    @OneToOne(mappedBy = "teamS")
    @JsonIgnore
    private Match match;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TeamS name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Player> getPlayerS() {
        return playerS;
    }

    public TeamS playerS(Set<Player> players) {
        this.playerS = players;
        return this;
    }

    public TeamS addPlayerS(Player player) {
        this.playerS.add(player);
        player.getTeamS().add(this);
        return this;
    }

    public TeamS removePlayerS(Player player) {
        this.playerS.remove(player);
        player.getTeamS().remove(this);
        return this;
    }

    public void setPlayerS(Set<Player> players) {
        this.playerS = players;
    }

    public Match getMatch() {
        return match;
    }

    public TeamS match(Match match) {
        this.match = match;
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TeamS teamS = (TeamS) o;
        if (teamS.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamS.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamS{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
