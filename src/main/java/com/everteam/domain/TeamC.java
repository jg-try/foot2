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
 * A TeamC.
 */
@Entity
@Table(name = "team_c")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeamC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "teamc_playerc",
               joinColumns = @JoinColumn(name = "teamcs_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "playercs_id", referencedColumnName = "id"))
    private Set<Player> playerCS = new HashSet<>();

    @OneToOne(mappedBy = "teamC")
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

    public TeamC name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Player> getPlayerCS() {
        return playerCS;
    }

    public TeamC playerCS(Set<Player> players) {
        this.playerCS = players;
        return this;
    }

    public TeamC addPlayerC(Player player) {
        this.playerCS.add(player);
        player.getTeamCS().add(this);
        return this;
    }

    public TeamC removePlayerC(Player player) {
        this.playerCS.remove(player);
        player.getTeamCS().remove(this);
        return this;
    }

    public void setPlayerCS(Set<Player> players) {
        this.playerCS = players;
    }

    public Match getMatch() {
        return match;
    }

    public TeamC match(Match match) {
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
        TeamC teamC = (TeamC) o;
        if (teamC.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamC.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamC{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
