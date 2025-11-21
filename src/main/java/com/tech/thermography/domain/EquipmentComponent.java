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
 * A EquipmentComponent.
 */
@Entity
@Table(name = "equipment_component")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EquipmentComponent implements Serializable {

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

    @JsonIgnoreProperties(value = { "equipmentComponent" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private EquipmentComponentTemperatureLimits componentTemperatureLimits;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_equipment_component__equipments",
        joinColumns = @JoinColumn(name = "equipment_component_id"),
        inverseJoinColumns = @JoinColumn(name = "equipments_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plant", "group", "components" }, allowSetters = true)
    private Set<Equipment> equipments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public EquipmentComponent id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public EquipmentComponent code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public EquipmentComponent name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public EquipmentComponent description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EquipmentComponentTemperatureLimits getComponentTemperatureLimits() {
        return this.componentTemperatureLimits;
    }

    public void setComponentTemperatureLimits(EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimits) {
        this.componentTemperatureLimits = equipmentComponentTemperatureLimits;
    }

    public EquipmentComponent componentTemperatureLimits(EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimits) {
        this.setComponentTemperatureLimits(equipmentComponentTemperatureLimits);
        return this;
    }

    public Set<Equipment> getEquipments() {
        return this.equipments;
    }

    public void setEquipments(Set<Equipment> equipment) {
        this.equipments = equipment;
    }

    public EquipmentComponent equipments(Set<Equipment> equipment) {
        this.setEquipments(equipment);
        return this;
    }

    public EquipmentComponent addEquipments(Equipment equipment) {
        this.equipments.add(equipment);
        return this;
    }

    public EquipmentComponent removeEquipments(Equipment equipment) {
        this.equipments.remove(equipment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipmentComponent)) {
            return false;
        }
        return getId() != null && getId().equals(((EquipmentComponent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipmentComponent{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
