package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ROI.
 */
@Entity
@Table(name = "roi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ROI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "max_temp", nullable = false)
    private Double maxTemp;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "equipment", "createdBy" }, allowSetters = true)
    private Thermogram thermogram;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ROI id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public ROI type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return this.label;
    }

    public ROI label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getMaxTemp() {
        return this.maxTemp;
    }

    public ROI maxTemp(Double maxTemp) {
        this.setMaxTemp(maxTemp);
        return this;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Thermogram getThermogram() {
        return this.thermogram;
    }

    public void setThermogram(Thermogram thermogram) {
        this.thermogram = thermogram;
    }

    public ROI thermogram(Thermogram thermogram) {
        this.setThermogram(thermogram);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ROI)) {
            return false;
        }
        return getId() != null && getId().equals(((ROI) o).getId());
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ROI{" +
                "id=" + getId() +
                ", type='" + getType() + "'" +
                ", label='" + getLabel() + "'" +
                ", maxTemp=" + getMaxTemp() +
                "}";
    }
}
