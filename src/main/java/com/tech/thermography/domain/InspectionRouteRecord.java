package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InspectionRouteRecord.
 */
@Entity
@Table(name = "inspection_route_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InspectionRouteRecord implements Serializable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "company" }, allowSetters = true)
    private UserInfo startedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "company" }, allowSetters = true)
    private UserInfo finishedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public InspectionRouteRecord id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public InspectionRouteRecord code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public InspectionRouteRecord name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public InspectionRouteRecord description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaintenanceDocument() {
        return this.maintenanceDocument;
    }

    public InspectionRouteRecord maintenanceDocument(String maintenanceDocument) {
        this.setMaintenanceDocument(maintenanceDocument);
        return this;
    }

    public void setMaintenanceDocument(String maintenanceDocument) {
        this.maintenanceDocument = maintenanceDocument;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public InspectionRouteRecord createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getExpectedStartDate() {
        return this.expectedStartDate;
    }

    public InspectionRouteRecord expectedStartDate(LocalDate expectedStartDate) {
        this.setExpectedStartDate(expectedStartDate);
        return this;
    }

    public void setExpectedStartDate(LocalDate expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
    }

    public LocalDate getExpectedEndDate() {
        return this.expectedEndDate;
    }

    public InspectionRouteRecord expectedEndDate(LocalDate expectedEndDate) {
        this.setExpectedEndDate(expectedEndDate);
        return this;
    }

    public void setExpectedEndDate(LocalDate expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public Boolean getStarted() {
        return this.started;
    }

    public InspectionRouteRecord started(Boolean started) {
        this.setStarted(started);
        return this;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Instant getStartedAt() {
        return this.startedAt;
    }

    public InspectionRouteRecord startedAt(Instant startedAt) {
        this.setStartedAt(startedAt);
        return this;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public InspectionRouteRecord finished(Boolean finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Instant getFinishedAt() {
        return this.finishedAt;
    }

    public InspectionRouteRecord finishedAt(Instant finishedAt) {
        this.setFinishedAt(finishedAt);
        return this;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public UserInfo getStartedBy() {
        return this.startedBy;
    }

    public void setStartedBy(UserInfo userInfo) {
        this.startedBy = userInfo;
    }

    public InspectionRouteRecord startedBy(UserInfo userInfo) {
        this.setStartedBy(userInfo);
        return this;
    }

    public UserInfo getFinishedBy() {
        return this.finishedBy;
    }

    public void setFinishedBy(UserInfo userInfo) {
        this.finishedBy = userInfo;
    }

    public InspectionRouteRecord finishedBy(UserInfo userInfo) {
        this.setFinishedBy(userInfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InspectionRouteRecord)) {
            return false;
        }
        return getId() != null && getId().equals(((InspectionRouteRecord) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InspectionRouteRecord{" +
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
