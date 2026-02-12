package com.tech.thermography.web.rest;

import com.tech.thermography.service.ImportDataService;
import com.tech.thermography.web.rest.dto.ApiUploadedFileMetaDTO;
import com.tech.thermography.web.rest.dto.FileValidationResultDTO;
import com.tech.thermography.web.rest.dto.RevalidateRequestDTO;
import com.tech.thermography.web.rest.dto.UploadResponseDTO;
import com.tech.thermography.web.rest.dto.ValidateRequestDTO;
import com.tech.thermography.web.rest.dto.ValidateResponseDTO;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for importing data from CSV files.
 */
@RestController
@RequestMapping("/api/import")
public class ImportDataResource {

    private static final Logger LOG = LoggerFactory.getLogger(ImportDataResource.class);

    private final ImportDataService importDataService;

    public ImportDataResource(ImportDataService importDataService) {
        this.importDataService = importDataService;
    }

    /**
     * {@code POST /import/plants} : Import plants from an uploaded CSV file.
     *
     * @param file the uploaded CSV file
     * @return the ResponseEntity with status 200 (OK) and the import result message
     */
    @PostMapping("/plants")
    public ResponseEntity<String> importPlants(@RequestParam("file") MultipartFile file) {
        LOG.debug("REST request to import plants from uploaded file: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Uploaded file is empty");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".csv")) {
            return ResponseEntity.badRequest().body("Only CSV files are allowed");
        }

        try {
            String result = importDataService.importPlants(file.getInputStream());
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            LOG.error("Error processing uploaded file", e);
            return ResponseEntity.internalServerError().body("Error processing uploaded file: " + e.getMessage());
        } catch (RuntimeException e) {
            LOG.error("Error importing plants", e);
            return ResponseEntity.badRequest().body("Error importing plants: " + e.getMessage());
        }
    }

    /**
     * {@code POST /import/equipments} : Import equipments from an uploaded CSV
     * file.
     *
     * @param file the uploaded CSV file
     * @return the ResponseEntity with status 200 (OK) and the import result message
     */
    @PostMapping("/equipments")
    public ResponseEntity<String> importEquipments(@RequestParam("file") MultipartFile file) {
        LOG.debug("REST request to import equipments from uploaded file: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Uploaded file is empty");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".csv")) {
            return ResponseEntity.badRequest().body("Only CSV files are allowed");
        }

        try {
            String result = importDataService.importEquipments(file.getInputStream());
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            LOG.error("Error processing uploaded file", e);
            return ResponseEntity.internalServerError().body("Error processing uploaded file: " + e.getMessage());
        } catch (RuntimeException e) {
            LOG.error("Error importing equipments", e);
            return ResponseEntity.badRequest().body("Error importing equipments: " + e.getMessage());
        }
    }

    /**
     * {@code POST /import/components} : Import componentes from an uploaded CSV
     * file.
     *
     * @param file the uploaded CSV file
     * @return the ResponseEntity with status 200 (OK) and the import result message
     */
    @PostMapping("/")
    public ResponseEntity<String> importComponents(@RequestParam("file") MultipartFile file) {
        LOG.debug("REST request to import components from uploaded file: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Uploaded file is empty");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".csv")) {
            return ResponseEntity.badRequest().body("Only CSV files are allowed");
        }

        try {
            String result = importDataService.importComponents(file.getInputStream());
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            LOG.error("Error processing uploaded file", e);
            return ResponseEntity.internalServerError().body("Error processing uploaded file: " + e.getMessage());
        } catch (RuntimeException e) {
            LOG.error("Error importing components", e);
            return ResponseEntity.badRequest().body("Error importing components: " + e.getMessage());
        }
    }

    // @PostMapping("/equipments/excel")
    // public ResponseEntity<String> importEquipmentsExcel(@RequestParam("file")
    // MultipartFile file) {
    // LOG.debug("REST request to import equipments from uploaded Excel file: {}",
    // file.getOriginalFilename());

    // if (file.isEmpty()) {
    // return ResponseEntity.badRequest().body("Uploaded file is empty");
    // }

    // String filename = file.getOriginalFilename();
    // if (filename == null
    // || !(filename.toLowerCase().endsWith(".xls") ||
    // filename.toLowerCase().endsWith(".xlsx"))) {
    // return ResponseEntity.badRequest().body("Only Excel files (.xls, .xlsx) are
    // allowed");
    // }

    // try {
    // String result =
    // importDataService.importEquipmentsExcel(file.getInputStream());
    // return ResponseEntity.ok(result);
    // } catch (IOException e) {
    // LOG.error("Error processing uploaded file", e);
    // return ResponseEntity.internalServerError().body("Error processing uploaded
    // file: " + e.getMessage());
    // } catch (RuntimeException e) {
    // LOG.error("Error importing equipments", e);
    // return ResponseEntity.badRequest().body("Error importing equipments: " +
    // e.getMessage());
    // }
    // }

    /**
     * {@code POST /import/equipments/validate} : Validate equipments from an
     * uploaded Excel file.
     *
     * @param file the uploaded Excel file
     * @return the ResponseEntity with status 200 (OK) and the validation result
     */
    @PostMapping("/equipments/excel/validate")
    public ResponseEntity<FileValidationResultDTO> validateEquipments(@RequestParam("file") MultipartFile file) {
        LOG.debug("REST request to validate equipments from uploaded Excel file: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            FileValidationResultDTO error = new FileValidationResultDTO();
            error.isValid = false;
            error.issues = java.util.List.of();
            error.rowsToFix = java.util.List.of();
            return ResponseEntity.badRequest().body(error);
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !(filename.toLowerCase().endsWith(".xls") || filename.toLowerCase().endsWith(".xlsx"))) {
            FileValidationResultDTO error = new FileValidationResultDTO();
            error.isValid = false;
            error.issues = java.util.List.of();
            error.rowsToFix = java.util.List.of();
            return ResponseEntity.badRequest().body(error);
        }

        try {
            FileValidationResultDTO result = importDataService.validateEquipmentsFile(file.getInputStream());
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            LOG.error("Error processing uploaded file", e);
            FileValidationResultDTO error = new FileValidationResultDTO();
            error.isValid = false;
            error.issues = java.util.List.of();
            error.rowsToFix = java.util.List.of();
            return ResponseEntity.internalServerError().body(error);
        } catch (RuntimeException e) {
            LOG.error("Error validating equipments", e);
            FileValidationResultDTO error = new FileValidationResultDTO();
            error.isValid = false;
            error.issues = java.util.List.of();
            error.rowsToFix = java.util.List.of();
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * {@code POST /import/equipments/import} : Import equipments from an uploaded
     * Excel file.
     *
     * @param file the uploaded Excel file
     * @return the ResponseEntity with status 200 (OK) and the import result message
     */
    @PostMapping("/equipments/excel")
    public ResponseEntity<String> importEquipmentsFromFile(@RequestParam("file") MultipartFile file) {
        LOG.debug("REST request to import equipments from uploaded Excel file: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Uploaded file is empty");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || !(filename.toLowerCase().endsWith(".xls") || filename.toLowerCase().endsWith(".xlsx"))) {
            return ResponseEntity.badRequest().body("Only Excel files (.xls, .xlsx) are allowed");
        }

        try {
            String result = importDataService.importEquipmentsExcel(file.getInputStream());
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            LOG.error("Error processing uploaded file", e);
            return ResponseEntity.internalServerError().body("Error processing uploaded file: " + e.getMessage());
        } catch (RuntimeException e) {
            LOG.error("Error importing equipments", e);
            return ResponseEntity.badRequest().body("Error importing equipments: " + e.getMessage());
        }
    }

    @PostMapping("/equipments/files")
    public ResponseEntity<UploadResponseDTO> uploadEquipmentFiles(@RequestParam("files") List<MultipartFile> files) {
        LOG.debug("REST request to upload equipments excel files");

        UploadResponseDTO response = new UploadResponseDTO();
        List<ApiUploadedFileMetaDTO> uploaded = importDataService.storeUploadedFiles(files);
        response.files = uploaded;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/equipments/validate")
    public ResponseEntity<ValidateResponseDTO> validateEquipments(@RequestBody ValidateRequestDTO request) {
        LOG.debug("REST request to validate equipments excel files");
        ValidateResponseDTO response = importDataService.validateFiles(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/equipments/revalidate")
    public ResponseEntity<FileValidationResultDTO> revalidateEquipments(@RequestBody RevalidateRequestDTO request) {
        LOG.debug("REST request to revalidate equipments data");
        FileValidationResultDTO response = importDataService.revalidate(request);
        return ResponseEntity.ok(response);
    }
}
