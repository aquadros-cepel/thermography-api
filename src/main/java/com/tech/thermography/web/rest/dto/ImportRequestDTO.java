package com.tech.thermography.web.rest.dto;

import java.util.List;

public class ImportRequestDTO {

    public List<FileImportRefDTO> files;

    public static class FileImportRefDTO {
        public String fileId;
    }
}