package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tech.thermography.domain.enumeration.EquipmentInspectionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InspectionRecordGroupEquipment.
 */
@Entity
@Table(name = "inspection_record_group_equipment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InspectionRecordGroupEquipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "order_index")
    private Integer orderIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EquipmentInspectionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "inspectionRecord", "parentGroup", "subGroups", "equipments" }, allowSetters = true)
    @com.fasterxml.jackson.annotation.JsonIncludeProperties(value = { "id" })
    private InspectionRecordGroup inspectionRecordGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plant", "group", "components" }, allowSetters = true)
    private Equipment equipment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public InspectionRecordGroupEquipment id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public InspectionRecordGroupEquipment orderIndex(Integer orderIndex) {
        this.setOrderIndex(orderIndex);
        return this;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public EquipmentInspectionStatus getStatus() {
        return this.status;
    }

    public InspectionRecordGroupEquipment status(EquipmentInspectionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(EquipmentInspectionStatus status) {
        this.status = status;
    }

    public InspectionRecordGroup getInspectionRecordGroup() {
        return this.inspectionRecordGroup;
    }

    public void setInspectionRecordGroup(InspectionRecordGroup inspectionRecordGroup) {
        this.inspectionRecordGroup = inspectionRecordGroup;
    }

    public InspectionRecordGroupEquipment inspectionRecordGroup(InspectionRecordGroup inspectionRecordGroup) {
        this.setInspectionRecordGroup(inspectionRecordGroup);
        return this;
    }

    public Equipment getEquipment() {
        return this.equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public InspectionRecordGroupEquipment equipment(Equipment equipment) {
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
        if (!(o instanceof InspectionRecordGroupEquipment)) {
            return false;
        }
        return getId() != null && getId().equals(((InspectionRecordGroupEquipment) o).getId());
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
        return "InspectionRecordGroupEquipment{" +
                "id=" + getId() +
                ", orderIndex=" + getOrderIndex() +
                ", status='" + getStatus() + "'" +
                "}";
    }
}
