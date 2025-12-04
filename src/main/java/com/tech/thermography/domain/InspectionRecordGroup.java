package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InspectionRecordGroup.
 */
@Entity
@Table(name = "inspection_record_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InspectionRecordGroup implements Serializable {

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

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plant", "createdBy", "startedBy", "finishedBy", "groups" }, allowSetters = true)
    @com.fasterxml.jackson.annotation.JsonIncludeProperties(value = { "id" })
    private InspectionRecord inspectionRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "inspectionRecord", "parentGroup", "subGroups", "equipments" }, allowSetters = true)
    @com.fasterxml.jackson.annotation.JsonIncludeProperties(value = { "id" })
    private InspectionRecordGroup parentGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspectionRecord", "subGroups" }, allowSetters = true)
    private Set<InspectionRecordGroup> subGroups = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inspectionRecordGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<InspectionRecordGroupEquipment> equipments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public InspectionRecordGroup id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public InspectionRecordGroup code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public InspectionRecordGroup name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public InspectionRecordGroup description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderIndex() {
        return this.orderIndex;
    }

    public InspectionRecordGroup orderIndex(Integer orderIndex) {
        this.setOrderIndex(orderIndex);
        return this;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public InspectionRecordGroup finished(Boolean finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Instant getFinishedAt() {
        return this.finishedAt;
    }

    public InspectionRecordGroup finishedAt(Instant finishedAt) {
        this.setFinishedAt(finishedAt);
        return this;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public InspectionRecord getInspectionRecord() {
        return this.inspectionRecord;
    }

    public void setInspectionRecord(InspectionRecord inspectionRecord) {
        this.inspectionRecord = inspectionRecord;
    }

    public InspectionRecordGroup inspectionRecord(InspectionRecord inspectionRecord) {
        this.setInspectionRecord(inspectionRecord);
        return this;
    }

    public InspectionRecordGroup getParentGroup() {
        return this.parentGroup;
    }

    public void setParentGroup(InspectionRecordGroup inspectionRecordGroup) {
        this.parentGroup = inspectionRecordGroup;
    }

    public InspectionRecordGroup parentGroup(InspectionRecordGroup inspectionRecordGroup) {
        this.setParentGroup(inspectionRecordGroup);
        return this;
    }

    public Set<InspectionRecordGroup> getSubGroups() {
        return this.subGroups;
    }

    public void setSubGroups(Set<InspectionRecordGroup> inspectionRecordGroups) {
        if (this.subGroups != null) {
            this.subGroups.forEach(i -> i.setParentGroup(null));
        }
        if (inspectionRecordGroups != null) {
            inspectionRecordGroups.forEach(i -> i.setParentGroup(this));
        }
        this.subGroups = inspectionRecordGroups;
    }

    public InspectionRecordGroup subGroups(Set<InspectionRecordGroup> inspectionRecordGroups) {
        this.setSubGroups(inspectionRecordGroups);
        return this;
    }

    public InspectionRecordGroup addSubGroups(InspectionRecordGroup inspectionRecordGroup) {
        this.subGroups.add(inspectionRecordGroup);
        inspectionRecordGroup.setParentGroup(this);
        return this;
    }

    public InspectionRecordGroup removeSubGroups(InspectionRecordGroup inspectionRecordGroup) {
        this.subGroups.remove(inspectionRecordGroup);
        inspectionRecordGroup.setParentGroup(null);
        return this;
    }

    public Set<InspectionRecordGroupEquipment> getEquipments() {
        return this.equipments;
    }

    public void setEquipments(Set<InspectionRecordGroupEquipment> inspectionRecordGroupEquipments) {
        if (this.equipments != null) {
            this.equipments.forEach(i -> i.setInspectionRecordGroup(null));
        }
        if (inspectionRecordGroupEquipments != null) {
            inspectionRecordGroupEquipments.forEach(i -> i.setInspectionRecordGroup(this));
        }
        this.equipments = inspectionRecordGroupEquipments;
    }

    public InspectionRecordGroup equipments(Set<InspectionRecordGroupEquipment> inspectionRecordGroupEquipments) {
        this.setEquipments(inspectionRecordGroupEquipments);
        return this;
    }

    public InspectionRecordGroup addEquipments(InspectionRecordGroupEquipment inspectionRecordGroupEquipment) {
        this.equipments.add(inspectionRecordGroupEquipment);
        inspectionRecordGroupEquipment.setInspectionRecordGroup(this);
        return this;
    }

    public InspectionRecordGroup removeEquipments(InspectionRecordGroupEquipment inspectionRecordGroupEquipment) {
        this.equipments.remove(inspectionRecordGroupEquipment);
        inspectionRecordGroupEquipment.setInspectionRecordGroup(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InspectionRecordGroup)) {
            return false;
        }
        return getId() != null && getId().equals(((InspectionRecordGroup) o).getId());
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
        return "InspectionRecordGroup{" +
                "id=" + getId() +
                ", code='" + getCode() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", orderIndex=" + getOrderIndex() +
                ", finished='" + getFinished() + "'" +
                ", finishedAt='" + getFinishedAt() + "'" +
                "}";
    }
}
