import dayjs from 'dayjs';
import { IUserInfo } from 'app/shared/model/user-info.model';

export interface IInspectionRouteRecord {
  id?: string;
  code?: string | null;
  name?: string;
  description?: string | null;
  maintenanceDocument?: string | null;
  createdAt?: dayjs.Dayjs;
  expectedStartDate?: dayjs.Dayjs;
  expectedEndDate?: dayjs.Dayjs;
  started?: boolean | null;
  startedAt?: dayjs.Dayjs | null;
  finished?: boolean | null;
  finishedAt?: dayjs.Dayjs | null;
  startedBy?: IUserInfo | null;
  finishedBy?: IUserInfo | null;
}

export const defaultValue: Readonly<IInspectionRouteRecord> = {
  started: false,
  finished: false,
};
