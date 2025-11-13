import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RiskRecommendationTranslation from './risk-recommendation-translation';
import RiskRecommendationTranslationDetail from './risk-recommendation-translation-detail';
import RiskRecommendationTranslationUpdate from './risk-recommendation-translation-update';
import RiskRecommendationTranslationDeleteDialog from './risk-recommendation-translation-delete-dialog';

const RiskRecommendationTranslationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RiskRecommendationTranslation />} />
    <Route path="new" element={<RiskRecommendationTranslationUpdate />} />
    <Route path=":id">
      <Route index element={<RiskRecommendationTranslationDetail />} />
      <Route path="edit" element={<RiskRecommendationTranslationUpdate />} />
      <Route path="delete" element={<RiskRecommendationTranslationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RiskRecommendationTranslationRoutes;
