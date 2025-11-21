import dayjs from 'dayjs';
import { IPlant } from 'app/shared/model/plant.model';
import { IUserInfo } from 'app/shared/model/user-info.model';
import { Periodicity } from 'app/shared/model/enumerations/periodicity.model';

export interface IInspectionRoute {
  id?: string;
  code?: string | null;
  name?: string;
  description?: string | null;
  maintenancePlan?: string | null;
  periodicity?: keyof typeof Periodicity | null;
  duration?: number | null;
  expectedStartDate?: dayjs.Dayjs | null;
  createdAt?: dayjs.Dayjs;
  plant?: IPlant;
  createdBy?: IUserInfo;
}

export const defaultValue: Readonly<IInspectionRoute> = {};
