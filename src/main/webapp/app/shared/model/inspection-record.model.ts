import dayjs from 'dayjs';
import { IPlant } from 'app/shared/model/plant.model';
import { IInspectionRoute } from 'app/shared/model/inspection-route.model';
import { IUserInfo } from 'app/shared/model/user-info.model';

export interface IInspectionRecord {
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
  plant?: IPlant;
  inspectionRoute?: IInspectionRoute | null;
  createdBy?: IUserInfo;
  startedBy?: IUserInfo | null;
  finishedBy?: IUserInfo | null;
}

export const defaultValue: Readonly<IInspectionRecord> = {
  started: false,
  finished: false,
};
