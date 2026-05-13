package com.tech.thermography.web.rest.dto;

import com.tech.thermography.service.ThermogramMetadata;

public class FlirUploadResponseDTO {

    public String status;
    public String filename;
    public String imagePath;
    public String audioPath;
    public ThermogramMetadata metadata;
    public String image; // base64
    public String thermalFile;

    public FlirUploadResponseDTO() {}

    public FlirUploadResponseDTO(
        String status,
        String filename,
        String imagePath,
        String audioPath,
        ThermogramMetadata metadata,
        String image,
        String thermalFile
    ) {
        this.status = status;
        this.filename = filename;
        this.imagePath = imagePath;
        this.audioPath = audioPath;
        this.metadata = metadata;
        this.image = image;
        this.thermalFile = thermalFile;
    }
}
