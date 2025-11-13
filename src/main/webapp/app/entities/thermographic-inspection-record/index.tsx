import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ThermographicInspectionRecord from './thermographic-inspection-record';
import ThermographicInspectionRecordDetail from './thermographic-inspection-record-detail';
import ThermographicInspectionRecordUpdate from './thermographic-inspection-record-update';
import ThermographicInspectionRecordDeleteDialog from './thermographic-inspection-record-delete-dialog';

const ThermographicInspectionRecordRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ThermographicInspectionRecord />} />
    <Route path="new" element={<ThermographicInspectionRecordUpdate />} />
    <Route path=":id">
      <Route index element={<ThermographicInspectionRecordDetail />} />
      <Route path="edit" element={<ThermographicInspectionRecordUpdate />} />
      <Route path="delete" element={<ThermographicInspectionRecordDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ThermographicInspectionRecordRoutes;
