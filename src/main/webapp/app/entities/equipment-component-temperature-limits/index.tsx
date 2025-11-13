import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EquipmentComponentTemperatureLimits from './equipment-component-temperature-limits';
import EquipmentComponentTemperatureLimitsDetail from './equipment-component-temperature-limits-detail';
import EquipmentComponentTemperatureLimitsUpdate from './equipment-component-temperature-limits-update';
import EquipmentComponentTemperatureLimitsDeleteDialog from './equipment-component-temperature-limits-delete-dialog';

const EquipmentComponentTemperatureLimitsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EquipmentComponentTemperatureLimits />} />
    <Route path="new" element={<EquipmentComponentTemperatureLimitsUpdate />} />
    <Route path=":id">
      <Route index element={<EquipmentComponentTemperatureLimitsDetail />} />
      <Route path="edit" element={<EquipmentComponentTemperatureLimitsUpdate />} />
      <Route path="delete" element={<EquipmentComponentTemperatureLimitsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EquipmentComponentTemperatureLimitsRoutes;
