package com.tech.thermography.web.rest.dto;

import java.util.Set;
import java.util.UUID;

public class InspectionRouteGroupDTO {

    private UUID id;
    private String code;
    private String name;
    private String description;
    private Boolean included;
    private Integer orderIndex;
    private InspectionRouteDTO inspectionRoute;
    private InspectionRouteGroupDTO parentGroup;
    private Set<InspectionRouteGroupDTO> subGroups;
    private Set<InspectionRouteGroupEquipmentDTO> equipments;

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

    public Boolean getIncluded() {
        return included;
    }

    public void setIncluded(Boolean included) {
        this.included = included;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public InspectionRouteDTO getInspectionRoute() {
        return inspectionRoute;
    }

    public void setInspectionRoute(InspectionRouteDTO inspectionRoute) {
        this.inspectionRoute = inspectionRoute;
    }

    public InspectionRouteGroupDTO getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(InspectionRouteGroupDTO parentGroup) {
        this.parentGroup = parentGroup;
    }

    public Set<InspectionRouteGroupDTO> getSubGroups() {
        return subGroups;
    }

    public void setSubGroups(Set<InspectionRouteGroupDTO> subGroups) {
        this.subGroups = subGroups;
    }

    public Set<InspectionRouteGroupEquipmentDTO> getEquipments() {
        return equipments;
    }

    public void setEquipments(Set<InspectionRouteGroupEquipmentDTO> equipments) {
        this.equipments = equipments;
    }
}
