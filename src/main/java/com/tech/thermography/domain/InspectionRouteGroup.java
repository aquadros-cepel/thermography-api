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
 * A InspectionRouteGroup.
 */
@Entity
@Table(name = "inspection_route_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InspectionRouteGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plant", "createdBy", "startedBy", "finishedBy" }, allowSetters = true)
    private InspectionRoute inspectionRoute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "inspectionRoute", "subGroup", "equipments", "parentGroups" }, allowSetters = true)
    private InspectionRouteGroup subGroup;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_inspection_route_group__equipments",
        joinColumns = @JoinColumn(name = "inspection_route_group_id"),
        inverseJoinColumns = @JoinColumn(name = "equipments_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plant", "group", "inspectionRouteGroups", "components" }, allowSetters = true)
    private Set<Equipment> equipments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspectionRoute", "subGroup", "equipments", "parentGroups" }, allowSetters = true)
    private Set<InspectionRouteGroup> parentGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public InspectionRouteGroup id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public InspectionRouteGroup name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public InspectionRouteGroup title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public InspectionRouteGroup description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InspectionRoute getInspectionRoute() {
        return this.inspectionRoute;
    }

    public void setInspectionRoute(InspectionRoute inspectionRoute) {
        this.inspectionRoute = inspectionRoute;
    }

    public InspectionRouteGroup inspectionRoute(InspectionRoute inspectionRoute) {
        this.setInspectionRoute(inspectionRoute);
        return this;
    }

    public InspectionRouteGroup getSubGroup() {
        return this.subGroup;
    }

    public void setSubGroup(InspectionRouteGroup inspectionRouteGroup) {
        this.subGroup = inspectionRouteGroup;
    }

    public InspectionRouteGroup subGroup(InspectionRouteGroup inspectionRouteGroup) {
        this.setSubGroup(inspectionRouteGroup);
        return this;
    }

    public Set<Equipment> getEquipments() {
        return this.equipments;
    }

    public void setEquipments(Set<Equipment> equipment) {
        this.equipments = equipment;
    }

    public InspectionRouteGroup equipments(Set<Equipment> equipment) {
        this.setEquipments(equipment);
        return this;
    }

    public InspectionRouteGroup addEquipments(Equipment equipment) {
        this.equipments.add(equipment);
        return this;
    }

    public InspectionRouteGroup removeEquipments(Equipment equipment) {
        this.equipments.remove(equipment);
        return this;
    }

    public Set<InspectionRouteGroup> getParentGroups() {
        return this.parentGroups;
    }

    public void setParentGroups(Set<InspectionRouteGroup> inspectionRouteGroups) {
        if (this.parentGroups != null) {
            this.parentGroups.forEach(i -> i.setSubGroup(null));
        }
        if (inspectionRouteGroups != null) {
            inspectionRouteGroups.forEach(i -> i.setSubGroup(this));
        }
        this.parentGroups = inspectionRouteGroups;
    }

    public InspectionRouteGroup parentGroups(Set<InspectionRouteGroup> inspectionRouteGroups) {
        this.setParentGroups(inspectionRouteGroups);
        return this;
    }

    public InspectionRouteGroup addParentGroup(InspectionRouteGroup inspectionRouteGroup) {
        this.parentGroups.add(inspectionRouteGroup);
        inspectionRouteGroup.setSubGroup(this);
        return this;
    }

    public InspectionRouteGroup removeParentGroup(InspectionRouteGroup inspectionRouteGroup) {
        this.parentGroups.remove(inspectionRouteGroup);
        inspectionRouteGroup.setSubGroup(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InspectionRouteGroup)) {
            return false;
        }
        return getId() != null && getId().equals(((InspectionRouteGroup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InspectionRouteGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
