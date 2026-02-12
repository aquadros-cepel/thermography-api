package com.tech.thermography.web.rest.dto;

import java.util.List;
import java.util.UUID;

public class ThermographicInspectionRecordCreateDTO {

    public UUID id;
    public String name;
    public String type;
    public IdRefDTO equipment;
    public PlantRefDTO plant;
    public String createdAt;
    public String deadlineExecution;
    public String nextMonitoring;
    public String condition;
    public Double deltaT;
    public Integer periodicity;
    public String serviceOrder;
    public String analysisDescription;
    public String recommendations;
    public Boolean finished;
    public IdRefDTO component;
    public ThermogramDTO thermogram;
    public ThermogramDTO thermogramRef;
    public IdRefDTO route;
    public IdRefDTO createdBy;
    public IdRefDTO finishedBy;

    public static class IdRefDTO {

        public UUID id;
    }

    public static class PlantRefDTO extends IdRefDTO {

        public String name;
        public String code;
    }

    public static class ThermogramDTO {

        public UUID id;
        public String imagePath;
        public String audioPath;
        public String imageRefPath;
        public Double minTemp;
        public Double avgTemp;
        public Double maxTemp;
        public Double emissivity;
        public Double subjectDistance;
        public Double atmosphericTemp;
        public Double reflectedTemp;
        public Double relativeHumidity;
        public String cameraLens;
        public String cameraModel;
        public String imageResolution;
        public UUID selectedRoiId;
        public Double maxTempRoi;
        public String createdAt;
        public Double latitude;
        public Double longitude;
        public List<RoiDTO> rois;
    }

    public static class RoiDTO {

        public UUID id;
        public String type;
        public String label;
        public Double maxTemp;
    }
}
