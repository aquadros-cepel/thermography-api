package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tech.thermography.domain.enumeration.ConditionType;
import com.tech.thermography.domain.enumeration.ThermographicInspectionRecordType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ThermographicInspectionRecord.
 */
@Entity
@Table(name = "thermographic_inspection_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ThermographicInspectionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ThermographicInspectionRecordType type;

    @Column(name = "service_order")
    private String serviceOrder;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "analysis_description")
    private String analysisDescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "condition", nullable = false)
    private ConditionType condition;

    @NotNull
    @Column(name = "delta_t", nullable = false)
    private Double deltaT;

    @Column(name = "periodicity")
    private Integer periodicity;

    @Column(name = "deadline_execution")
    private LocalDate deadlineExecution;

    @Column(name = "next_monitoring")
    private LocalDate nextMonitoring;

    @Column(name = "recommendations")
    private String recommendations;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "company", "businessUnit" }, allowSetters = true)
    private Plant plant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plant", "createdBy", "startedBy", "finishedBy" }, allowSetters = true)
    private InspectionRoute route;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "plant", "group", "inspectionRouteGroups", "components" }, allowSetters = true)
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "componentTemperatureLimits", "equipments" }, allowSetters = true)
    private EquipmentComponent component;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "company" }, allowSetters = true)
    private UserInfo createdBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "company" }, allowSetters = true)
    private UserInfo finishedBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "equipment", "createdBy" }, allowSetters = true)
    private Thermogram thermogram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "equipment", "createdBy" }, allowSetters = true)
    private Thermogram thermogramRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ThermographicInspectionRecord id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ThermographicInspectionRecord name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ThermographicInspectionRecordType getType() {
        return this.type;
    }

    public ThermographicInspectionRecord type(ThermographicInspectionRecordType type) {
        this.setType(type);
        return this;
    }

    public void setType(ThermographicInspectionRecordType type) {
        this.type = type;
    }

    public String getServiceOrder() {
        return this.serviceOrder;
    }

    public ThermographicInspectionRecord serviceOrder(String serviceOrder) {
        this.setServiceOrder(serviceOrder);
        return this;
    }

    public void setServiceOrder(String serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public ThermographicInspectionRecord createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getAnalysisDescription() {
        return this.analysisDescription;
    }

    public ThermographicInspectionRecord analysisDescription(String analysisDescription) {
        this.setAnalysisDescription(analysisDescription);
        return this;
    }

    public void setAnalysisDescription(String analysisDescription) {
        this.analysisDescription = analysisDescription;
    }

    public ConditionType getCondition() {
        return this.condition;
    }

    public ThermographicInspectionRecord condition(ConditionType condition) {
        this.setCondition(condition);
        return this;
    }

    public void setCondition(ConditionType condition) {
        this.condition = condition;
    }

    public Double getDeltaT() {
        return this.deltaT;
    }

    public ThermographicInspectionRecord deltaT(Double deltaT) {
        this.setDeltaT(deltaT);
        return this;
    }

    public void setDeltaT(Double deltaT) {
        this.deltaT = deltaT;
    }

    public Integer getPeriodicity() {
        return this.periodicity;
    }

    public ThermographicInspectionRecord periodicity(Integer periodicity) {
        this.setPeriodicity(periodicity);
        return this;
    }

    public void setPeriodicity(Integer periodicity) {
        this.periodicity = periodicity;
    }

    public LocalDate getDeadlineExecution() {
        return this.deadlineExecution;
    }

    public ThermographicInspectionRecord deadlineExecution(LocalDate deadlineExecution) {
        this.setDeadlineExecution(deadlineExecution);
        return this;
    }

    public void setDeadlineExecution(LocalDate deadlineExecution) {
        this.deadlineExecution = deadlineExecution;
    }

    public LocalDate getNextMonitoring() {
        return this.nextMonitoring;
    }

    public ThermographicInspectionRecord nextMonitoring(LocalDate nextMonitoring) {
        this.setNextMonitoring(nextMonitoring);
        return this;
    }

    public void setNextMonitoring(LocalDate nextMonitoring) {
        this.nextMonitoring = nextMonitoring;
    }

    public String getRecommendations() {
        return this.recommendations;
    }

    public ThermographicInspectionRecord recommendations(String recommendations) {
        this.setRecommendations(recommendations);
        return this;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public ThermographicInspectionRecord finished(Boolean finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Instant getFinishedAt() {
        return this.finishedAt;
    }

    public ThermographicInspectionRecord finishedAt(Instant finishedAt) {
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

    public ThermographicInspectionRecord plant(Plant plant) {
        this.setPlant(plant);
        return this;
    }

    public InspectionRoute getRoute() {
        return this.route;
    }

    public void setRoute(InspectionRoute inspectionRoute) {
        this.route = inspectionRoute;
    }

    public ThermographicInspectionRecord route(InspectionRoute inspectionRoute) {
        this.setRoute(inspectionRoute);
        return this;
    }

    public Equipment getEquipment() {
        return this.equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public ThermographicInspectionRecord equipment(Equipment equipment) {
        this.setEquipment(equipment);
        return this;
    }

    public EquipmentComponent getComponent() {
        return this.component;
    }

    public void setComponent(EquipmentComponent equipmentComponent) {
        this.component = equipmentComponent;
    }

    public ThermographicInspectionRecord component(EquipmentComponent equipmentComponent) {
        this.setComponent(equipmentComponent);
        return this;
    }

    public UserInfo getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(UserInfo userInfo) {
        this.createdBy = userInfo;
    }

    public ThermographicInspectionRecord createdBy(UserInfo userInfo) {
        this.setCreatedBy(userInfo);
        return this;
    }

    public UserInfo getFinishedBy() {
        return this.finishedBy;
    }

    public void setFinishedBy(UserInfo userInfo) {
        this.finishedBy = userInfo;
    }

    public ThermographicInspectionRecord finishedBy(UserInfo userInfo) {
        this.setFinishedBy(userInfo);
        return this;
    }

    public Thermogram getThermogram() {
        return this.thermogram;
    }

    public void setThermogram(Thermogram thermogram) {
        this.thermogram = thermogram;
    }

    public ThermographicInspectionRecord thermogram(Thermogram thermogram) {
        this.setThermogram(thermogram);
        return this;
    }

    public Thermogram getThermogramRef() {
        return this.thermogramRef;
    }

    public void setThermogramRef(Thermogram thermogram) {
        this.thermogramRef = thermogram;
    }

    public ThermographicInspectionRecord thermogramRef(Thermogram thermogram) {
        this.setThermogramRef(thermogram);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThermographicInspectionRecord)) {
            return false;
        }
        return getId() != null && getId().equals(((ThermographicInspectionRecord) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThermographicInspectionRecord{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", serviceOrder='" + getServiceOrder() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", analysisDescription='" + getAnalysisDescription() + "'" +
            ", condition='" + getCondition() + "'" +
            ", deltaT=" + getDeltaT() +
            ", periodicity=" + getPeriodicity() +
            ", deadlineExecution='" + getDeadlineExecution() + "'" +
            ", nextMonitoring='" + getNextMonitoring() + "'" +
            ", recommendations='" + getRecommendations() + "'" +
            ", finished='" + getFinished() + "'" +
            ", finishedAt='" + getFinishedAt() + "'" +
            "}";
    }
}
