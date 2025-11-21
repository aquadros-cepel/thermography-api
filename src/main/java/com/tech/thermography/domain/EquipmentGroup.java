package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EquipmentGroup.
 */
@Entity
@Table(name = "equipment_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EquipmentGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "company", "businessUnit" }, allowSetters = true)
    private Plant plant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plant", "subGroup", "parentGroups" }, allowSetters = true)
    private EquipmentGroup subGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plant", "subGroup", "parentGroups" }, allowSetters = true)
    private Set<EquipmentGroup> parentGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public EquipmentGroup id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public EquipmentGroup code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public EquipmentGroup name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public EquipmentGroup description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Plant getPlant() {
        return this.plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public EquipmentGroup plant(Plant plant) {
        this.setPlant(plant);
        return this;
    }

    public EquipmentGroup getSubGroup() {
        return this.subGroup;
    }

    public void setSubGroup(EquipmentGroup equipmentGroup) {
        this.subGroup = equipmentGroup;
    }

    public EquipmentGroup subGroup(EquipmentGroup equipmentGroup) {
        this.setSubGroup(equipmentGroup);
        return this;
    }

    public Set<EquipmentGroup> getParentGroups() {
        return this.parentGroups;
    }

    public void setParentGroups(Set<EquipmentGroup> equipmentGroups) {
        if (this.parentGroups != null) {
            this.parentGroups.forEach(i -> i.setSubGroup(null));
        }
        if (equipmentGroups != null) {
            equipmentGroups.forEach(i -> i.setSubGroup(this));
        }
        this.parentGroups = equipmentGroups;
    }

    public EquipmentGroup parentGroups(Set<EquipmentGroup> equipmentGroups) {
        this.setParentGroups(equipmentGroups);
        return this;
    }

    public EquipmentGroup addParentGroup(EquipmentGroup equipmentGroup) {
        this.parentGroups.add(equipmentGroup);
        equipmentGroup.setSubGroup(this);
        return this;
    }

    public EquipmentGroup removeParentGroup(EquipmentGroup equipmentGroup) {
        this.parentGroups.remove(equipmentGroup);
        equipmentGroup.setSubGroup(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipmentGroup)) {
            return false;
        }
        return getId() != null && getId().equals(((EquipmentGroup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipmentGroup{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
