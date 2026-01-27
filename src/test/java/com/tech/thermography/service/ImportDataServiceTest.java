package com.tech.thermography.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tech.thermography.domain.Equipment;
import com.tech.thermography.domain.EquipmentGroup;
import com.tech.thermography.domain.Plant;
import com.tech.thermography.repository.EquipmentComponentRepository;
import com.tech.thermography.repository.EquipmentComponentTemperatureLimitsRepository;
import com.tech.thermography.repository.EquipmentGroupRepository;
import com.tech.thermography.repository.EquipmentRepository;
import com.tech.thermography.repository.PlantRepository;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ImportDataServiceTest {

    @Mock
    private PlantRepository plantRepository;

    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private EquipmentGroupRepository equipmentGroupRepository;

    @Mock
    private EquipmentComponentRepository equipmentComponentRepository;

    @Mock
    private EquipmentComponentTemperatureLimitsRepository equipmentComponentTemperatureLimitsRepository;

    private ImportDataService importDataService;

    @BeforeEach
    void setUp() {
        importDataService = new ImportDataService(
            plantRepository,
            equipmentRepository,
            equipmentGroupRepository,
            equipmentComponentRepository,
            equipmentComponentTemperatureLimitsRepository
        );
    }

    @Test
    void shouldImportEquipmentsWithSubgroups() {
        // Given
        String csvContent =
            "plant_code,group_code,group_name,subgroup_code,subgroup_name,equipment_code,equipment_name,equipment_description,equipment_type,equipment_manufacturer,equipment_model,equipment_serial_number,equipment_voltage_class,equipment_phase_type,equipment_start_date,equipment_latitude,equipment_longitude\n" +
            "PLANT1,GROUP1,Group One,SUB1,Subgroup One,EQ1,Equipment One,Desc,CIRCUIT_BREAKER,Manuf,Model,Serial,230.0,THREE_PHASE,2023-01-01,0.0,0.0";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        Plant plant = new Plant();
        plant.setCode("PLANT1");

        when(plantRepository.findByCode("PLANT1")).thenReturn(Optional.of(plant));
        when(equipmentGroupRepository.findFirstByCodeAndPlantAndParentGroupIsNull(anyString(), any(Plant.class))).thenReturn(
            Optional.empty()
        );
        when(equipmentGroupRepository.findFirstByCodeAndPlantAndParentGroup(anyString(), any(Plant.class), any())).thenReturn(
            Optional.empty()
        );

        // Mock saving groups to return the object with ID (simulated)
        when(equipmentGroupRepository.save(any(EquipmentGroup.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        String result = importDataService.importEquipments(inputStream);

        // Then
        assertThat(result).contains("Importação concluída com sucesso");

        // Verify Parent Group Creation
        ArgumentCaptor<EquipmentGroup> groupCaptor = ArgumentCaptor.forClass(EquipmentGroup.class);
        verify(equipmentGroupRepository, atLeast(2)).save(groupCaptor.capture());

        // We expect 2 saves: Parent and Subgroup (and possibly Subgroup update if logic
        // requires, but here we expect creation)
        // Actually, the logic saves parent, then saves subgroup.

        EquipmentGroup parentGroup = groupCaptor
            .getAllValues()
            .stream()
            .filter(g -> "GROUP1".equals(g.getCode()))
            .findFirst()
            .orElseThrow();
        assertThat(parentGroup.getName()).isEqualTo("Group One");
        assertThat(parentGroup.getParentGroup()).isNull();

        EquipmentGroup subgroup = groupCaptor.getAllValues().stream().filter(g -> "SUB1".equals(g.getCode())).findFirst().orElseThrow();
        assertThat(subgroup.getName()).isEqualTo("Subgroup One");
        assertThat(subgroup.getParentGroup()).isEqualTo(parentGroup);

        // Verify Equipment Creation
        ArgumentCaptor<Equipment> equipmentCaptor = ArgumentCaptor.forClass(Equipment.class);
        verify(equipmentRepository).save(equipmentCaptor.capture());
        Equipment equipment = equipmentCaptor.getValue();

        assertThat(equipment.getCode()).isEqualTo("EQ1");
        assertThat(equipment.getGroup()).isEqualTo(subgroup);
    }
}
