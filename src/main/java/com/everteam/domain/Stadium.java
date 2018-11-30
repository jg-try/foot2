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
 * A Stadium.
 */
@Entity
@Table(name = "stadium")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stadium implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "stadium")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Match> matches = new HashSet<>();
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

    public Stadium name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public Stadium matches(Set<Match> matches) {
        this.matches = matches;
        return this;
    }

    public Stadium addMatch(Match match) {
        this.matches.add(match);
        match.setStadium(this);
        return this;
    }

    public Stadium removeMatch(Match match) {
        this.matches.remove(match);
        match.setStadium(null);
        return this;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
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
        Stadium stadium = (Stadium) o;
        if (stadium.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stadium.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stadium{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
