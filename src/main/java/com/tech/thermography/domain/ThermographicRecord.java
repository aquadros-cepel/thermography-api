package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tech.thermography.domain.enumeration.ConditionType;
import com.tech.thermography.domain.enumeration.ThermographicRecordType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * A ThermographicRecord.
 */
@Entity
@Table(name = "thermographic_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ThermographicRecord implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private ThermographicRecordType type;

    @Column(name = "service_order")
    private String serviceOrder;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "analysis_description", length = 5000)
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

    @Column(name = "recommendations", length = 5000)
    private String recommendations;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Column(name = "current")
    private Double current;

    @Column(name = "voltage")
    private Double voltage;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "company", "businessUnit" }, allowSetters = true)
    private Plant plant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "plant", "inspectionRoute", "createdBy", "startedBy", "finishedBy", "groups" }, allowSetters = true)
    private InspectionRecord route;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "plant", "group", "components" }, allowSetters = true)
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
    private Thermogram thermogram;

    @ManyToOne(fetch = FetchType.LAZY)
    private Thermogram thermogramRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "plant",
            "route",
            "equipment",
            "component",
            "createdBy",
            "finishedBy",
            "thermogram",
            "thermogramRef",
            "parentRecord",
            "childRecords",
        },
        allowSetters = true
    )
    private ThermographicRecord parentRecord;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentRecord")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "plant",
            "route",
            "equipment",
            "component",
            "createdBy",
            "finishedBy",
            "thermogram",
            "thermogramRef",
            "parentRecord",
            "childRecords",
        },
        allowSetters = true
    )
    private Set<ThermographicRecord> childRecords = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public ThermographicRecord id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ThermographicRecord name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ThermographicRecordType getType() {
        return this.type;
    }

    public ThermographicRecord type(ThermographicRecordType type) {
        this.setType(type);
        return this;
    }

    public void setType(ThermographicRecordType type) {
        this.type = type;
    }

    public String getServiceOrder() {
        return this.serviceOrder;
    }

    public ThermographicRecord serviceOrder(String serviceOrder) {
        this.setServiceOrder(serviceOrder);
        return this;
    }

    public void setServiceOrder(String serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public ThermographicRecord createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getAnalysisDescription() {
        return this.analysisDescription;
    }

    public ThermographicRecord analysisDescription(String analysisDescription) {
        this.setAnalysisDescription(analysisDescription);
        return this;
    }

    public void setAnalysisDescription(String analysisDescription) {
        this.analysisDescription = analysisDescription;
    }

    public ConditionType getCondition() {
        return this.condition;
    }

    public ThermographicRecord condition(ConditionType condition) {
        this.setCondition(condition);
        return this;
    }

    public void setCondition(ConditionType condition) {
        this.condition = condition;
    }

    public Double getDeltaT() {
        return this.deltaT;
    }

    public ThermographicRecord deltaT(Double deltaT) {
        this.setDeltaT(deltaT);
        return this;
    }

    public void setDeltaT(Double deltaT) {
        this.deltaT = deltaT;
    }

    public Integer getPeriodicity() {
        return this.periodicity;
    }

    public ThermographicRecord periodicity(Integer periodicity) {
        this.setPeriodicity(periodicity);
        return this;
    }

    public void setPeriodicity(Integer periodicity) {
        this.periodicity = periodicity;
    }

    public LocalDate getDeadlineExecution() {
        return this.deadlineExecution;
    }

    public ThermographicRecord deadlineExecution(LocalDate deadlineExecution) {
        this.setDeadlineExecution(deadlineExecution);
        return this;
    }

    public void setDeadlineExecution(LocalDate deadlineExecution) {
        this.deadlineExecution = deadlineExecution;
    }

    public LocalDate getNextMonitoring() {
        return this.nextMonitoring;
    }

    public ThermographicRecord nextMonitoring(LocalDate nextMonitoring) {
        this.setNextMonitoring(nextMonitoring);
        return this;
    }

    public void setNextMonitoring(LocalDate nextMonitoring) {
        this.nextMonitoring = nextMonitoring;
    }

    public String getRecommendations() {
        return this.recommendations;
    }

    public ThermographicRecord recommendations(String recommendations) {
        this.setRecommendations(recommendations);
        return this;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public ThermographicRecord finished(Boolean finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Instant getFinishedAt() {
        return this.finishedAt;
    }

    public ThermographicRecord finishedAt(Instant finishedAt) {
        this.setFinishedAt(finishedAt);
        return this;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Double getCurrent() {
        return this.current;
    }

    public ThermographicRecord current(Double current) {
        this.setCurrent(current);
        return this;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Double getVoltage() {
        return this.voltage;
    }

    public ThermographicRecord voltage(Double voltage) {
        this.setVoltage(voltage);
        return this;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Double getWindSpeed() {
        return this.windSpeed;
    }

    public ThermographicRecord windSpeed(Double windSpeed) {
        this.setWindSpeed(windSpeed);
        return this;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Plant getPlant() {
        return this.plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public ThermographicRecord plant(Plant plant) {
        this.setPlant(plant);
        return this;
    }

    public InspectionRecord getRoute() {
        return this.route;
    }

    public void setRoute(InspectionRecord inspectionRecord) {
        this.route = inspectionRecord;
    }

    public ThermographicRecord route(InspectionRecord inspectionRecord) {
        this.setRoute(inspectionRecord);
        return this;
    }

    public Equipment getEquipment() {
        return this.equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public ThermographicRecord equipment(Equipment equipment) {
        this.setEquipment(equipment);
        return this;
    }

    public EquipmentComponent getComponent() {
        return this.component;
    }

    public void setComponent(EquipmentComponent equipmentComponent) {
        this.component = equipmentComponent;
    }

    public ThermographicRecord component(EquipmentComponent equipmentComponent) {
        this.setComponent(equipmentComponent);
        return this;
    }

    public UserInfo getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(UserInfo userInfo) {
        this.createdBy = userInfo;
    }

    public ThermographicRecord createdBy(UserInfo userInfo) {
        this.setCreatedBy(userInfo);
        return this;
    }

    public UserInfo getFinishedBy() {
        return this.finishedBy;
    }

    public void setFinishedBy(UserInfo userInfo) {
        this.finishedBy = userInfo;
    }

    public ThermographicRecord finishedBy(UserInfo userInfo) {
        this.setFinishedBy(userInfo);
        return this;
    }

    public Thermogram getThermogram() {
        return this.thermogram;
    }

    public void setThermogram(Thermogram thermogram) {
        this.thermogram = thermogram;
    }

    public ThermographicRecord thermogram(Thermogram thermogram) {
        this.setThermogram(thermogram);
        return this;
    }

    public Thermogram getThermogramRef() {
        return this.thermogramRef;
    }

    public void setThermogramRef(Thermogram thermogram) {
        this.thermogramRef = thermogram;
    }

    public ThermographicRecord thermogramRef(Thermogram thermogram) {
        this.setThermogramRef(thermogram);
        return this;
    }

    public ThermographicRecord getParentRecord() {
        return this.parentRecord;
    }

    public void setParentRecord(ThermographicRecord thermographicRecord) {
        this.parentRecord = thermographicRecord;
    }

    public ThermographicRecord parentRecord(ThermographicRecord thermographicRecord) {
        this.setParentRecord(thermographicRecord);
        return this;
    }

    public Set<ThermographicRecord> getChildRecords() {
        return this.childRecords;
    }

    public void setChildRecords(Set<ThermographicRecord> thermographicRecords) {
        if (this.childRecords != null) {
            this.childRecords.forEach(i -> i.setParentRecord(null));
        }
        if (thermographicRecords != null) {
            thermographicRecords.forEach(i -> i.setParentRecord(this));
        }
        this.childRecords = thermographicRecords;
    }

    public ThermographicRecord childRecords(Set<ThermographicRecord> thermographicRecords) {
        this.setChildRecords(thermographicRecords);
        return this;
    }

    public ThermographicRecord addChildRecords(ThermographicRecord thermographicRecord) {
        this.childRecords.add(thermographicRecord);
        thermographicRecord.setParentRecord(this);
        return this;
    }

    public ThermographicRecord removeChildRecords(ThermographicRecord thermographicRecord) {
        this.childRecords.remove(thermographicRecord);
        thermographicRecord.setParentRecord(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThermographicRecord)) {
            return false;
        }
        return getId() != null && getId().equals(((ThermographicRecord) o).getId());
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
        return "ThermographicRecord{" +
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
                ", current=" + getCurrent() +
                ", voltage=" + getVoltage() +
                ", windSpeed=" + getWindSpeed() +
                "}";
    }
}
