package com.tech.thermography.web.rest.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class InspectionRouteDTO {

    private UUID id;
    private String code;
    private String name;
    private String description;
    private String maintenancePlan;
    private String periodicity;
    private Integer duration;
    private LocalDate expectedStartDate;
    private Instant createdAt;
    private PlantDTO plant;
    private UserInfoDTO createdBy;
    private Set<InspectionRouteGroupDTO> groups;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaintenancePlan() {
        return maintenancePlan;
    }

    public void setMaintenancePlan(String maintenancePlan) {
        this.maintenancePlan = maintenancePlan;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getExpectedStartDate() {
        return expectedStartDate;
    }

    public void setExpectedStartDate(LocalDate expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public PlantDTO getPlant() {
        return plant;
    }

    public void setPlant(PlantDTO plant) {
        this.plant = plant;
    }

    public UserInfoDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserInfoDTO createdBy) {
        this.createdBy = createdBy;
    }

    public Set<InspectionRouteGroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(Set<InspectionRouteGroupDTO> groups) {
        this.groups = groups;
    }
}
