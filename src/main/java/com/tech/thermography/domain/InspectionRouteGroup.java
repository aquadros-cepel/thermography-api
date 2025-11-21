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
    @JsonIgnoreProperties(value = { "plant", "createdBy" }, allowSetters = true)
    private InspectionRoute inspectionRoute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "inspectionRoute", "subGroup", "parentGroups" }, allowSetters = true)
    private InspectionRouteGroup subGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspectionRoute", "subGroup", "parentGroups" }, allowSetters = true)
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
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", included='" + getIncluded() + "'" +
            ", orderIndex=" + getOrderIndex() +
            "}";
    }
}
