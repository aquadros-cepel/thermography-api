import React from 'react';
import { Route } from 'react-router'; // eslint-disable-line

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BusinessUnit from './business-unit';
import Company from './company';
import Equipment from './equipment';
import EquipmentComponent from './equipment-component';
import EquipmentComponentTemperatureLimits from './equipment-component-temperature-limits';
import EquipmentGroup from './equipment-group';
import EquipmentTypeTranslation from './equipment-type-translation';
import InspectionRecord from './inspection-record';
import InspectionRecordGroup from './inspection-record-group';
import InspectionRecordGroupEquipment from './inspection-record-group-equipment';
import InspectionRoute from './inspection-route';
import InspectionRouteGroup from './inspection-route-group';
import InspectionRouteGroupEquipment from './inspection-route-group-equipment';
import Plant from './plant';
import RiskPeriodicityDeadline from './risk-periodicity-deadline';
import RiskRecommendationTranslation from './risk-recommendation-translation';
import ROI from './roi';
import Thermogram from './thermogram';
import ThermographicRecord from './thermographic-record';
import UserInfo from './user-info';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="company/*" element={<Company />} />
        <Route path="plant/*" element={<Plant />} />
        <Route path="business-unit/*" element={<BusinessUnit />} />
        <Route path="equipment/*" element={<Equipment />} />
        <Route path="equipment-group/*" element={<EquipmentGroup />} />
        <Route path="inspection-route/*" element={<InspectionRoute />} />
        <Route path="inspection-route-group/*" element={<InspectionRouteGroup />} />
        <Route path="equipment-type-translation/*" element={<EquipmentTypeTranslation />} />
        <Route path="equipment-component/*" element={<EquipmentComponent />} />
        <Route path="equipment-component-temperature-limits/*" element={<EquipmentComponentTemperatureLimits />} />
        <Route path="risk-periodicity-deadline/*" element={<RiskPeriodicityDeadline />} />
        <Route path="risk-recommendation-translation/*" element={<RiskRecommendationTranslation />} />
        <Route path="thermogram/*" element={<Thermogram />} />
        <Route path="roi/*" element={<ROI />} />
        <Route path="thermographic-record/*" element={<ThermographicRecord />} />
        <Route path="user-info/*" element={<UserInfo />} />
        <Route path="inspection-route-group-equipment/*" element={<InspectionRouteGroupEquipment />} />
        <Route path="inspection-record/*" element={<InspectionRecord />} />
        <Route path="inspection-record-group/*" element={<InspectionRecordGroup />} />
        <Route path="inspection-record-group-equipment/*" element={<InspectionRecordGroupEquipment />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
