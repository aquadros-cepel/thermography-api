import businessUnit from 'app/entities/business-unit/business-unit.reducer';
import company from 'app/entities/company/company.reducer';
import equipmentComponentTemperatureLimits from 'app/entities/equipment-component-temperature-limits/equipment-component-temperature-limits.reducer';
import equipmentComponent from 'app/entities/equipment-component/equipment-component.reducer';
import equipmentGroup from 'app/entities/equipment-group/equipment-group.reducer';
import equipmentTypeTranslation from 'app/entities/equipment-type-translation/equipment-type-translation.reducer';
import equipment from 'app/entities/equipment/equipment.reducer';
import inspectionRecordGroupEquipment from 'app/entities/inspection-record-group-equipment/inspection-record-group-equipment.reducer';
import inspectionRecordGroup from 'app/entities/inspection-record-group/inspection-record-group.reducer';
import inspectionRecord from 'app/entities/inspection-record/inspection-record.reducer';
import inspectionRouteGroupEquipment from 'app/entities/inspection-route-group-equipment/inspection-route-group-equipment.reducer';
import inspectionRouteGroup from 'app/entities/inspection-route-group/inspection-route-group.reducer';
import inspectionRoute from 'app/entities/inspection-route/inspection-route.reducer';
import plant from 'app/entities/plant/plant.reducer';
import riskPeriodicityDeadline from 'app/entities/risk-periodicity-deadline/risk-periodicity-deadline.reducer';
import riskRecommendationTranslation from 'app/entities/risk-recommendation-translation/risk-recommendation-translation.reducer';
import rOI from 'app/entities/roi/roi.reducer';
import thermogram from 'app/entities/thermogram/thermogram.reducer';
import ThermographicRecord from 'app/entities/thermographic-record/thermographic-record.reducer';
import userInfo from 'app/entities/user-info/user-info.reducer';
import thermographicRecord from 'app/entities/thermographic-record/thermographic-record.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  company,
  plant,
  businessUnit,
  equipment,
  equipmentGroup,
  inspectionRoute,
  inspectionRouteGroup,
  equipmentTypeTranslation,
  equipmentComponent,
  equipmentComponentTemperatureLimits,
  riskPeriodicityDeadline,
  riskRecommendationTranslation,
  thermogram,
  rOI,
  ThermographicRecord,
  userInfo,
  inspectionRouteGroupEquipment,
  inspectionRecord,
  inspectionRecordGroup,
  inspectionRecordGroupEquipment,
  thermographicRecord,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
