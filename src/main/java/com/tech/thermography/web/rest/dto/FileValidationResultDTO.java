package com.tech.thermography.web.rest.dto;

import java.util.List;

public class FileValidationResultDTO {

    public String fileId;
    public boolean isValid;
    public List<ValidationRowDTO> rowsToFix;
    public List<ValidationIssueDTO> issues;
}