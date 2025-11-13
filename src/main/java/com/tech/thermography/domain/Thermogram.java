package com.tech.thermography.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Thermogram.
 */
@Entity
@Table(name = "thermogram")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Thermogram implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Column(name = "audio_path")
    private String audioPath;

    @NotNull
    @Column(name = "image_ref_path", nullable = false)
    private String imageRefPath;

    @Column(name = "min_temp")
    private Double minTemp;

    @Column(name = "avg_temp")
    private Double avgTemp;

    @Column(name = "max_temp")
    private Double maxTemp;

    @Column(name = "emissivity")
    private Double emissivity;

    @Column(name = "subject_distance")
    private Double subjectDistance;

    @Column(name = "atmospheric_temp")
    private Double atmosphericTemp;

    @Column(name = "reflected_temp")
    private Double reflectedTemp;

    @Column(name = "relative_humidity")
    private Double relativeHumidity;

    @Column(name = "camera_lens")
    private String cameraLens;

    @Column(name = "camera_model")
    private String cameraModel;

    @Column(name = "image_resolution")
    private String imageResolution;

    @Column(name = "selected_roi_id")
    private UUID selectedRoiId;

    @Column(name = "max_temp_roi")
    private Double maxTempRoi;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "plant", "group", "inspectionRouteGroups", "components" }, allowSetters = true)
    private Equipment equipment;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "company" }, allowSetters = true)
    private UserInfo createdBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Thermogram id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public Thermogram imagePath(String imagePath) {
        this.setImagePath(imagePath);
        return this;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAudioPath() {
        return this.audioPath;
    }

    public Thermogram audioPath(String audioPath) {
        this.setAudioPath(audioPath);
        return this;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getImageRefPath() {
        return this.imageRefPath;
    }

    public Thermogram imageRefPath(String imageRefPath) {
        this.setImageRefPath(imageRefPath);
        return this;
    }

    public void setImageRefPath(String imageRefPath) {
        this.imageRefPath = imageRefPath;
    }

    public Double getMinTemp() {
        return this.minTemp;
    }

    public Thermogram minTemp(Double minTemp) {
        this.setMinTemp(minTemp);
        return this;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Double getAvgTemp() {
        return this.avgTemp;
    }

    public Thermogram avgTemp(Double avgTemp) {
        this.setAvgTemp(avgTemp);
        return this;
    }

    public void setAvgTemp(Double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public Double getMaxTemp() {
        return this.maxTemp;
    }

    public Thermogram maxTemp(Double maxTemp) {
        this.setMaxTemp(maxTemp);
        return this;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Double getEmissivity() {
        return this.emissivity;
    }

    public Thermogram emissivity(Double emissivity) {
        this.setEmissivity(emissivity);
        return this;
    }

    public void setEmissivity(Double emissivity) {
        this.emissivity = emissivity;
    }

    public Double getSubjectDistance() {
        return this.subjectDistance;
    }

    public Thermogram subjectDistance(Double subjectDistance) {
        this.setSubjectDistance(subjectDistance);
        return this;
    }

    public void setSubjectDistance(Double subjectDistance) {
        this.subjectDistance = subjectDistance;
    }

    public Double getAtmosphericTemp() {
        return this.atmosphericTemp;
    }

    public Thermogram atmosphericTemp(Double atmosphericTemp) {
        this.setAtmosphericTemp(atmosphericTemp);
        return this;
    }

    public void setAtmosphericTemp(Double atmosphericTemp) {
        this.atmosphericTemp = atmosphericTemp;
    }

    public Double getReflectedTemp() {
        return this.reflectedTemp;
    }

    public Thermogram reflectedTemp(Double reflectedTemp) {
        this.setReflectedTemp(reflectedTemp);
        return this;
    }

    public void setReflectedTemp(Double reflectedTemp) {
        this.reflectedTemp = reflectedTemp;
    }

    public Double getRelativeHumidity() {
        return this.relativeHumidity;
    }

    public Thermogram relativeHumidity(Double relativeHumidity) {
        this.setRelativeHumidity(relativeHumidity);
        return this;
    }

    public void setRelativeHumidity(Double relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public String getCameraLens() {
        return this.cameraLens;
    }

    public Thermogram cameraLens(String cameraLens) {
        this.setCameraLens(cameraLens);
        return this;
    }

    public void setCameraLens(String cameraLens) {
        this.cameraLens = cameraLens;
    }

    public String getCameraModel() {
        return this.cameraModel;
    }

    public Thermogram cameraModel(String cameraModel) {
        this.setCameraModel(cameraModel);
        return this;
    }

    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }

    public String getImageResolution() {
        return this.imageResolution;
    }

    public Thermogram imageResolution(String imageResolution) {
        this.setImageResolution(imageResolution);
        return this;
    }

    public void setImageResolution(String imageResolution) {
        this.imageResolution = imageResolution;
    }

    public UUID getSelectedRoiId() {
        return this.selectedRoiId;
    }

    public Thermogram selectedRoiId(UUID selectedRoiId) {
        this.setSelectedRoiId(selectedRoiId);
        return this;
    }

    public void setSelectedRoiId(UUID selectedRoiId) {
        this.selectedRoiId = selectedRoiId;
    }

    public Double getMaxTempRoi() {
        return this.maxTempRoi;
    }

    public Thermogram maxTempRoi(Double maxTempRoi) {
        this.setMaxTempRoi(maxTempRoi);
        return this;
    }

    public void setMaxTempRoi(Double maxTempRoi) {
        this.maxTempRoi = maxTempRoi;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Thermogram createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Thermogram latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Thermogram longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Equipment getEquipment() {
        return this.equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Thermogram equipment(Equipment equipment) {
        this.setEquipment(equipment);
        return this;
    }

    public UserInfo getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(UserInfo userInfo) {
        this.createdBy = userInfo;
    }

    public Thermogram createdBy(UserInfo userInfo) {
        this.setCreatedBy(userInfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thermogram)) {
            return false;
        }
        return getId() != null && getId().equals(((Thermogram) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thermogram{" +
            "id=" + getId() +
            ", imagePath='" + getImagePath() + "'" +
            ", audioPath='" + getAudioPath() + "'" +
            ", imageRefPath='" + getImageRefPath() + "'" +
            ", minTemp=" + getMinTemp() +
            ", avgTemp=" + getAvgTemp() +
            ", maxTemp=" + getMaxTemp() +
            ", emissivity=" + getEmissivity() +
            ", subjectDistance=" + getSubjectDistance() +
            ", atmosphericTemp=" + getAtmosphericTemp() +
            ", reflectedTemp=" + getReflectedTemp() +
            ", relativeHumidity=" + getRelativeHumidity() +
            ", cameraLens='" + getCameraLens() + "'" +
            ", cameraModel='" + getCameraModel() + "'" +
            ", imageResolution='" + getImageResolution() + "'" +
            ", selectedRoiId='" + getSelectedRoiId() + "'" +
            ", maxTempRoi=" + getMaxTempRoi() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
