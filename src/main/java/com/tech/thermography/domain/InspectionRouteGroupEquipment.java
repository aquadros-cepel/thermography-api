package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InspectionRouteGroupEquipment.
 */
@Entity
@Table(name = "inspection_route_group_equipment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InspectionRouteGroupEquipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "included")
    private Boolean included;

    @Column(name = "order_index")
    private Integer orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "inspectionRoute", "parentGroup", "subGroups", "equipments" }, allowSetters = true)
    @com.fasterxml.jackson.annotation.JsonIncludeProperties(value = { "id" })
    private InspectionRouteGroup inspectionRouteGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plant", "group", "components" }, allowSetters = true)
    private Equipment equipment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public InspectionRouteGroupEquipment id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getIncluded() {
        return this.included;
    }

    public InspectionRouteGroupEquipment included(Boolean included) {
        this.setIncluded(included);
        return this;
    }

    public void setIncluded(Boolean included) {
        this.included = included;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public InspectionRouteGroupEquipment orderIndex(Integer orderIndex) {
        this.setOrderIndex(orderIndex);
        return this;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public InspectionRouteGroup getInspectionRouteGroup() {
        return this.inspectionRouteGroup;
    }

    public void setInspectionRouteGroup(InspectionRouteGroup inspectionRouteGroup) {
        this.inspectionRouteGroup = inspectionRouteGroup;
    }

    public InspectionRouteGroupEquipment inspectionRouteGroup(InspectionRouteGroup inspectionRouteGroup) {
        this.setInspectionRouteGroup(inspectionRouteGroup);
        return this;
    }

    public Equipment getEquipment() {
        return this.equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public InspectionRouteGroupEquipment equipment(Equipment equipment) {
        this.setEquipment(equipment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InspectionRouteGroupEquipment)) {
            return false;
        }
        return getId() != null && getId().equals(((InspectionRouteGroupEquipment) o).getId());
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
        return "InspectionRouteGroupEquipment{" +
                "id=" + getId() +
                ", included='" + getIncluded() + "'" +
                ", orderIndex=" + getOrderIndex() +
                "}";
    }
}
