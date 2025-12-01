select
	p.external_name as plant_code,
	f.external_name as group_code,
	f.title as group_name,
	array_to_string((string_to_array(e.external_name, '-'))[1:5], '-') as subgroup_code,
	CONCAT('Módulo ', split_part(e.external_name, '-', 5)) as subgroup_name,
	e.external_name as equipment_code,
	e.title as equipment_name, 
	e.description equipment_description,
	
	-- split_part(e.info_class, '.', array_length(string_to_array(e.info_class, '.'), 1)) AS type,
    CASE split_part(e.info_class, '.', array_length(string_to_array(e.info_class, '.'), 1))
        WHEN 'CircuitBreaker'    THEN 'CIRCUIT_BREAKER'
        WHEN 'PowerTransformer'  THEN 'POWER_TRANSFORMER'
        WHEN 'Bushing'           THEN 'BUSHING'
        WHEN 'Relay'             THEN 'RELAY'
        WHEN 'Generator'         THEN 'GENERATOR'
        WHEN 'ProtectionSystem'  THEN 'PROTECTION_SYSTEM'
        WHEN 'ShuntReactor'      THEN 'SHUNT_REACTOR'
        WHEN 'DisconnectSwitch'  THEN 'DISCONNECT_SWITCH'
        ELSE 'UNKNOWN'
    END AS equipment_type,

	e.manufacturer as equipment_manufacturer, 
	e.model equipment_model, 
	e.serial_number equipment_serial_number, 
	
	-- e.voltage_class,
	CASE
        WHEN e.voltage_class = 'NA' THEN NULL
        ELSE ROUND((substring(e.voltage_class FROM 2)::numeric / 1000), 3)
    END AS equipment_voltage_class,
	
	CASE 
		WHEN EI.phase_position = 'T' THEN 'THREE_PHASE'
		ELSE NULL
	END AS equipment_phase_type, 
	
	to_char(to_timestamp(e.start_date), 'YYYY-MM-DD') as equipment_start_date,
	null as equipment_latitude,
	null as equipment_longitude
	
from 
	eq_company c, 
	eq_plant p, 
	eq_function f, 
	eq_equipment e, 
	eq_equipment_info ei
	
	
where
	c.codigo = p.cod_company and
	p.codigo = f.cod_plant and
	f.codigo = e.cod_function and
	e.codigo = ei.cod_equipment and
	(
		p.external_name like '%N-S-MA%' OR
		p.external_name like '%N-S-TO%' OR
		p.external_name like '%N-S-PA%' OR
		p.external_name like '%N-S-STLI' OR
		p.external_name like '%N-S-MTJU%' OR
		p.external_name like '%N-S-MTBP%' OR
		p.external_name like '%N-S-ROAQ%' OR
		p.external_name like '%N-S-RRBV%'
	)

-- select
-- 	split_part(e.info_class, '.', array_length(string_to_array(e.info_class, '.'), 1)) AS type,
--     CASE split_part(e.info_class, '.', array_length(string_to_array(e.info_class, '.'), 1))
--         WHEN 'TransmissionTower' THEN 'BLOCKING_COIL'           -- não há torre, mapeie conforme necessidade
--         WHEN 'CircuitBreaker'    THEN 'CIRCUIT_BREAKER'
--         WHEN 'PowerTransformer'  THEN 'POWER_TRANSFORMER'
--         WHEN 'Bushing'           THEN 'BUSHING'
--         WHEN 'Relay'             THEN 'RELAY'
--         WHEN 'Generator'         THEN 'GENERATOR'
--         WHEN 'ProtectionSystem'  THEN 'PROTECTION_SYSTEM'
--         WHEN 'ShuntReactor'      THEN 'SHUNT_REACTOR'
--         WHEN 'DisconnectSwitch'  THEN 'DISCONNECT_SWITCH'
--         ELSE 'UNKNOWN'
--     END AS equipment_type
-- from eq_equipment e


-- SELECT DISTINCT ON (equipment_info) equipment_info, uuid
-- FROM eq_equipment_info
-- ORDER BY equipment_info, uuid;


-- SELECT DISTINCT
--     voltage_class,
--     CASE
--         WHEN voltage_class = 'NA' THEN NULL
--         ELSE ROUND((substring(voltage_class FROM 2)::numeric / 1000), 3)
--     END AS voltage_kv
-- FROM eq_equipment
-- ORDER BY voltage_kv;

-- select * from eq_plant