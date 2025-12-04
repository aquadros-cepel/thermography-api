import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InspectionRecordGroupEquipment from './inspection-record-group-equipment';
import InspectionRecordGroupEquipmentDetail from './inspection-record-group-equipment-detail';
import InspectionRecordGroupEquipmentUpdate from './inspection-record-group-equipment-update';
import InspectionRecordGroupEquipmentDeleteDialog from './inspection-record-group-equipment-delete-dialog';

const InspectionRecordGroupEquipmentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InspectionRecordGroupEquipment />} />
    <Route path="new" element={<InspectionRecordGroupEquipmentUpdate />} />
    <Route path=":id">
      <Route index element={<InspectionRecordGroupEquipmentDetail />} />
      <Route path="edit" element={<InspectionRecordGroupEquipmentUpdate />} />
      <Route path="delete" element={<InspectionRecordGroupEquipmentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InspectionRecordGroupEquipmentRoutes;
