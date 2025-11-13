import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InspectionRoute from './inspection-route';
import InspectionRouteDetail from './inspection-route-detail';
import InspectionRouteUpdate from './inspection-route-update';
import InspectionRouteDeleteDialog from './inspection-route-delete-dialog';

const InspectionRouteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InspectionRoute />} />
    <Route path="new" element={<InspectionRouteUpdate />} />
    <Route path=":id">
      <Route index element={<InspectionRouteDetail />} />
      <Route path="edit" element={<InspectionRouteUpdate />} />
      <Route path="delete" element={<InspectionRouteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InspectionRouteRoutes;
