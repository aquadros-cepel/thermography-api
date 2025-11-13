package com.tech.thermography.domain;

import com.tech.thermography.domain.enumeration.DatetimeUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RiskPeriodicityDeadline.
 */
@Entity
@Table(name = "risk_periodicity_deadline")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RiskPeriodicityDeadline implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "deadline")
    private Integer deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "deadline_unit")
    private DatetimeUnit deadlineUnit;

    @Column(name = "periodicity")
    private Integer periodicity;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicity_unit")
    private DatetimeUnit periodicityUnit;

    @Column(name = "recommendations")
    private String recommendations;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public RiskPeriodicityDeadline id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public RiskPeriodicityDeadline name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeadline() {
        return this.deadline;
    }

    public RiskPeriodicityDeadline deadline(Integer deadline) {
        this.setDeadline(deadline);
        return this;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public DatetimeUnit getDeadlineUnit() {
        return this.deadlineUnit;
    }

    public RiskPeriodicityDeadline deadlineUnit(DatetimeUnit deadlineUnit) {
        this.setDeadlineUnit(deadlineUnit);
        return this;
    }

    public void setDeadlineUnit(DatetimeUnit deadlineUnit) {
        this.deadlineUnit = deadlineUnit;
    }

    public Integer getPeriodicity() {
        return this.periodicity;
    }

    public RiskPeriodicityDeadline periodicity(Integer periodicity) {
        this.setPeriodicity(periodicity);
        return this;
    }

    public void setPeriodicity(Integer periodicity) {
        this.periodicity = periodicity;
    }

    public DatetimeUnit getPeriodicityUnit() {
        return this.periodicityUnit;
    }

    public RiskPeriodicityDeadline periodicityUnit(DatetimeUnit periodicityUnit) {
        this.setPeriodicityUnit(periodicityUnit);
        return this;
    }

    public void setPeriodicityUnit(DatetimeUnit periodicityUnit) {
        this.periodicityUnit = periodicityUnit;
    }

    public String getRecommendations() {
        return this.recommendations;
    }

    public RiskPeriodicityDeadline recommendations(String recommendations) {
        this.setRecommendations(recommendations);
        return this;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiskPeriodicityDeadline)) {
            return false;
        }
        return getId() != null && getId().equals(((RiskPeriodicityDeadline) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiskPeriodicityDeadline{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", deadline=" + getDeadline() +
            ", deadlineUnit='" + getDeadlineUnit() + "'" +
            ", periodicity=" + getPeriodicity() +
            ", periodicityUnit='" + getPeriodicityUnit() + "'" +
            ", recommendations='" + getRecommendations() + "'" +
            "}";
    }
}
