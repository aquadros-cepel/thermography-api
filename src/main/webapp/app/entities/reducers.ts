import company from 'app/entities/company/company.reducer';
import plant from 'app/entities/plant/plant.reducer';
import businessUnit from 'app/entities/business-unit/business-unit.reducer';
import equipment from 'app/entities/equipment/equipment.reducer';
import equipmentGroup from 'app/entities/equipment-group/equipment-group.reducer';
import inspectionRoute from 'app/entities/inspection-route/inspection-route.reducer';
import inspectionRouteGroup from 'app/entities/inspection-route-group/inspection-route-group.reducer';
import equipmentTypeTranslation from 'app/entities/equipment-type-translation/equipment-type-translation.reducer';
import equipmentComponent from 'app/entities/equipment-component/equipment-component.reducer';
import equipmentComponentTemperatureLimits from 'app/entities/equipment-component-temperature-limits/equipment-component-temperature-limits.reducer';
import riskPeriodicityDeadline from 'app/entities/risk-periodicity-deadline/risk-periodicity-deadline.reducer';
import riskRecommendationTranslation from 'app/entities/risk-recommendation-translation/risk-recommendation-translation.reducer';
import thermogram from 'app/entities/thermogram/thermogram.reducer';
import rOI from 'app/entities/roi/roi.reducer';
import thermographicInspectionRecord from 'app/entities/thermographic-inspection-record/thermographic-inspection-record.reducer';
import userInfo from 'app/entities/user-info/user-info.reducer';
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
  thermographicInspectionRecord,
  userInfo,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
