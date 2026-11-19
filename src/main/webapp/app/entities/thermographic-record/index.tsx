import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ThermographicRecord from './thermographic-record';
import ThermographicRecordDetail from './thermographic-record-detail';
import ThermographicRecordUpdate from './thermographic-record-update';
import ThermographicRecordDeleteDialog from './thermographic-record-delete-dialog';

const ThermographicRecordRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ThermographicRecord />} />
    <Route path="new" element={<ThermographicRecordUpdate />} />
    <Route path=":id">
      <Route index element={<ThermographicRecordDetail />} />
      <Route path="edit" element={<ThermographicRecordUpdate />} />
      <Route path="delete" element={<ThermographicRecordDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ThermographicRecordRoutes;
