import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EquipmentComponent from './equipment-component';
import EquipmentComponentDetail from './equipment-component-detail';
import EquipmentComponentUpdate from './equipment-component-update';
import EquipmentComponentDeleteDialog from './equipment-component-delete-dialog';

const EquipmentComponentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EquipmentComponent />} />
    <Route path="new" element={<EquipmentComponentUpdate />} />
    <Route path=":id">
      <Route index element={<EquipmentComponentDetail />} />
      <Route path="edit" element={<EquipmentComponentUpdate />} />
      <Route path="delete" element={<EquipmentComponentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EquipmentComponentRoutes;
