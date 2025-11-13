import dayjs from 'dayjs';
import { IPlant } from 'app/shared/model/plant.model';
import { IUserInfo } from 'app/shared/model/user-info.model';

export interface IInspectionRoute {
  id?: string;
  name?: string;
  title?: string | null;
  description?: string | null;
  planNote?: string | null;
  createdAt?: dayjs.Dayjs;
  startDate?: dayjs.Dayjs;
  started?: boolean | null;
  startedAt?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs;
  finished?: boolean | null;
  finishedAt?: dayjs.Dayjs | null;
  plant?: IPlant;
  createdBy?: IUserInfo;
  startedBy?: IUserInfo | null;
  finishedBy?: IUserInfo | null;
}

export const defaultValue: Readonly<IInspectionRoute> = {
  started: false,
  finished: false,
};
