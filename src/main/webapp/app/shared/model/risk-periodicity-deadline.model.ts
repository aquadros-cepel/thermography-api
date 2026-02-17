import { DatetimeUnit } from 'app/shared/model/enumerations/datetime-unit.model';

export interface IRiskPeriodicityDeadline {
  id?: string;
  name?: string | null;
  description?: string | null;
  deadline?: number | null;
  deadlineUnit?: keyof typeof DatetimeUnit | null;
  periodicity?: number | null;
  periodicityUnit?: keyof typeof DatetimeUnit | null;
  recommendations?: string | null;
}

export const defaultValue: Readonly<IRiskPeriodicityDeadline> = {};
