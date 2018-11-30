package com.everteam.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Vote.
 */
@Entity
@Table(name = "vote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties("votes")
    private Match match;

    @ManyToOne
    @JsonIgnoreProperties("votes")
    private Player voter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public Vote note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Match getMatch() {
        return match;
    }

    public Vote match(Match match) {
        this.match = match;
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Player getVoter() {
        return voter;
    }

    public Vote voter(Player player) {
        this.voter = player;
        return this;
    }

    public void setVoter(Player player) {
        this.voter = player;
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
        Vote vote = (Vote) o;
        if (vote.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vote.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vote{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
