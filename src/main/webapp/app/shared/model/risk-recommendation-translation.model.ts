import { IRiskPeriodicityDeadline } from 'app/shared/model/risk-periodicity-deadline.model';

export interface IRiskRecommendationTranslation {
  id?: string;
  language?: string;
  name?: string;
  riskPeriodicityDeadline?: IRiskPeriodicityDeadline | null;
}

export const defaultValue: Readonly<IRiskRecommendationTranslation> = {};
