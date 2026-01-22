package com.tech.thermography.web.rest;

import com.tech.thermography.service.ImportDataService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
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

        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
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
    @PostMapping("/components")
    public ResponseEntity<String> importComponents(@RequestParam("file") MultipartFile file) {
        LOG.debug("REST request to import components from uploaded file: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Uploaded file is empty");
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
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
}
