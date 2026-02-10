package com.tech.thermography.web.rest.dto;

import java.util.List;

public class RevalidateRequestDTO {

    public String fileId;
    public List<ValidationRowDTO> rows;
}