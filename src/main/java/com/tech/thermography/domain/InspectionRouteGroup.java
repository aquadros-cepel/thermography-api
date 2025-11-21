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

    @Column(name = "included")
    private Boolean included;

    @Column(name = "order_index")
    private Integer orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plant", "createdBy", "groups" }, allowSetters = true)
    private InspectionRoute inspectionRoute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "inspectionRoute", "parentGroup", "subGroups", "equipments" }, allowSetters = true)
    private InspectionRouteGroup parentGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspectionRoute", "parentGroup", "subGroups", "equipments" }, allowSetters = true)
    private Set<InspectionRouteGroup> subGroups = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inspectionRouteGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspectionRouteGroup", "equipment" }, allowSetters = true)
    private Set<InspectionRouteGroupEquipment> equipments = new HashSet<>();

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

    public String getCode() {
        return this.code;
    }

    public InspectionRouteGroup code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Boolean getIncluded() {
        return this.included;
    }

    public InspectionRouteGroup included(Boolean included) {
        this.setIncluded(included);
        return this;
    }

    public void setIncluded(Boolean included) {
        this.included = included;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public InspectionRouteGroup orderIndex(Integer orderIndex) {
        this.setOrderIndex(orderIndex);
        return this;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
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

    public InspectionRouteGroup getParentGroup() {
        return this.parentGroup;
    }

    public void setParentGroup(InspectionRouteGroup inspectionRouteGroup) {
        this.parentGroup = inspectionRouteGroup;
    }

    public InspectionRouteGroup parentGroup(InspectionRouteGroup inspectionRouteGroup) {
        this.setParentGroup(inspectionRouteGroup);
        return this;
    }

    public Set<InspectionRouteGroup> getSubGroups() {
        return this.subGroups;
    }

    public void setSubGroups(Set<InspectionRouteGroup> inspectionRouteGroups) {
        if (this.subGroups != null) {
            this.subGroups.forEach(i -> i.setParentGroup(null));
        }
        if (inspectionRouteGroups != null) {
            inspectionRouteGroups.forEach(i -> i.setParentGroup(this));
        }
        this.subGroups = inspectionRouteGroups;
    }

    public InspectionRouteGroup subGroups(Set<InspectionRouteGroup> inspectionRouteGroups) {
        this.setSubGroups(inspectionRouteGroups);
        return this;
    }

    public InspectionRouteGroup addSubGroups(InspectionRouteGroup inspectionRouteGroup) {
        this.subGroups.add(inspectionRouteGroup);
        inspectionRouteGroup.setParentGroup(this);
        return this;
    }

    public InspectionRouteGroup removeSubGroups(InspectionRouteGroup inspectionRouteGroup) {
        this.subGroups.remove(inspectionRouteGroup);
        inspectionRouteGroup.setParentGroup(null);
        return this;
    }

    public Set<InspectionRouteGroupEquipment> getEquipments() {
        return this.equipments;
    }

    public void setEquipments(Set<InspectionRouteGroupEquipment> inspectionRouteGroupEquipments) {
        if (this.equipments != null) {
            this.equipments.forEach(i -> i.setInspectionRouteGroup(null));
        }
        if (inspectionRouteGroupEquipments != null) {
            inspectionRouteGroupEquipments.forEach(i -> i.setInspectionRouteGroup(this));
        }
        this.equipments = inspectionRouteGroupEquipments;
    }

    public InspectionRouteGroup equipments(Set<InspectionRouteGroupEquipment> inspectionRouteGroupEquipments) {
        this.setEquipments(inspectionRouteGroupEquipments);
        return this;
    }

    public InspectionRouteGroup addEquipments(InspectionRouteGroupEquipment inspectionRouteGroupEquipment) {
        this.equipments.add(inspectionRouteGroupEquipment);
        inspectionRouteGroupEquipment.setInspectionRouteGroup(this);
        return this;
    }

    public InspectionRouteGroup removeEquipments(InspectionRouteGroupEquipment inspectionRouteGroupEquipment) {
        this.equipments.remove(inspectionRouteGroupEquipment);
        inspectionRouteGroupEquipment.setInspectionRouteGroup(null);
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
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", included='" + getIncluded() + "'" +
            ", orderIndex=" + getOrderIndex() +
            "}";
    }
}
