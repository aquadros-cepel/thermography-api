select
    external_name as name,
    title,
    description,
    latitude,
    longitude,
    to_char(to_timestamp(start_date), 'YYYY-MM-DD') as start_date
from
    eq_plant

where 
	info_class = 'br.cepel.gamma.equipment.model.equipment.info.plant.PowerStation' and
    (
		external_name like '%N-S-MA%' OR
    external_name like '%N-S-TO%' OR
    external_name like '%N-S-PA%' OR
    external_name like '%N-S-STLI'
	)

order by title

