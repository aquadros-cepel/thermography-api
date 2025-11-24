package com.tech.thermography.web.rest.dto;

import java.util.UUID;

public class InspectionRouteGroupEquipmentDTO {

    private UUID id;
    private Boolean included;
    private Integer orderIndex;
    private InspectionRouteGroupDTO inspectionRouteGroup;
    private EquipmentDTO equipment;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public InspectionRouteGroupDTO getInspectionRouteGroup() {
        return inspectionRouteGroup;
    }

    public void setInspectionRouteGroup(InspectionRouteGroupDTO inspectionRouteGroup) {
        this.inspectionRouteGroup = inspectionRouteGroup;
    }

    public EquipmentDTO getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentDTO equipment) {
        this.equipment = equipment;
    }
}
