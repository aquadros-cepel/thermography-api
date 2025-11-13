import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InspectionRouteGroup from './inspection-route-group';
import InspectionRouteGroupDetail from './inspection-route-group-detail';
import InspectionRouteGroupUpdate from './inspection-route-group-update';
import InspectionRouteGroupDeleteDialog from './inspection-route-group-delete-dialog';

const InspectionRouteGroupRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InspectionRouteGroup />} />
    <Route path="new" element={<InspectionRouteGroupUpdate />} />
    <Route path=":id">
      <Route index element={<InspectionRouteGroupDetail />} />
      <Route path="edit" element={<InspectionRouteGroupUpdate />} />
      <Route path="delete" element={<InspectionRouteGroupDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InspectionRouteGroupRoutes;
