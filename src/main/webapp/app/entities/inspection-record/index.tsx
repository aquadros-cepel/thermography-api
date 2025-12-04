import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InspectionRecord from './inspection-record';
import InspectionRecordDetail from './inspection-record-detail';
import InspectionRecordUpdate from './inspection-record-update';
import InspectionRecordDeleteDialog from './inspection-record-delete-dialog';

const InspectionRecordRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InspectionRecord />} />
    <Route path="new" element={<InspectionRecordUpdate />} />
    <Route path=":id">
      <Route index element={<InspectionRecordDetail />} />
      <Route path="edit" element={<InspectionRecordUpdate />} />
      <Route path="delete" element={<InspectionRecordDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InspectionRecordRoutes;
