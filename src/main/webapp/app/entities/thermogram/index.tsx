import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Thermogram from './thermogram';
import ThermogramDetail from './thermogram-detail';
import ThermogramUpdate from './thermogram-update';
import ThermogramDeleteDialog from './thermogram-delete-dialog';

const ThermogramRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Thermogram />} />
    <Route path="new" element={<ThermogramUpdate />} />
    <Route path=":id">
      <Route index element={<ThermogramDetail />} />
      <Route path="edit" element={<ThermogramUpdate />} />
      <Route path="delete" element={<ThermogramDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ThermogramRoutes;
