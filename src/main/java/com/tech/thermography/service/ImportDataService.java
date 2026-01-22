package com.tech.thermography.service;

import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.EquipmentComponent;
import com.tech.thermography.domain.EquipmentComponentTemperatureLimits;
import com.tech.thermography.domain.EquipmentGroup;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.domain.enumeration.EquipmentType;
import com.tech.thermography.domain.enumeration.PhaseType;
import com.tech.thermography.repository.EquipmentComponentRepository;
import com.tech.thermography.repository.EquipmentComponentTemperatureLimitsRepository;
import com.tech.thermography.repository.EquipmentGroupRepository;
import com.tech.thermography.repository.EquipmentRepository;
import com.tech.thermography.repository.PlantRepository;
import com.tech.thermography.web.rest.NotFoundException;
import java.io.BufferedReader;
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
    private final EquipmentComponentRepository equipmentComponentRepository;
    private final EquipmentComponentTemperatureLimitsRepository equipmentComponentTemperatureLimitsRepository;

    public ImportDataService(
        PlantRepository plantRepository,
        EquipmentRepository equipmentRepository,
        EquipmentGroupRepository equipmentGroupRepository,
        EquipmentComponentRepository equipmentComponentRepository,
        EquipmentComponentTemperatureLimitsRepository equipmentComponentTemperatureLimitsRepository
    ) {
        this.plantRepository = plantRepository;
        this.equipmentRepository = equipmentRepository;
        this.equipmentGroupRepository = equipmentGroupRepository;
        this.equipmentComponentRepository = equipmentComponentRepository;
        this.equipmentComponentTemperatureLimitsRepository = equipmentComponentTemperatureLimitsRepository;
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
            plant.setCode(fields[0].trim());
            plant.setName(fields[1].trim().isEmpty() ? null : fields[1].trim());
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

        // --- PHASE 1: Read CSV and Build Hierarchy (In-Memory) ---
        Map<String, ImportPlant> plantsMap = new HashMap<>(); // Key: plantCode

        String line;
        boolean firstLine = true;

        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                firstLine = false; // Skip header
                continue;
            }

            totalRecords++;
            String[] values = line.split(",", -1);

            if (values.length < 17) {
                errorMessages.add(String.format("Linha %d: Formato inválido (menos de 17 colunas).", totalRecords + 1));
                continue;
            }

            try {
                // Extract Data
                String plantCode = values[0].trim();
                String groupCode = values[1].trim();
                String groupName = values[2].trim();
                String subgroupCode = values[3].trim();
                String subgroupName = values[4].trim();

                // Equipment Data
                Equipment equipment = new Equipment();
                equipment.setCode(values[5].trim());
                equipment.setName(values[6].trim());
                equipment.setDescription(values[7].trim());
                equipment.setManufacturer(values[9].trim());
                equipment.setModel(values[10].trim());
                equipment.setSerialNumber(values[11].trim());

                // Enums and Parsed fields
                String typeStr = values[8].trim();
                if (!typeStr.isEmpty()) equipment.setType(EquipmentType.valueOf(typeStr.toUpperCase()));

                String phaseStr = values[13].trim();
                if (!phaseStr.isEmpty() && !"NULL".equalsIgnoreCase(phaseStr)) equipment.setPhaseType(
                    PhaseType.valueOf(phaseStr.toUpperCase())
                );

                String voltStr = values[12].trim();
                if (!voltStr.isEmpty() && !"NULL".equalsIgnoreCase(voltStr)) equipment.setVoltageClass(Float.parseFloat(voltStr));

                String dateStr = values[14].trim();
                if (!dateStr.isEmpty() && !"NULL".equalsIgnoreCase(dateStr)) equipment.setStartDate(LocalDate.parse(dateStr));

                String latStr = values[15].trim();
                if (!latStr.isEmpty() && !"NULL".equalsIgnoreCase(latStr)) equipment.setLatitude(Double.parseDouble(latStr));

                String lonStr = values[16].trim();
                if (!lonStr.isEmpty() && !"NULL".equalsIgnoreCase(lonStr)) equipment.setLongitude(Double.parseDouble(lonStr));

                // Build Hierarchy
                ImportPlant importPlant = plantsMap.computeIfAbsent(plantCode, k -> new ImportPlant(k));

                if (!groupCode.isEmpty()) {
                    ImportGroup importGroup = importPlant.groups.computeIfAbsent(groupCode, k -> new ImportGroup(k, groupName));

                    if (!subgroupCode.isEmpty()) {
                        ImportSubgroup importSubgroup = importGroup.subgroups.computeIfAbsent(subgroupCode, k ->
                            new ImportSubgroup(k, subgroupName)
                        );
                        importSubgroup.equipments.add(equipment);
                    } else {
                        // Equipment directly under Group (if allowed/possible logic, though req says eq
                        // -> subgroup)
                        // For now, let's assume strict hierarchy or handle as needed.
                        // If subgroup is empty, we might attach to group directly or warn.
                        // Based on user req: "equipamentos têm que possuir uma referencia para o
                        // subgrupo".
                        // So if no subgroup, maybe we skip or attach to group?
                        // Let's attach to group via a "default" or just keep it in group list if we
                        // added one.
                        // But ImportGroup structure below needs a list of equipments too if we support
                        // that.
                        // Let's add equipments list to ImportGroup as fallback.
                        importGroup.directEquipments.add(equipment);
                    }
                }
            } catch (IllegalArgumentException e) {
                errorMessages.add(String.format("Linha %d: Erro de conversão/enum. Detalhe: %s", totalRecords + 1, e.getMessage()));
            } catch (Exception e) {
                errorMessages.add(String.format("Linha %d: Erro inesperado. Detalhe: %s", totalRecords + 1, e.getMessage()));
            }
        }

        // --- PHASE 2: Persist Hierarchy ---

        for (ImportPlant importPlant : plantsMap.values()) {
            Plant plant;
            try {
                plant = plantRepository
                    .findByCode(importPlant.code)
                    .orElseThrow(() -> new NotFoundException("Plant not found with code: " + importPlant.code));
            } catch (NotFoundException e) {
                errorMessages.add(String.format("Planta '%s' não encontrada. Equipamentos ignorados.", importPlant.code));
                continue;
            }

            for (ImportGroup importGroup : importPlant.groups.values()) {
                // Find or Create Parent Group
                EquipmentGroup parentGroup = findOrCreateGroup(importGroup.code, importGroup.name, plant, null);

                // Process Subgroups
                for (ImportSubgroup importSubgroup : importGroup.subgroups.values()) {
                    EquipmentGroup subgroup = findOrCreateGroup(importSubgroup.code, importSubgroup.name, plant, parentGroup);

                    // Save Equipments for Subgroup
                    for (Equipment eq : importSubgroup.equipments) {
                        eq.setPlant(plant);
                        eq.setGroup(subgroup);
                        equipmentRepository.save(eq);
                        importedCount++;
                    }
                }

                // Process Direct Equipments (Fallback)
                for (Equipment eq : importGroup.directEquipments) {
                    eq.setPlant(plant);
                    eq.setGroup(parentGroup);
                    equipmentRepository.save(eq);
                    importedCount++;
                }
            }
        }

        if (!errorMessages.isEmpty()) {
            return String.format(
                "Importação concluída com erros. Total lido: %d. Importados: %d. Erros:\n%s",
                totalRecords,
                importedCount,
                String.join("\n", errorMessages)
            );
        }

        return String.format("Importação concluída com sucesso. Total de %d equipamentos importados.", importedCount);
    }

    private EquipmentGroup findOrCreateGroup(String code, String name, Plant plant, EquipmentGroup parent) {
        Optional<EquipmentGroup> opt;
        if (parent == null) {
            opt = equipmentGroupRepository.findFirstByCodeAndPlantAndParentGroupIsNull(code, plant);
        } else {
            opt = equipmentGroupRepository.findFirstByCodeAndPlantAndParentGroup(code, null, parent);
        }

        return opt.orElseGet(() -> {
            EquipmentGroup group = new EquipmentGroup();
            group.setCode(code);
            group.setName(name);
            if (parent != null) {
                group.setPlant(null);
            } else {
                group.setPlant(plant);
            }
            group.setParentGroup(parent);
            return equipmentGroupRepository.save(group);
        });
    }

    // --- Inner Classes for In-Memory Hierarchy ---

    private static class ImportPlant {

        String code;
        Map<String, ImportGroup> groups = new HashMap<>();

        ImportPlant(String code) {
            this.code = code;
        }
    }

    private static class ImportGroup {

        String code;
        String name;
        Map<String, ImportSubgroup> subgroups = new HashMap<>();
        List<Equipment> directEquipments = new ArrayList<>();

        ImportGroup(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    private static class ImportSubgroup {

        String code;
        String name;
        List<Equipment> equipments = new ArrayList<>();

        ImportSubgroup(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    /**
     * Imports components from an uploaded file InputStream.
     *
     * @param inputStream the InputStream of the uploaded CSV file
     * @return a success message with the number of imported components
     * @throws RuntimeException if an error occurs during import
     */
    public String importComponents(InputStream inputStream) {
        LOG.info("Starting import of components from uploaded file");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return importComponentsFromReader(reader);
        } catch (IOException e) {
            LOG.error("Error reading uploaded CSV file", e);
            throw new RuntimeException("Failed to import components from uploaded file", e);
        }
    }

    /**
     * Common method to import components from a BufferedReader.
     *
     * @param reader the BufferedReader to read from
     * @return a success message with the number of imported components
     * @throws IOException if an error occurs during reading
     */
    private String importComponentsFromReader(BufferedReader reader) throws IOException {
        String line;
        boolean firstLine = true;
        int importedCount = 0;

        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                firstLine = false; // Skip header
                continue;
            }

            String[] fields = line.split(";");
            if (fields.length != 7) {
                LOG.warn("Skipping invalid line: {}", line);
                continue;
            }

            EquipmentComponent equipmentComponent = new EquipmentComponent();
            equipmentComponent.setDescription(fields[0].trim());
            equipmentComponent.setCode(fields[1].trim());
            equipmentComponent.setName(fields[2].trim());
            equipmentComponent = equipmentComponentRepository.save(equipmentComponent);

            EquipmentComponentTemperatureLimits equipmentComponentTemperatureLimits = new EquipmentComponentTemperatureLimits();
            equipmentComponentTemperatureLimits.setEquipmentComponent(equipmentComponent);
            equipmentComponentTemperatureLimits.setName(fields[0].trim());
            equipmentComponentTemperatureLimits.setNormal("NA");
            equipmentComponentTemperatureLimits.setLowRisk(fields[3].trim());
            equipmentComponentTemperatureLimits.setMediumRisk(fields[4].trim());
            equipmentComponentTemperatureLimits.setHighRisk(fields[5].trim());
            equipmentComponentTemperatureLimits.setImminentHighRisk(fields[6].trim());
            equipmentComponentTemperatureLimitsRepository.save(equipmentComponentTemperatureLimits);
            importedCount++;
        }

        LOG.info("Successfully imported {} components", importedCount);
        return "Successfully imported " + importedCount + " components";
    }
}
