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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InspectionRecord.
 */
@Entity
@Table(name = "inspection_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InspectionRecord implements Serializable {

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

    @Column(name = "maintenance_document")
    private String maintenanceDocument;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "expected_start_date", nullable = false)
    private LocalDate expectedStartDate;

    @NotNull
    @Column(name = "expected_end_date", nullable = false)
    private LocalDate expectedEndDate;

    @Column(name = "started")
    private Boolean started;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "company", "businessUnit" }, allowSetters = true)
    private Plant plant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plant", "createdBy" }, allowSetters = true)
    @com.fasterxml.jackson.annotation.JsonIncludeProperties(value = { "id" })
    private InspectionRoute inspectionRoute;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "company" }, allowSetters = true)
    private UserInfo createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "company" }, allowSetters = true)
    private UserInfo startedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "company" }, allowSetters = true)
    private UserInfo finishedBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inspectionRecord")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    // @JsonIgnoreProperties(value = { "inspectionRecord", "parentGroup",
    // "subGroups", "equipments" }, allowSetters = true)
    private Set<InspectionRecordGroup> groups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public InspectionRecord id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public InspectionRecord code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public InspectionRecord name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public InspectionRecord description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaintenanceDocument() {
        return this.maintenanceDocument;
    }

    public InspectionRecord maintenanceDocument(String maintenanceDocument) {
        this.setMaintenanceDocument(maintenanceDocument);
        return this;
    }

    public void setMaintenanceDocument(String maintenanceDocument) {
        this.maintenanceDocument = maintenanceDocument;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public InspectionRecord createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getExpectedStartDate() {
        return this.expectedStartDate;
    }

    public InspectionRecord expectedStartDate(LocalDate expectedStartDate) {
        this.setExpectedStartDate(expectedStartDate);
        return this;
    }

    public void setExpectedStartDate(LocalDate expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
    }

    public LocalDate getExpectedEndDate() {
        return this.expectedEndDate;
    }

    public InspectionRecord expectedEndDate(LocalDate expectedEndDate) {
        this.setExpectedEndDate(expectedEndDate);
        return this;
    }

    public void setExpectedEndDate(LocalDate expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public Boolean getStarted() {
        return this.started;
    }

    public InspectionRecord started(Boolean started) {
        this.setStarted(started);
        return this;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Instant getStartedAt() {
        return this.startedAt;
    }

    public InspectionRecord startedAt(Instant startedAt) {
        this.setStartedAt(startedAt);
        return this;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public InspectionRecord finished(Boolean finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Instant getFinishedAt() {
        return this.finishedAt;
    }

    public InspectionRecord finishedAt(Instant finishedAt) {
        this.setFinishedAt(finishedAt);
        return this;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Plant getPlant() {
        return this.plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public InspectionRecord plant(Plant plant) {
        this.setPlant(plant);
        return this;
    }

    public InspectionRoute getInspectionRoute() {
        return this.inspectionRoute;
    }

    public void setInspectionRoute(InspectionRoute inspectionRoute) {
        this.inspectionRoute = inspectionRoute;
    }

    public InspectionRecord inspectionRoute(InspectionRoute inspectionRoute) {
        this.setInspectionRoute(inspectionRoute);
        return this;
    }

    public UserInfo getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(UserInfo userInfo) {
        this.createdBy = userInfo;
    }

    public InspectionRecord createdBy(UserInfo userInfo) {
        this.setCreatedBy(userInfo);
        return this;
    }

    public UserInfo getStartedBy() {
        return this.startedBy;
    }

    public void setStartedBy(UserInfo userInfo) {
        this.startedBy = userInfo;
    }

    public InspectionRecord startedBy(UserInfo userInfo) {
        this.setStartedBy(userInfo);
        return this;
    }

    public UserInfo getFinishedBy() {
        return this.finishedBy;
    }

    public void setFinishedBy(UserInfo userInfo) {
        this.finishedBy = userInfo;
    }

    public InspectionRecord finishedBy(UserInfo userInfo) {
        this.setFinishedBy(userInfo);
        return this;
    }

    public Set<InspectionRecordGroup> getGroups() {
        return this.groups;
    }

    public void setGroups(Set<InspectionRecordGroup> inspectionRecordGroups) {
        if (this.groups != null) {
            this.groups.forEach(i -> i.setInspectionRecord(null));
        }
        if (inspectionRecordGroups != null) {
            inspectionRecordGroups.forEach(i -> i.setInspectionRecord(this));
        }
        this.groups = inspectionRecordGroups;
    }

    public InspectionRecord groups(Set<InspectionRecordGroup> inspectionRecordGroups) {
        this.setGroups(inspectionRecordGroups);
        return this;
    }

    public InspectionRecord addGroups(InspectionRecordGroup inspectionRecordGroup) {
        this.groups.add(inspectionRecordGroup);
        inspectionRecordGroup.setInspectionRecord(this);
        return this;
    }

    public InspectionRecord removeGroups(InspectionRecordGroup inspectionRecordGroup) {
        this.groups.remove(inspectionRecordGroup);
        inspectionRecordGroup.setInspectionRecord(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InspectionRecord)) {
            return false;
        }
        return getId() != null && getId().equals(((InspectionRecord) o).getId());
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
        return "InspectionRecord{" +
                "id=" + getId() +
                ", code='" + getCode() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", maintenanceDocument='" + getMaintenanceDocument() + "'" +
                ", createdAt='" + getCreatedAt() + "'" +
                ", expectedStartDate='" + getExpectedStartDate() + "'" +
                ", expectedEndDate='" + getExpectedEndDate() + "'" +
                ", started='" + getStarted() + "'" +
                ", startedAt='" + getStartedAt() + "'" +
                ", finished='" + getFinished() + "'" +
                ", finishedAt='" + getFinishedAt() + "'" +
                "}";
    }
}
