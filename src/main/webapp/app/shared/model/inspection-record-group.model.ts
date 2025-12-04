import dayjs from 'dayjs';
import { IInspectionRecord } from 'app/shared/model/inspection-record.model';

export interface IInspectionRecordGroup {
  id?: string;
  code?: string | null;
  name?: string;
  description?: string | null;
  orderIndex?: number | null;
  finished?: boolean | null;
  finishedAt?: dayjs.Dayjs | null;
  inspectionRecord?: IInspectionRecord | null;
  parentGroup?: IInspectionRecordGroup | null;
}

export const defaultValue: Readonly<IInspectionRecordGroup> = {
  finished: false,
};
