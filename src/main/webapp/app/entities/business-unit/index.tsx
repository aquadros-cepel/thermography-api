import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BusinessUnit from './business-unit';
import BusinessUnitDetail from './business-unit-detail';
import BusinessUnitUpdate from './business-unit-update';
import BusinessUnitDeleteDialog from './business-unit-delete-dialog';

const BusinessUnitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BusinessUnit />} />
    <Route path="new" element={<BusinessUnitUpdate />} />
    <Route path=":id">
      <Route index element={<BusinessUnitDetail />} />
      <Route path="edit" element={<BusinessUnitUpdate />} />
      <Route path="delete" element={<BusinessUnitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BusinessUnitRoutes;
