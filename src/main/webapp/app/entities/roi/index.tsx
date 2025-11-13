import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ROI from './roi';
import ROIDetail from './roi-detail';
import ROIUpdate from './roi-update';
import ROIDeleteDialog from './roi-delete-dialog';

const ROIRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ROI />} />
    <Route path="new" element={<ROIUpdate />} />
    <Route path=":id">
      <Route index element={<ROIDetail />} />
      <Route path="edit" element={<ROIUpdate />} />
      <Route path="delete" element={<ROIDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ROIRoutes;
