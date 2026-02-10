package com.tech.thermography.web.rest.dto;

import java.util.List;

public class ValidateRequestDTO {

    public List<FileRefDTO> files;

    public static class FileRefDTO {
        public String id;
    }
}