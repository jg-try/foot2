package com.everteam.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Match.
 */
@Entity
@Table(name = "match")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "score_c")
    private Integer scoreC;

    @Column(name = "score_s")
    private Integer scoreS;

    @OneToOne    @JoinColumn(unique = true)
    private TeamC teamC;

    @OneToOne    @JoinColumn(unique = true)
    private TeamS teamS;

    @ManyToOne
    @JsonIgnoreProperties("matches")
    private Stadium stadium;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Player mvpC;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Player mvpS;

    @OneToMany(mappedBy = "match")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vote> votes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Match date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getScoreC() {
        return scoreC;
    }

    public Match scoreC(Integer scoreC) {
        this.scoreC = scoreC;
        return this;
    }

    public void setScoreC(Integer scoreC) {
        this.scoreC = scoreC;
    }

    public Integer getScoreS() {
        return scoreS;
    }

    public Match scoreS(Integer scoreS) {
        this.scoreS = scoreS;
        return this;
    }

    public void setScoreS(Integer scoreS) {
        this.scoreS = scoreS;
    }

    public TeamC getTeamC() {
        return teamC;
    }

    public Match teamC(TeamC teamC) {
        this.teamC = teamC;
        return this;
    }

    public void setTeamC(TeamC teamC) {
        this.teamC = teamC;
    }

    public TeamS getTeamS() {
        return teamS;
    }

    public Match teamS(TeamS teamS) {
        this.teamS = teamS;
        return this;
    }

    public void setTeamS(TeamS teamS) {
        this.teamS = teamS;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public Match stadium(Stadium stadium) {
        this.stadium = stadium;
        return this;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public Player getMvpC() {
        return mvpC;
    }

    public Match mvpC(Player player) {
        this.mvpC = player;
        return this;
    }

    public void setMvpC(Player player) {
        this.mvpC = player;
    }

    public Player getMvpS() {
        return mvpS;
    }

    public Match mvpS(Player player) {
        this.mvpS = player;
        return this;
    }

    public void setMvpS(Player player) {
        this.mvpS = player;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public Match votes(Set<Vote> votes) {
        this.votes = votes;
        return this;
    }

    public Match addVote(Vote vote) {
        this.votes.add(vote);
        vote.setMatch(this);
        return this;
    }

    public Match removeVote(Vote vote) {
        this.votes.remove(vote);
        vote.setMatch(null);
        return this;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
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
        Match match = (Match) o;
        if (match.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), match.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", scoreC=" + getScoreC() +
            ", scoreS=" + getScoreS() +
            "}";
    }
}
