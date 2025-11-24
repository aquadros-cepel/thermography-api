package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EquipmentComponentTemperatureLimits.
 */
@Entity
@Table(name = "equipment_component_temperature_limits")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EquipmentComponentTemperatureLimits implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "normal")
    private String normal;

    @Column(name = "low_risk")
    private String lowRisk;

    @Column(name = "medium_risk")
    private String mediumRisk;

    @Column(name = "high_risk")
    private String highRisk;

    @Column(name = "imminent_high_risk")
    private String imminentHighRisk;

    @JsonIgnoreProperties(value = { "componentTemperatureLimits", "equipments" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "componentTemperatureLimits")
    private EquipmentComponent equipmentComponent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public EquipmentComponentTemperatureLimits id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public EquipmentComponentTemperatureLimits name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormal() {
        return this.normal;
    }

    public EquipmentComponentTemperatureLimits normal(String normal) {
        this.setNormal(normal);
        return this;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getLowRisk() {
        return this.lowRisk;
    }

    public EquipmentComponentTemperatureLimits lowRisk(String lowRisk) {
        this.setLowRisk(lowRisk);
        return this;
    }

    public void setLowRisk(String lowRisk) {
        this.lowRisk = lowRisk;
    }

    public String getMediumRisk() {
        return this.mediumRisk;
    }

    public EquipmentComponentTemperatureLimits mediumRisk(String mediumRisk) {
        this.setMediumRisk(mediumRisk);
        return this;
    }

    public void setMediumRisk(String mediumRisk) {
        this.mediumRisk = mediumRisk;
    }

    public String getHighRisk() {
        return this.highRisk;
    }

    public EquipmentComponentTemperatureLimits highRisk(String highRisk) {
        this.setHighRisk(highRisk);
        return this;
    }

    public void setHighRisk(String highRisk) {
        this.highRisk = highRisk;
    }

    public String getImminentHighRisk() {
        return this.imminentHighRisk;
    }

    public EquipmentComponentTemperatureLimits imminentHighRisk(String imminentHighRisk) {
        this.setImminentHighRisk(imminentHighRisk);
        return this;
    }

    public void setImminentHighRisk(String imminentHighRisk) {
        this.imminentHighRisk = imminentHighRisk;
    }

    public EquipmentComponent getEquipmentComponent() {
        return this.equipmentComponent;
    }

    public void setEquipmentComponent(EquipmentComponent equipmentComponent) {
        if (this.equipmentComponent != null) {
            this.equipmentComponent.setComponentTemperatureLimits(null);
        }
        if (equipmentComponent != null) {
            equipmentComponent.setComponentTemperatureLimits(this);
        }
        this.equipmentComponent = equipmentComponent;
    }

    public EquipmentComponentTemperatureLimits equipmentComponent(EquipmentComponent equipmentComponent) {
        this.setEquipmentComponent(equipmentComponent);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipmentComponentTemperatureLimits)) {
            return false;
        }
        return getId() != null && getId().equals(((EquipmentComponentTemperatureLimits) o).getId());
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
        return "EquipmentComponentTemperatureLimits{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", normal='" + getNormal() + "'" +
                ", lowRisk='" + getLowRisk() + "'" +
                ", mediumRisk='" + getMediumRisk() + "'" +
                ", highRisk='" + getHighRisk() + "'" +
                ", imminentHighRisk='" + getImminentHighRisk() + "'" +
                "}";
    }
}
