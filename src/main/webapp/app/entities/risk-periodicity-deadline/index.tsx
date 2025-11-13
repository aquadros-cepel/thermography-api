import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RiskPeriodicityDeadline from './risk-periodicity-deadline';
import RiskPeriodicityDeadlineDetail from './risk-periodicity-deadline-detail';
import RiskPeriodicityDeadlineUpdate from './risk-periodicity-deadline-update';
import RiskPeriodicityDeadlineDeleteDialog from './risk-periodicity-deadline-delete-dialog';

const RiskPeriodicityDeadlineRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RiskPeriodicityDeadline />} />
    <Route path="new" element={<RiskPeriodicityDeadlineUpdate />} />
    <Route path=":id">
      <Route index element={<RiskPeriodicityDeadlineDetail />} />
      <Route path="edit" element={<RiskPeriodicityDeadlineUpdate />} />
      <Route path="delete" element={<RiskPeriodicityDeadlineDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RiskPeriodicityDeadlineRoutes;
