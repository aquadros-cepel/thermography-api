package com.tech.thermography.service;

import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.EquipmentGroup;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.domain.enumeration.EquipmentType;
import com.tech.thermography.domain.enumeration.PhaseType;
import com.tech.thermography.repository.EquipmentGroupRepository;
import com.tech.thermography.repository.EquipmentRepository;
import com.tech.thermography.repository.PlantRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for importing data from CSV files.
 */
@Service
@Transactional
public class ImportDataService {

    private static final Logger LOG = LoggerFactory.getLogger(ImportDataService.class);

    private final PlantRepository plantRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentGroupRepository equipmentGroupRepository;

    public ImportDataService(
        PlantRepository plantRepository,
        EquipmentRepository equipmentRepository,
        EquipmentGroupRepository equipmentGroupRepository
    ) {
        this.plantRepository = plantRepository;
        this.equipmentRepository = equipmentRepository;
        this.equipmentGroupRepository = equipmentGroupRepository;
    }

    /**
     * Imports plants from the specified CSV file path.
     *
     * @param csvPath the classpath path to the CSV file (e.g., "data/plants.csv")
     * @return a success message with the number of imported plants
     * @throws RuntimeException if an error occurs during import
     */
    public String importPlants(String csvPath) {
        LOG.info("Starting import of plants from CSV: {}", csvPath);

        ClassPathResource resource = new ClassPathResource(csvPath);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return importPlantsFromReader(reader, dateFormatter);
        } catch (IOException e) {
            LOG.error("Error reading CSV file: {}", csvPath, e);
            throw new RuntimeException("Failed to import plants from " + csvPath, e);
        }
    }

    /**
     * Imports plants from an uploaded file InputStream.
     *
     * @param inputStream the InputStream of the uploaded CSV file
     * @return a success message with the number of imported plants
     * @throws RuntimeException if an error occurs during import
     */
    public String importPlants(InputStream inputStream) {
        LOG.info("Starting import of plants from uploaded file");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return importPlantsFromReader(reader, dateFormatter);
        } catch (IOException e) {
            LOG.error("Error reading uploaded CSV file", e);
            throw new RuntimeException("Failed to import plants from uploaded file", e);
        }
    }

    /**
     * Common method to import plants from a BufferedReader.
     *
     * @param reader        the BufferedReader to read from
     * @param dateFormatter the DateTimeFormatter for parsing dates
     * @return a success message with the number of imported plants
     * @throws IOException if an error occurs during reading
     */
    private String importPlantsFromReader(BufferedReader reader, DateTimeFormatter dateFormatter) throws IOException {
        String line;
        boolean firstLine = true;
        int importedCount = 0;

        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                firstLine = false; // Skip header
                continue;
            }

            String[] fields = line.split(",");
            if (fields.length != 6) {
                LOG.warn("Skipping invalid line: {}", line);
                continue;
            }

            Plant plant = new Plant();
            plant.setName(fields[0].trim());
            plant.setTitle(fields[1].trim().isEmpty() ? null : fields[1].trim());
            plant.setDescription(fields[2].trim().isEmpty() ? null : fields[2].trim());

            try {
                plant.setLatitude(fields[3].trim().isEmpty() ? null : Double.parseDouble(fields[3].trim()));
            } catch (NumberFormatException e) {
                LOG.warn("Invalid latitude for plant {}: {}", plant.getName(), fields[3]);
                plant.setLatitude(null);
            }

            try {
                plant.setLongitude(fields[4].trim().isEmpty() ? null : Double.parseDouble(fields[4].trim()));
            } catch (NumberFormatException e) {
                LOG.warn("Invalid longitude for plant {}: {}", plant.getName(), fields[4]);
                plant.setLongitude(null);
            }

            String startDateStr = fields[5].trim();
            if (!startDateStr.isEmpty() && !"NULL".equalsIgnoreCase(startDateStr)) {
                try {
                    plant.setStartDate(LocalDate.parse(startDateStr, dateFormatter));
                } catch (Exception e) {
                    LOG.warn("Invalid start date for plant {}: {}", plant.getName(), startDateStr);
                    plant.setStartDate(null);
                }
            } else {
                plant.setStartDate(null);
            }

            plantRepository.save(plant);
            importedCount++;
        }

        LOG.info("Successfully imported {} plants", importedCount);
        return "Successfully imported " + importedCount + " plants";
    }

    /*
     * Imports equipments from an uploaded file InputStream.
     *
     * @param inputStream the InputStream of the uploaded CSV file
     *
     * @return a success message with the number of imported plants
     *
     * @throws RuntimeException if an error occurs during import
     */
    public String importEquipments(InputStream inputStream) {
        LOG.info("Starting import of plants from uploaded file");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return importEquipmentsFromReader(reader, dateFormatter);
        } catch (IOException e) {
            LOG.error("Error reading uploaded CSV file", e);
            throw new RuntimeException("Failed to import plants from uploaded file", e);
        }
    }

    private String importEquipmentsFromReader(BufferedReader reader, DateTimeFormatter dateFormatter) throws IOException {
        List<String> errorMessages = new ArrayList<>();
        int importedCount = 0;
        int totalRecords = 0;

        // Key: plantName + ":" + groupName, Value: EquipmentGroup
        Map<String, EquipmentGroup> groupCache = new HashMap<>();

        String line;
        boolean firstLine = true;

        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                firstLine = false; // Skip header
                continue;
            }

            totalRecords++;
            String[] values = line.split(",", -1); // -1 to keep trailing empty strings

            if (values.length < 15) { // Check for minimum expected columns
                errorMessages.add(String.format("Linha %d: Formato inválido (menos de 15 colunas).", totalRecords + 1));
                continue;
            }

            try {
                // --- PHASE 2: CSV Parsing and Initial Data Extraction ---
                String plantName = values[0].trim();
                String groupName = values[1].trim();
                String groupDescription = values[2].trim();
                String equipmentName = values[3].trim();
                String equipmentTitle = values[4].trim();
                String equipmentDescription = values[5].trim();
                String equipmentTypeStr = values[6].trim();
                String equipmentManufacturer = values[7].trim();
                String equipmentModel = values[8].trim();
                String equipmentSerialNumber = values[9].trim();
                String equipmentVoltageClassStr = values[10].trim();
                String equipmentPhaseType = values[11].trim();
                String equipmentStartDateStr = values[12].trim();
                String equipmentLatitudeStr = values[13].trim();
                String equipmentLongitudeStr = values[14].trim();

                // --- PHASE 3: Business Logic Implementation ---

                // 1. Plant Existence Check (Rule 1)
                Optional<Plant> plantOpt = plantRepository.findByName(plantName);
                if (plantOpt.isEmpty()) {
                    errorMessages.add(
                        String.format("Linha %d: Planta '%s' não encontrada. Equipamento não importado.", totalRecords + 1, plantName)
                    );
                    continue;
                }
                Plant plant = plantOpt.get();

                // 2. EquipmentGroup Handling (Rule 2)
                EquipmentGroup equipmentGroup = null;
                String groupCacheKey = plantName + ":" + groupName;

                boolean hasGroupData = !groupName.isEmpty() && !groupDescription.isEmpty();

                if (hasGroupData) {
                    // Check cache first
                    if (groupCache.containsKey(groupCacheKey)) {
                        equipmentGroup = groupCache.get(groupCacheKey);
                    } else {
                        // Try to find in DB
                        Optional<EquipmentGroup> groupOpt = equipmentGroupRepository.findByNameAndPlant(groupName, plant);
                        if (groupOpt.isPresent()) {
                            equipmentGroup = groupOpt.get();
                        } else {
                            // Create and save new group
                            EquipmentGroup newGroup = new EquipmentGroup();
                            newGroup.setName(groupName);
                            newGroup.setDescription(groupDescription);
                            newGroup.setPlant(plant);
                            equipmentGroup = equipmentGroupRepository.save(newGroup);
                        }
                        // Add to cache
                        groupCache.put(groupCacheKey, equipmentGroup);
                    }
                }
                // If hasGroupData is false, equipmentGroup remains null (Rule 3 satisfied: "Se
                // não existir o grupo no Map, colocar null")

                // 3. Equipment Creation (Rule 3)
                Equipment equipment = new Equipment();
                equipment.setName(equipmentName);
                equipment.setTitle(equipmentTitle);
                equipment.setDescription(equipmentDescription);
                equipment.setManufacturer(equipmentManufacturer);
                equipment.setModel(equipmentModel);
                equipment.setSerialNumber(equipmentSerialNumber);

                // Data Conversion
                EquipmentType equipmentType;
                try {
                    equipmentType = EquipmentType.valueOf(equipmentTypeStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    errorMessages.add(
                        String.format(
                            "Linha %d: Tipo de equipamento '%s' inválido. Equipamento não importado.",
                            totalRecords + 1,
                            equipmentTypeStr
                        )
                    );
                    continue;
                }

                PhaseType phaseType = null;
                if (!equipmentPhaseType.isEmpty() && !"NULL".equalsIgnoreCase(equipmentPhaseType)) {
                    try {
                        phaseType = PhaseType.valueOf(equipmentPhaseType.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        errorMessages.add(
                            String.format(
                                "Linha %d: Tipo de fase '%s' inválido. Equipamento não importado.",
                                totalRecords + 1,
                                equipmentPhaseType
                            )
                        );
                        continue;
                    }
                }

                Float voltageClass = equipmentVoltageClassStr.isEmpty() || "NULL".equalsIgnoreCase(equipmentVoltageClassStr)
                    ? null
                    : Float.parseFloat(equipmentVoltageClassStr);
                LocalDate startDate = equipmentStartDateStr.isEmpty() || "NULL".equalsIgnoreCase(equipmentStartDateStr)
                    ? null
                    : LocalDate.parse(equipmentStartDateStr);
                Double latitude = equipmentLatitudeStr.isEmpty() || "NULL".equalsIgnoreCase(equipmentLatitudeStr)
                    ? null
                    : Double.parseDouble(equipmentLatitudeStr);
                Double longitude = equipmentLongitudeStr.isEmpty() || "NULL".equalsIgnoreCase(equipmentLongitudeStr)
                    ? null
                    : Double.parseDouble(equipmentLongitudeStr);

                equipment.setType(equipmentType);
                equipment.setPhaseType(phaseType);
                equipment.setVoltageClass(voltageClass);
                equipment.setStartDate(startDate);
                equipment.setLatitude(latitude);
                equipment.setLongitude(longitude);

                // Associations
                equipment.setPlant(plant);
                equipment.setGroup(equipmentGroup);

                // Save Equipment
                equipmentRepository.save(equipment);

                // Increment count
                importedCount++;
            } catch (IllegalArgumentException e) {
                errorMessages.add(String.format("Linha %d: Erro de conversão de dados. Detalhe: %s", totalRecords + 1, e.getMessage()));
            } catch (Exception e) {
                errorMessages.add(
                    String.format("Linha %d: Erro inesperado ao processar o registro. Detalhe: %s", totalRecords + 1, e.getMessage())
                );
            }
        }

        // --- PHASE 4: Finalization ---
        if (!errorMessages.isEmpty()) {
            return String.format(
                "Importação concluída com erros. Total de registros processados: %d. Registros importados com sucesso: %d. Erros encontrados:\n%s",
                totalRecords,
                importedCount,
                String.join("\n", errorMessages)
            );
        }

        return String.format("Importação concluída com sucesso. Total de %d equipamentos importados.", importedCount);
    }
}
