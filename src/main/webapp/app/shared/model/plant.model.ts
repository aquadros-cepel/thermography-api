import dayjs from 'dayjs';
import { ICompany } from 'app/shared/model/company.model';
import { IBusinessUnit } from 'app/shared/model/business-unit.model';

export interface IPlant {
  id?: string;
  name?: string;
  title?: string | null;
  description?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  startDate?: dayjs.Dayjs | null;
  company?: ICompany | null;
  businessUnit?: IBusinessUnit | null;
}

export const defaultValue: Readonly<IPlant> = {};
