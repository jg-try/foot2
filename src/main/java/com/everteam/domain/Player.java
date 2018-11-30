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
 * A Player.
 */
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    @Column(name = "avatar_content_type")
    private String avatarContentType;

    @Column(name = "win")
    private Integer win;

    @Column(name = "lose")
    private Integer lose;

    @Column(name = "draw")
    private Integer draw;

    @Column(name = "mvp")
    private Integer mvp;

    @OneToMany(mappedBy = "voter")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vote> votes = new HashSet<>();
    @ManyToMany(mappedBy = "playerS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<TeamS> teamS = new HashSet<>();

    @ManyToMany(mappedBy = "playerCS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<TeamC> teamCS = new HashSet<>();

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

    public Player name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public Player avatar(byte[] avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarContentType() {
        return avatarContentType;
    }

    public Player avatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
        return this;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }

    public Integer getWin() {
        return win;
    }

    public Player win(Integer win) {
        this.win = win;
        return this;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLose() {
        return lose;
    }

    public Player lose(Integer lose) {
        this.lose = lose;
        return this;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public Integer getDraw() {
        return draw;
    }

    public Player draw(Integer draw) {
        this.draw = draw;
        return this;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getMvp() {
        return mvp;
    }

    public Player mvp(Integer mvp) {
        this.mvp = mvp;
        return this;
    }

    public void setMvp(Integer mvp) {
        this.mvp = mvp;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public Player votes(Set<Vote> votes) {
        this.votes = votes;
        return this;
    }

    public Player addVote(Vote vote) {
        this.votes.add(vote);
        vote.setVoter(this);
        return this;
    }

    public Player removeVote(Vote vote) {
        this.votes.remove(vote);
        vote.setVoter(null);
        return this;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Set<TeamS> getTeamS() {
        return teamS;
    }

    public Player teamS(Set<TeamS> teamS) {
        this.teamS = teamS;
        return this;
    }

    public Player addTeamS(TeamS teamS) {
        this.teamS.add(teamS);
        teamS.getPlayerS().add(this);
        return this;
    }

    public Player removeTeamS(TeamS teamS) {
        this.teamS.remove(teamS);
        teamS.getPlayerS().remove(this);
        return this;
    }

    public void setTeamS(Set<TeamS> teamS) {
        this.teamS = teamS;
    }

    public Set<TeamC> getTeamCS() {
        return teamCS;
    }

    public Player teamCS(Set<TeamC> teamCS) {
        this.teamCS = teamCS;
        return this;
    }

    public Player addTeamC(TeamC teamC) {
        this.teamCS.add(teamC);
        teamC.getPlayerCS().add(this);
        return this;
    }

    public Player removeTeamC(TeamC teamC) {
        this.teamCS.remove(teamC);
        teamC.getPlayerCS().remove(this);
        return this;
    }

    public void setTeamCS(Set<TeamC> teamCS) {
        this.teamCS = teamCS;
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
        Player player = (Player) o;
        if (player.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", avatarContentType='" + getAvatarContentType() + "'" +
            ", win=" + getWin() +
            ", lose=" + getLose() +
            ", draw=" + getDraw() +
            ", mvp=" + getMvp() +
            "}";
    }
}
