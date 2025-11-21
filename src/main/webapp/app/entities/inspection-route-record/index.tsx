import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InspectionRouteRecord from './inspection-route-record';
import InspectionRouteRecordDetail from './inspection-route-record-detail';
import InspectionRouteRecordUpdate from './inspection-route-record-update';
import InspectionRouteRecordDeleteDialog from './inspection-route-record-delete-dialog';

const InspectionRouteRecordRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InspectionRouteRecord />} />
    <Route path="new" element={<InspectionRouteRecordUpdate />} />
    <Route path=":id">
      <Route index element={<InspectionRouteRecordDetail />} />
      <Route path="edit" element={<InspectionRouteRecordUpdate />} />
      <Route path="delete" element={<InspectionRouteRecordDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InspectionRouteRecordRoutes;
