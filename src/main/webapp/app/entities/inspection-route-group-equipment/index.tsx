import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InspectionRouteGroupEquipment from './inspection-route-group-equipment';
import InspectionRouteGroupEquipmentDetail from './inspection-route-group-equipment-detail';
import InspectionRouteGroupEquipmentUpdate from './inspection-route-group-equipment-update';
import InspectionRouteGroupEquipmentDeleteDialog from './inspection-route-group-equipment-delete-dialog';

const InspectionRouteGroupEquipmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InspectionRouteGroupEquipment />} />
    <Route path="new" element={<InspectionRouteGroupEquipmentUpdate />} />
    <Route path=":id">
      <Route index element={<InspectionRouteGroupEquipmentDetail />} />
      <Route path="edit" element={<InspectionRouteGroupEquipmentUpdate />} />
      <Route path="delete" element={<InspectionRouteGroupEquipmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InspectionRouteGroupEquipmentRoutes;
