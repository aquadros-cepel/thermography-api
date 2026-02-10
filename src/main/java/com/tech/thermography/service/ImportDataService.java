package com.tech.thermography.service;

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
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
import com.tech.thermography.web.rest.dto.ApiUploadedFileMetaDTO;
import com.tech.thermography.web.rest.dto.EquipmentRowDataDTO;
import com.tech.thermography.web.rest.dto.FileImportResultDTO;
import com.tech.thermography.web.rest.dto.FileValidationResultDTO;
import com.tech.thermography.web.rest.dto.ImportRequestDTO;
import com.tech.thermography.web.rest.dto.ImportResponseDTO;
import com.tech.thermography.web.rest.dto.RevalidateRequestDTO;
import com.tech.thermography.web.rest.dto.ValidateRequestDTO;
import com.tech.thermography.web.rest.dto.ValidateResponseDTO;
import com.tech.thermography.web.rest.dto.ValidationIssueDTO;
import com.tech.thermography.web.rest.dto.ValidationRowDTO;

/**
 * Service for importing data from CSV files.
 */
@Service
@Transactional
public class ImportDataService {

    private static final Logger LOG = LoggerFactory.getLogger(ImportDataService.class);

    private static final Map<String, EquipmentType> EQUIPMENT_TYPE_BY_SIGLA = buildEquipmentTypeBySigla();

