package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tech.thermography.domain.enumeration.EquipmentType;
import com.tech.thermography.domain.enumeration.PhaseType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Equipment.
 */
@Entity
@Table(name = "equipment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Equipment implements Serializable {

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EquipmentType type;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "voltage_class")
    private Float voltageClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase_type")
    private PhaseType phaseType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "company", "businessUnit" }, allowSetters = true)
    private Plant plant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plant", "parentGroup", "subGroups" }, allowSetters = true)
    private EquipmentGroup group;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "equipments")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "componentTemperatureLimits", "equipments" }, allowSetters = true)
    private Set<EquipmentComponent> components = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Equipment id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Equipment code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Equipment name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Equipment description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EquipmentType getType() {
        return this.type;
    }

    public Equipment type(EquipmentType type) {
        this.setType(type);
        return this;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public Equipment manufacturer(String manufacturer) {
        this.setManufacturer(manufacturer);
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return this.model;
    }

    public Equipment model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public Equipment serialNumber(String serialNumber) {
        this.setSerialNumber(serialNumber);
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Float getVoltageClass() {
        return this.voltageClass;
    }

    public Equipment voltageClass(Float voltageClass) {
        this.setVoltageClass(voltageClass);
        return this;
    }

    public void setVoltageClass(Float voltageClass) {
        this.voltageClass = voltageClass;
    }

    public PhaseType getPhaseType() {
        return this.phaseType;
    }

    public Equipment phaseType(PhaseType phaseType) {
        this.setPhaseType(phaseType);
        return this;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Equipment startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Equipment latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Equipment longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Plant getPlant() {
        return this.plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Equipment plant(Plant plant) {
        this.setPlant(plant);
        return this;
    }

    public EquipmentGroup getGroup() {
        return this.group;
    }

    public void setGroup(EquipmentGroup equipmentGroup) {
        this.group = equipmentGroup;
    }

    public Equipment group(EquipmentGroup equipmentGroup) {
        this.setGroup(equipmentGroup);
        return this;
    }

    public Set<EquipmentComponent> getComponents() {
        return this.components;
    }

    public void setComponents(Set<EquipmentComponent> equipmentComponents) {
        if (this.components != null) {
            this.components.forEach(i -> i.removeEquipments(this));
        }
        if (equipmentComponents != null) {
            equipmentComponents.forEach(i -> i.addEquipments(this));
        }
        this.components = equipmentComponents;
    }

    public Equipment components(Set<EquipmentComponent> equipmentComponents) {
        this.setComponents(equipmentComponents);
        return this;
    }

    public Equipment addComponents(EquipmentComponent equipmentComponent) {
        this.components.add(equipmentComponent);
        equipmentComponent.getEquipments().add(this);
        return this;
    }

    public Equipment removeComponents(EquipmentComponent equipmentComponent) {
        this.components.remove(equipmentComponent);
        equipmentComponent.getEquipments().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipment)) {
            return false;
        }
        return getId() != null && getId().equals(((Equipment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipment{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", model='" + getModel() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", voltageClass=" + getVoltageClass() +
            ", phaseType='" + getPhaseType() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
