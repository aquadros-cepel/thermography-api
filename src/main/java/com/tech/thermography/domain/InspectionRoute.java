package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tech.thermography.domain.enumeration.Periodicity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "maintenance_plan")
    private String maintenancePlan;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicity")
    private Periodicity periodicity;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "expected_start_date")
    private LocalDate expectedStartDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "company", "businessUnit" }, allowSetters = true)
    private Plant plant;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "company" }, allowSetters = true)
    private UserInfo createdBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inspectionRoute")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    // @JsonIgnoreProperties(value = { "inspectionRoute", "parentGroup",
    // "subGroups", "equipments" }, allowSetters = true)
    private Set<InspectionRouteGroup> groups = new HashSet<>();

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

    public String getCode() {
        return this.code;
    }

    public InspectionRoute code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getMaintenancePlan() {
        return this.maintenancePlan;
    }

    public InspectionRoute maintenancePlan(String maintenancePlan) {
        this.setMaintenancePlan(maintenancePlan);
        return this;
    }

    public void setMaintenancePlan(String maintenancePlan) {
        this.maintenancePlan = maintenancePlan;
    }

    public Periodicity getPeriodicity() {
        return this.periodicity;
    }

    public InspectionRoute periodicity(Periodicity periodicity) {
        this.setPeriodicity(periodicity);
        return this;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public InspectionRoute duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getExpectedStartDate() {
        return this.expectedStartDate;
    }

    public InspectionRoute expectedStartDate(LocalDate expectedStartDate) {
        this.setExpectedStartDate(expectedStartDate);
        return this;
    }

    public void setExpectedStartDate(LocalDate expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
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

    public Set<InspectionRouteGroup> getGroups() {
        return this.groups;
    }

    public void setGroups(Set<InspectionRouteGroup> inspectionRouteGroups) {
        if (this.groups != null) {
            this.groups.forEach(i -> i.setInspectionRoute(null));
        }
        if (inspectionRouteGroups != null) {
            inspectionRouteGroups.forEach(i -> i.setInspectionRoute(this));
        }
        this.groups = inspectionRouteGroups;
    }

    public InspectionRoute groups(Set<InspectionRouteGroup> inspectionRouteGroups) {
        this.setGroups(inspectionRouteGroups);
        return this;
    }

    public InspectionRoute addGroups(InspectionRouteGroup inspectionRouteGroup) {
        this.groups.add(inspectionRouteGroup);
        inspectionRouteGroup.setInspectionRoute(this);
        return this;
    }

    public InspectionRoute removeGroups(InspectionRouteGroup inspectionRouteGroup) {
        this.groups.remove(inspectionRouteGroup);
        inspectionRouteGroup.setInspectionRoute(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

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
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InspectionRoute{" +
                "id=" + getId() +
                ", code='" + getCode() + "'" +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", maintenancePlan='" + getMaintenancePlan() + "'" +
                ", periodicity='" + getPeriodicity() + "'" +
                ", duration=" + getDuration() +
                ", expectedStartDate='" + getExpectedStartDate() + "'" +
                ", createdAt='" + getCreatedAt() + "'" +
                "}";
    }
}