    private final Map<String, UploadedFile> uploadedFiles = new ConcurrentHashMap<>();
    private final Map<String, List<ValidationRowDTO>> cachedRowsByFileId = new ConcurrentHashMap<>();

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
            EquipmentComponentTemperatureLimitsRepository equipmentComponentTemperatureLimitsRepository) {
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

    /**
     * Imports plants, groups, subgroups, and equipments from an uploaded Excel file
     * InputStream.
     *
     * @param inputStream the InputStream of the uploaded Excel file
     * @return a success message with the number of imported equipments
     * @throws RuntimeException if an error occurs during import
     */
    public String importEquipmentsExcel(InputStream inputStream) {
        LOG.info("Starting import of equipments from uploaded Excel file");

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getNumberOfSheets() > 0 ? workbook.getSheetAt(0) : null;
            return importEquipmentsFromSheet(sheet);
        } catch (IOException e) {
            LOG.error("Error reading uploaded Excel file", e);
            throw new RuntimeException("Failed to import equipments from uploaded Excel file", e);
        } catch (RuntimeException e) {
            LOG.error("Error processing uploaded Excel file", e);
            throw new RuntimeException("Failed to import equipments from uploaded Excel file", e);
        }
    }

    public List<ApiUploadedFileMetaDTO> storeUploadedFiles(List<MultipartFile> files) {
        List<ApiUploadedFileMetaDTO> result = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                continue;
            }
            String filename = file.getOriginalFilename();
            if (filename == null
                    || !(filename.toLowerCase().endsWith(".xls") || filename.toLowerCase().endsWith(".xlsx"))) {
                continue;
            }
            try {
                UploadedFile uploadedFile = new UploadedFile();
                uploadedFile.id = UUID.randomUUID().toString();
                uploadedFile.name = filename;
                uploadedFile.size = file.getSize();
                uploadedFile.type = file.getContentType();
                uploadedFile.content = file.getBytes();

                uploadedFiles.put(uploadedFile.id, uploadedFile);

                ApiUploadedFileMetaDTO meta = new ApiUploadedFileMetaDTO();
                meta.id = uploadedFile.id;
                meta.name = uploadedFile.name;
                meta.size = uploadedFile.size;
                meta.type = uploadedFile.type;
                result.add(meta);
            } catch (IOException e) {
                LOG.warn("Failed to store uploaded file: {}", filename, e);
            }
        }

        return result;
    }

    public ValidateResponseDTO validateFiles(ValidateRequestDTO request) {
        ValidateResponseDTO response = new ValidateResponseDTO();
        response.results = new ArrayList<>();

        if (request == null || request.files == null) {
            return response;
        }

        for (ValidateRequestDTO.FileRefDTO fileRef : request.files) {
            FileValidationResultDTO result = new FileValidationResultDTO();
            result.fileId = fileRef == null ? null : fileRef.id;

            UploadedFile uploadedFile = result.fileId == null ? null : uploadedFiles.get(result.fileId);
            if (uploadedFile == null) {
                result.isValid = false;
                result.rowsToFix = List.of();
                result.issues = List.of(buildIssue(result.fileId, null, 0, "file", "Arquivo não encontrado.", "error"));
                response.results.add(result);
                continue;
            }

            List<ValidationRowDTO> rows = parseExcelToRows(uploadedFile.content);
            cachedRowsByFileId.put(result.fileId, rows);

            ValidationSummary summary = validateRows(result.fileId, rows);
            result.isValid = summary.isValid;
            result.rowsToFix = summary.rowsToFix;
            result.issues = summary.issues;
            response.results.add(result);
        }

        return response;
    }

    public FileValidationResultDTO revalidate(RevalidateRequestDTO request) {
        FileValidationResultDTO result = new FileValidationResultDTO();
        if (request == null) {
            result.isValid = false;
            result.rowsToFix = List.of();
            result.issues = List.of(buildIssue(null, null, 0, "request", "Requisição inválida.", "error"));
            return result;
        }

        result.fileId = request.fileId;
        List<ValidationRowDTO> rows = request.rows == null ? List.of() : request.rows;
        List<ValidationRowDTO> cachedRows = result.fileId == null ? null : cachedRowsByFileId.get(result.fileId);
        if (cachedRows != null && !rows.isEmpty()) {
            Map<String, ValidationRowDTO> byRowId = new HashMap<>();
            for (ValidationRowDTO cachedRow : cachedRows) {
                if (cachedRow != null && cachedRow.rowId != null) {
                    byRowId.put(cachedRow.rowId, cachedRow);
                }
            }
            for (ValidationRowDTO updatedRow : rows) {
                if (updatedRow != null && updatedRow.rowId != null) {
                    byRowId.put(updatedRow.rowId, updatedRow);
                }
            }
            cachedRows = new ArrayList<>(byRowId.values());
            cachedRowsByFileId.put(result.fileId, cachedRows);
        }

        List<ValidationRowDTO> rowsToValidate = cachedRows == null ? rows : cachedRows;
        ValidationSummary summary = validateRows(result.fileId, rowsToValidate);
        result.isValid = summary.isValid;
        result.rowsToFix = summary.rowsToFix;
        result.issues = summary.issues;
        return result;
    }

    public ImportResponseDTO importFiles(ImportRequestDTO request) {
        ImportResponseDTO response = new ImportResponseDTO();
        response.results = new ArrayList<>();

        if (request == null || request.files == null) {
            return response;
        }

        for (ImportRequestDTO.FileImportRefDTO fileRef : request.files) {
            FileImportResultDTO result = new FileImportResultDTO();
            result.fileId = fileRef == null ? null : fileRef.fileId;

            try {
                List<ValidationRowDTO> rows = result.fileId == null ? null : cachedRowsByFileId.get(result.fileId);
                if (rows == null) {
                    UploadedFile uploadedFile = result.fileId == null ? null : uploadedFiles.get(result.fileId);
                    if (uploadedFile != null) {
                        rows = parseExcelToRows(uploadedFile.content);
                    }
                }

                if (rows == null) {
                    result.success = false;
                    result.message = "Arquivo não encontrado para importação.";
                } else {
                    String message = importEquipmentsFromRows(rows);
                    result.success = true;
                    result.message = message;
                }
            } catch (RuntimeException e) {
                result.success = false;
                result.message = "Falha ao importar: " + e.getMessage();
            }

            response.results.add(result);
        }

        return response;
    }

    private String importEquipmentsFromReader(BufferedReader reader, DateTimeFormatter dateFormatter)
            throws IOException {
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
                if (!typeStr.isEmpty())
                    equipment.setType(EquipmentType.valueOf(typeStr.toUpperCase()));

                String phaseStr = values[13].trim();
                if (!phaseStr.isEmpty() && !"NULL".equalsIgnoreCase(phaseStr))
                    equipment.setPhaseType(
                            PhaseType.valueOf(phaseStr.toUpperCase()));

                String voltStr = values[12].trim();
                if (!voltStr.isEmpty() && !"NULL".equalsIgnoreCase(voltStr))
                    equipment.setVoltageClass(Float.parseFloat(voltStr));

                String dateStr = values[14].trim();
                if (!dateStr.isEmpty() && !"NULL".equalsIgnoreCase(dateStr))
                    equipment.setStartDate(LocalDate.parse(dateStr));

                String latStr = values[15].trim();
                if (!latStr.isEmpty() && !"NULL".equalsIgnoreCase(latStr))
                    equipment.setLatitude(Double.parseDouble(latStr));

                String lonStr = values[16].trim();
                if (!lonStr.isEmpty() && !"NULL".equalsIgnoreCase(lonStr))
                    equipment.setLongitude(Double.parseDouble(lonStr));

                // Build Hierarchy
                ImportPlant importPlant = plantsMap.computeIfAbsent(plantCode, k -> new ImportPlant(k));

                if (!groupCode.isEmpty()) {
                    ImportGroup importGroup = importPlant.groups.computeIfAbsent(groupCode,
                            k -> new ImportGroup(k, groupName));

                    if (!subgroupCode.isEmpty()) {
                        ImportSubgroup importSubgroup = importGroup.subgroups.computeIfAbsent(subgroupCode,
                                k -> new ImportSubgroup(k, subgroupName));
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
                errorMessages.add(String.format("Linha %d: Erro de conversão/enum. Detalhe: %s", totalRecords + 1,
                        e.getMessage()));
            } catch (Exception e) {
                errorMessages
                        .add(String.format("Linha %d: Erro inesperado. Detalhe: %s", totalRecords + 1, e.getMessage()));
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
                errorMessages
                        .add(String.format("Planta '%s' não encontrada. Equipamentos ignorados.", importPlant.code));
                continue;
            }

            for (ImportGroup importGroup : importPlant.groups.values()) {
                // Find or Create Parent Group
                EquipmentGroup parentGroup = findOrCreateGroup(importGroup.code, importGroup.name, plant, null);

                // Process Subgroups
                for (ImportSubgroup importSubgroup : importGroup.subgroups.values()) {
                    EquipmentGroup subgroup = findOrCreateGroup(importSubgroup.code, importSubgroup.name, plant,
                            parentGroup);

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
                    String.join("\n", errorMessages));
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

    private String importEquipmentsFromSheet(Sheet sheet) {
        if (sheet == null) {
            return "Planilha vazia. Nenhum registro importado.";
        }

        DataFormatter formatter = new DataFormatter();
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            return "Planilha vazia. Nenhum registro importado.";
        }

        List<ValidationRowDTO> rows = parseExcelToRows(sheet, headerRow, formatter);
        return importEquipmentsFromRows(rows);
    }

    private String importEquipmentsFromRows(List<ValidationRowDTO> rows) {
        if (rows == null || rows.isEmpty()) {
            return "Nenhum registro importado.";
        }

        int importedCount = 0;
        Map<String, ImportPlant> plantsMap = new HashMap<>();

        for (ValidationRowDTO row : rows) {
            if (row == null || row.data == null) {
                continue;
            }

            EquipmentRowDataDTO data = row.data;
            String plantCode = safeString(data.plant_code);
            String groupCode = safeString(data.group_code);
            String subgroupCode = safeString(data.subgroup_code);

            ImportPlant importPlant = plantsMap.computeIfAbsent(plantCode, k -> new ImportPlant(k));
            importPlant.updateMetadata(safeString(data.plant_name), parseDouble(stringValue(data.latitude)),
                    parseDouble(stringValue(data.longitude)));

            ImportGroup importGroup = importPlant.groups.computeIfAbsent(groupCode,
                    k -> new ImportGroup(k, safeString(data.group_name)));

            ImportSubgroup importSubgroup = importGroup.subgroups.computeIfAbsent(subgroupCode,
                    k -> new ImportSubgroup(k, safeString(data.subgroup_name)));

            Equipment equipment = new Equipment();
            equipment.setCode(safeString(data.equipment_code));
            equipment.setName(stringValue(data.equipment_name));
            equipment.setDescription(safeString(data.equipment_description));
            equipment.setType(parseEquipmentType(safeString(data.equipment_type)));

            importSubgroup.equipments.add(equipment);
        }

        for (ImportPlant importPlant : plantsMap.values()) {
            Plant plant = plantRepository.findByCode(importPlant.code).orElseGet(() -> {
                Plant newPlant = new Plant();
                newPlant.setCode(importPlant.code);
                return newPlant;
            });

            if (importPlant.name != null && !importPlant.name.isBlank()) {
                plant.setName(importPlant.name);
            }
            if (importPlant.latitude != null) {
                plant.setLatitude(importPlant.latitude);
            }
            if (importPlant.longitude != null) {
                plant.setLongitude(importPlant.longitude);
            }

            plant = plantRepository.save(plant);

            for (ImportGroup importGroup : importPlant.groups.values()) {
                EquipmentGroup parentGroup = findOrCreateGroup(importGroup.code, importGroup.name, plant, null);

                for (ImportSubgroup importSubgroup : importGroup.subgroups.values()) {
                    EquipmentGroup subgroup = findOrCreateGroup(importSubgroup.code, importSubgroup.name, plant,
                            parentGroup);

                    for (Equipment eq : importSubgroup.equipments) {
                        eq.setPlant(plant);
                        eq.setGroup(subgroup);
                        equipmentRepository.save(eq);
                        importedCount++;
                    }
                }
            }
        }

        return String.format("Importação concluída com sucesso. Total de %d equipamentos importados.", importedCount);
    }

    private Map<String, Integer> buildHeaderIndex(Row headerRow, DataFormatter formatter) {
        Map<String, Integer> headerIndex = new HashMap<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            String header = formatter.formatCellValue(headerRow.getCell(i));
            if (header == null || header.isBlank()) {
                continue;
            }
            headerIndex.put(normalizeHeader(header), i);
        }
        return headerIndex;
    }

    private String normalizeHeader(String header) {
        return header.trim().toLowerCase().replaceAll("\\s+", "_");
    }

    private boolean isRowEmpty(Row row, DataFormatter formatter) {
        for (int i = 0; i < row.getLastCellNum(); i++) {
            String value = formatter.formatCellValue(row.getCell(i));
            if (value != null && !value.isBlank()) {
                return false;
            }
        }
        return true;
    }

    private String getCellValue(Row row, Integer index, DataFormatter formatter) {
        if (index == null) {
            return "";
        }
        String value = formatter.formatCellValue(row.getCell(index));
        return value == null ? "" : value.trim();
    }

    private List<ValidationRowDTO> parseExcelToRows(byte[] content) {
        try (Workbook workbook = WorkbookFactory.create(new java.io.ByteArrayInputStream(content))) {
            Sheet sheet = workbook.getNumberOfSheets() > 0 ? workbook.getSheetAt(0) : null;
            if (sheet == null) {
                return List.of();
            }
            DataFormatter formatter = new DataFormatter();
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return List.of();
            }
            return parseExcelToRows(sheet, headerRow, formatter);
        } catch (IOException e) {
            LOG.error("Failed to parse excel content", e);
            return List.of();
        }
    }

    private List<ValidationRowDTO> parseExcelToRows(Sheet sheet, Row headerRow, DataFormatter formatter) {
        Map<String, Integer> headerIndex = buildHeaderIndex(headerRow, formatter);
        List<ValidationRowDTO> rows = new ArrayList<>();

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null || isRowEmpty(row, formatter)) {
                continue;
            }

            EquipmentRowDataDTO data = new EquipmentRowDataDTO();
            data.plant_code = getCellValue(row, headerIndex.get("plant_code"), formatter);
            data.plant_name = getCellValue(row, headerIndex.get("plant_name"), formatter);
            data.latitude = getCellValue(row, headerIndex.get("latitude"), formatter);
            data.longitude = getCellValue(row, headerIndex.get("longitude"), formatter);
            data.group_code = getCellValue(row, headerIndex.get("group_code"), formatter);
            data.group_name = getCellValue(row, headerIndex.get("group_name"), formatter);
            data.subgroup_code = getCellValue(row, headerIndex.get("subgroup_code"), formatter);
            data.subgroup_name = getCellValue(row, headerIndex.get("subgroup_name"), formatter);
            data.equipment_code = getCellValue(row, headerIndex.get("equipment_code"), formatter);
            data.equipment_name = getCellValue(row, headerIndex.get("equipment_name"), formatter);
            data.equipment_description = getCellValue(row, headerIndex.get("equipment_description"), formatter);
            data.equipment_type = getCellValue(row, headerIndex.get("equipment_type"), formatter);

            ValidationRowDTO rowDTO = new ValidationRowDTO();
            rowDTO.rowId = UUID.randomUUID().toString();
            rowDTO.rowIndex = rowIndex + 1;
            rowDTO.data = data;
            rows.add(rowDTO);
        }

        return rows;
    }

    private ValidationSummary validateRows(String fileId, List<ValidationRowDTO> rows) {
        ValidationSummary summary = new ValidationSummary();
        summary.rowsToFix = new ArrayList<>();
        summary.issues = new ArrayList<>();

        for (ValidationRowDTO row : rows) {
            if (row == null || row.data == null) {
                continue;
            }

            List<ValidationIssueDTO> rowIssues = new ArrayList<>();
            EquipmentRowDataDTO data = row.data;

            addRequiredIssue(fileId, row, "plant_code", data.plant_code, rowIssues);
            addRequiredIssue(fileId, row, "group_code", data.group_code, rowIssues);
            addRequiredIssue(fileId, row, "subgroup_code", data.subgroup_code, rowIssues);
            addRequiredIssue(fileId, row, "equipment_code", data.equipment_code, rowIssues);

            if (isBlank(stringValue(data.latitude)) || parseDouble(stringValue(data.latitude)) == null) {
                rowIssues
                        .add(buildIssue(fileId, row, row.rowIndex, "latitude", "Latitude inválida ou vazia.", "error"));
            }
            if (isBlank(stringValue(data.longitude)) || parseDouble(stringValue(data.longitude)) == null) {
                rowIssues.add(
                        buildIssue(fileId, row, row.rowIndex, "longitude", "Longitude inválida ou vazia.", "error"));
            }

            if (isBlank(data.group_name)) {
                rowIssues.add(buildIssue(fileId, row, row.rowIndex, "group_name", "Campo 'group_name' está vazio.",
                        "warning"));
            }
            if (isBlank(data.subgroup_name)) {
                rowIssues.add(buildIssue(fileId, row, row.rowIndex, "subgroup_name",
                        "Campo 'subgroup_name' está vazio.", "warning"));
            }
            if (isBlank(stringValue(data.equipment_name))) {
                rowIssues.add(buildIssue(fileId, row, row.rowIndex, "equipment_name",
                        "Campo 'equipment_name' está vazio.", "warning"));
            }
            if (isBlank(data.equipment_description)) {
                rowIssues.add(buildIssue(fileId, row, row.rowIndex, "equipment_description",
                        "Campo 'equipment_description' está vazio.", "warning"));
            }

            EquipmentType equipmentType = parseEquipmentType(data.equipment_type);
            if (equipmentType == null) {
                rowIssues.add(buildIssue(fileId, row, row.rowIndex, "equipment_type",
                        "Tipo de equipamento inválido.", "error"));
            }

            if (!rowIssues.isEmpty()) {
                summary.rowsToFix.add(row);
                summary.issues.addAll(rowIssues);
            }
        }

        summary.isValid = summary.issues.stream().noneMatch(issue -> "error".equalsIgnoreCase(issue.severity));
        return summary;
    }

    private void addRequiredIssue(String fileId, ValidationRowDTO row, String field, String value,
            List<ValidationIssueDTO> issues) {
        if (isBlank(value)) {
            issues.add(buildIssue(fileId, row, row.rowIndex, field, "Campo obrigatório vazio.", "error"));
        }
    }

    private ValidationIssueDTO buildIssue(String fileId, ValidationRowDTO row, int rowIndex, String field,
            String message, String severity) {
        ValidationIssueDTO issue = new ValidationIssueDTO();
        issue.id = UUID.randomUUID().toString();
        issue.fileId = fileId;
        issue.rowId = row == null ? null : row.rowId;
        issue.rowIndex = rowIndex;
        issue.field = field;
        issue.message = message;
        issue.severity = severity;
        issue.resolved = false;
        return issue;
    }

    private Double parseDouble(String value) {
        if (value == null || value.isBlank() || "NULL".equalsIgnoreCase(value)) {
            return null;
        }
        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isBlank();
    }

    private String safeString(String value) {
        return value == null ? "" : value.trim();
    }

    private String stringValue(Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value).trim();
    }

    private EquipmentType parseEquipmentType(String rawType) {
        if (rawType == null) {
            return null;
        }
        String normalized = rawType.trim().toUpperCase();
        if (normalized.isBlank()) {
            return null;
        }
        try {
            return EquipmentType.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return EQUIPMENT_TYPE_BY_SIGLA.get(normalized);
        }
    }

    private static Map<String, EquipmentType> buildEquipmentTypeBySigla() {
        Map<String, EquipmentType> map = new HashMap<>();
        map.put("TF", EquipmentType.POWER_TRANSFORMER);
        map.put("TC", EquipmentType.CURRENT_TRANSFORMER);
        map.put("TP", EquipmentType.POTENTIAL_TRANSFORMER);
        map.put("DJ", EquipmentType.CIRCUIT_BREAKER);
        map.put("SC", EquipmentType.DISCONNECT_SWITCH);
        map.put("PR", EquipmentType.SURGE_ARRESTER);
        map.put("CA", EquipmentType.CAPACITOR_BANK);
        map.put("BT", EquipmentType.BATTERY_BANK);
        map.put("PA", EquipmentType.ELECTRICAL_PANEL);
        map.put("GE", EquipmentType.EMERGENCY_GENERATOR);
        map.put("CB", EquipmentType.METAL_CLAD_SWITCHGEAR);
        map.put("BR", EquipmentType.BUSBAR);
        map.put("RD", EquipmentType.DISTRIBUTION_LINE);
        map.put("TR", EquipmentType.TRANSMISSION_TOWER);
        map.put("RE", EquipmentType.SHUNT_REACTOR);
        return map;
    }

    // --- Inner Classes for In-Memory Hierarchy ---

    private static class ImportPlant {

        String code;
        String name;
        Double latitude;
        Double longitude;
        Map<String, ImportGroup> groups = new HashMap<>();

        ImportPlant(String code) {
            this.code = code;
        }

        void updateMetadata(String name, Double latitude, Double longitude) {
            if (name != null && !name.isBlank()) {
                this.name = name;
            }
            if (latitude != null) {
                this.latitude = latitude;
            }
            if (longitude != null) {
                this.longitude = longitude;
            }
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

    private static class UploadedFile {
        String id;
        String name;
        long size;
        String type;
        byte[] content;
    }

    private static class ValidationSummary {
        boolean isValid;
        List<ValidationRowDTO> rowsToFix;
        List<ValidationIssueDTO> issues;
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
