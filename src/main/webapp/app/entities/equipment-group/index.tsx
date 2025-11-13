import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EquipmentGroup from './equipment-group';
import EquipmentGroupDetail from './equipment-group-detail';
import EquipmentGroupUpdate from './equipment-group-update';
import EquipmentGroupDeleteDialog from './equipment-group-delete-dialog';

const EquipmentGroupRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EquipmentGroup />} />
    <Route path="new" element={<EquipmentGroupUpdate />} />
    <Route path=":id">
      <Route index element={<EquipmentGroupDetail />} />
      <Route path="edit" element={<EquipmentGroupUpdate />} />
      <Route path="delete" element={<EquipmentGroupDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EquipmentGroupRoutes;
