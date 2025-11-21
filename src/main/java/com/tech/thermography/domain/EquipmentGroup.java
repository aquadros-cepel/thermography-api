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
    @JsonIgnoreProperties(value = { "plant", "parentGroup", "subGroups" }, allowSetters = true)
    private EquipmentGroup parentGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plant", "parentGroup", "subGroups" }, allowSetters = true)
    private Set<EquipmentGroup> subGroups = new HashSet<>();

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

    public EquipmentGroup getParentGroup() {
        return this.parentGroup;
    }

    public void setParentGroup(EquipmentGroup equipmentGroup) {
        this.parentGroup = equipmentGroup;
    }

    public EquipmentGroup parentGroup(EquipmentGroup equipmentGroup) {
        this.setParentGroup(equipmentGroup);
        return this;
    }

    public Set<EquipmentGroup> getSubGroups() {
        return this.subGroups;
    }

    public void setSubGroups(Set<EquipmentGroup> equipmentGroups) {
        if (this.subGroups != null) {
            this.subGroups.forEach(i -> i.setParentGroup(null));
        }
        if (equipmentGroups != null) {
            equipmentGroups.forEach(i -> i.setParentGroup(this));
        }
        this.subGroups = equipmentGroups;
    }

    public EquipmentGroup subGroups(Set<EquipmentGroup> equipmentGroups) {
        this.setSubGroups(equipmentGroups);
        return this;
    }

    public EquipmentGroup addSubGroups(EquipmentGroup equipmentGroup) {
        this.subGroups.add(equipmentGroup);
        equipmentGroup.setParentGroup(this);
        return this;
    }

    public EquipmentGroup removeSubGroups(EquipmentGroup equipmentGroup) {
        this.subGroups.remove(equipmentGroup);
        equipmentGroup.setParentGroup(null);
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
