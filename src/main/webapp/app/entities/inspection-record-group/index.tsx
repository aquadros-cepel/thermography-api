import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InspectionRecordGroup from './inspection-record-group';
import InspectionRecordGroupDetail from './inspection-record-group-detail';
import InspectionRecordGroupUpdate from './inspection-record-group-update';
import InspectionRecordGroupDeleteDialog from './inspection-record-group-delete-dialog';

const InspectionRecordGroupRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InspectionRecordGroup />} />
    <Route path="new" element={<InspectionRecordGroupUpdate />} />
    <Route path=":id">
      <Route index element={<InspectionRecordGroupDetail />} />
      <Route path="edit" element={<InspectionRecordGroupUpdate />} />
      <Route path="delete" element={<InspectionRecordGroupDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InspectionRecordGroupRoutes;
