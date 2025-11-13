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
 * A InspectionRoute.
 */
@Entity
@Table(name = "inspection_route")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InspectionRoute implements Serializable {

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

    @Column(name = "plan_note")
    private String planNote;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "started")
    private Boolean started;

    @Column(name = "started_at")
    private Instant startedAt;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "company", "businessUnit" }, allowSetters = true)
    private Plant plant;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public InspectionRoute id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public InspectionRoute name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public InspectionRoute title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public InspectionRoute description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlanNote() {
        return this.planNote;
    }

    public InspectionRoute planNote(String planNote) {
        this.setPlanNote(planNote);
        return this;
    }

    public void setPlanNote(String planNote) {
        this.planNote = planNote;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public InspectionRoute createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public InspectionRoute startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Boolean getStarted() {
        return this.started;
    }

    public InspectionRoute started(Boolean started) {
        this.setStarted(started);
        return this;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Instant getStartedAt() {
        return this.startedAt;
    }

    public InspectionRoute startedAt(Instant startedAt) {
        this.setStartedAt(startedAt);
        return this;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public InspectionRoute endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public InspectionRoute finished(Boolean finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Instant getFinishedAt() {
        return this.finishedAt;
    }

    public InspectionRoute finishedAt(Instant finishedAt) {
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

    public InspectionRoute plant(Plant plant) {
        this.setPlant(plant);
        return this;
    }

    public UserInfo getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(UserInfo userInfo) {
        this.createdBy = userInfo;
    }

    public InspectionRoute createdBy(UserInfo userInfo) {
        this.setCreatedBy(userInfo);
        return this;
    }

    public UserInfo getStartedBy() {
        return this.startedBy;
    }

    public void setStartedBy(UserInfo userInfo) {
        this.startedBy = userInfo;
    }

    public InspectionRoute startedBy(UserInfo userInfo) {
        this.setStartedBy(userInfo);
        return this;
    }

    public UserInfo getFinishedBy() {
        return this.finishedBy;
    }

    public void setFinishedBy(UserInfo userInfo) {
        this.finishedBy = userInfo;
    }

    public InspectionRoute finishedBy(UserInfo userInfo) {
        this.setFinishedBy(userInfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InspectionRoute)) {
            return false;
        }
        return getId() != null && getId().equals(((InspectionRoute) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InspectionRoute{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", planNote='" + getPlanNote() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", started='" + getStarted() + "'" +
            ", startedAt='" + getStartedAt() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", finished='" + getFinished() + "'" +
            ", finishedAt='" + getFinishedAt() + "'" +
            "}";
    }
}
