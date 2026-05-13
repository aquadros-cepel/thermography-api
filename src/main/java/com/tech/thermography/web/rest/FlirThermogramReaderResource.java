package com.tech.thermography.web.rest;

import com.tech.thermography.service.FlirThermogramReaderService;
import com.tech.thermography.service.ThermogramMetadata;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller to read FLIR thermogram files using
 * FlirThermogramReaderService.
 */
@RestController
@RequestMapping("/api/flir")
public class FlirThermogramReaderResource {

    private static final Logger log = LoggerFactory.getLogger(FlirThermogramReaderResource.class);

    @PostMapping("/uploadThermogram")
    public ResponseEntity<Object> uploadThermogram(@RequestParam("file") MultipartFile file) {
        log.debug("REST request to upload FLIR thermogram: {}", file == null ? "null" : file.getOriginalFilename());

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String originalFilename = file.getOriginalFilename();
        if (
            originalFilename == null ||
            !(originalFilename.toLowerCase().endsWith(".jpg") || originalFilename.toLowerCase().endsWith(".jpeg"))
        ) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // Save uploaded file to system temp directory
            Path uploadDir = Paths.get(System.getProperty("java.io.tmpdir"));
            Path target = uploadDir.resolve(originalFilename).toAbsolutePath();
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            File savedFile = target.toFile();

            // Read metadata using the existing service
            ThermogramMetadata metadata = FlirThermogramReaderService.readMetadata(savedFile);

            // Encode image to base64 for response
            String imageBase64 = null;
            try {
                byte[] bytes = Files.readAllBytes(target);
                imageBase64 = Base64.getEncoder().encodeToString(bytes);
            } catch (IOException e) {
                log.warn("Could not read saved file to encode image to base64", e);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("filename", originalFilename);
            response.put("imagePath", savedFile.getAbsolutePath());
            response.put("audioPath", "");
            response.put("metadata", metadata);
            response.put("image", imageBase64);
            response.put("thermalFile", savedFile.getAbsolutePath());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error processing uploaded FLIR thermogram", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
