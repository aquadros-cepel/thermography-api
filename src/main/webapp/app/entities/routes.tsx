import React from 'react';
import { Route } from 'react-router'; // eslint-disable-line

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Company from './company';
import Plant from './plant';
import BusinessUnit from './business-unit';
import Equipment from './equipment';
import EquipmentGroup from './equipment-group';
import InspectionRoute from './inspection-route';
import InspectionRouteGroup from './inspection-route-group';
import EquipmentTypeTranslation from './equipment-type-translation';
import EquipmentComponent from './equipment-component';
import EquipmentComponentTemperatureLimits from './equipment-component-temperature-limits';
import RiskPeriodicityDeadline from './risk-periodicity-deadline';
import RiskRecommendationTranslation from './risk-recommendation-translation';
import Thermogram from './thermogram';
import ROI from './roi';
import ThermographicInspectionRecord from './thermographic-inspection-record';
import UserInfo from './user-info';
import InspectionRouteGroupEquipment from './inspection-route-group-equipment';
import InspectionRouteRecord from './inspection-route-record';
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
        <Route path="thermographic-inspection-record/*" element={<ThermographicInspectionRecord />} />
        <Route path="user-info/*" element={<UserInfo />} />
        <Route path="inspection-route-group-equipment/*" element={<InspectionRouteGroupEquipment />} />
        <Route path="inspection-route-record/*" element={<InspectionRouteRecord />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
