import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EquipmentTypeTranslation from './equipment-type-translation';
import EquipmentTypeTranslationDetail from './equipment-type-translation-detail';
import EquipmentTypeTranslationUpdate from './equipment-type-translation-update';
import EquipmentTypeTranslationDeleteDialog from './equipment-type-translation-delete-dialog';

const EquipmentTypeTranslationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EquipmentTypeTranslation />} />
    <Route path="new" element={<EquipmentTypeTranslationUpdate />} />
    <Route path=":id">
      <Route index element={<EquipmentTypeTranslationDetail />} />
      <Route path="edit" element={<EquipmentTypeTranslationUpdate />} />
      <Route path="delete" element={<EquipmentTypeTranslationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EquipmentTypeTranslationRoutes;
