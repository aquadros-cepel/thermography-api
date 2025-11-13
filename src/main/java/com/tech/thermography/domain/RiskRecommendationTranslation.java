package com.tech.thermography.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RiskRecommendationTranslation.
 */
@Entity
@Table(name = "risk_recommendation_translation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RiskRecommendationTranslation implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "language", nullable = false)
    private String language;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private RiskPeriodicityDeadline riskPeriodicityDeadline;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public RiskRecommendationTranslation id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLanguage() {
        return this.language;
    }

    public RiskRecommendationTranslation language(String language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return this.name;
    }

    public RiskRecommendationTranslation name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RiskPeriodicityDeadline getRiskPeriodicityDeadline() {
        return this.riskPeriodicityDeadline;
    }

    public void setRiskPeriodicityDeadline(RiskPeriodicityDeadline riskPeriodicityDeadline) {
        this.riskPeriodicityDeadline = riskPeriodicityDeadline;
    }

    public RiskRecommendationTranslation riskPeriodicityDeadline(RiskPeriodicityDeadline riskPeriodicityDeadline) {
        this.setRiskPeriodicityDeadline(riskPeriodicityDeadline);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiskRecommendationTranslation)) {
            return false;
        }
        return getId() != null && getId().equals(((RiskRecommendationTranslation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiskRecommendationTranslation{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
