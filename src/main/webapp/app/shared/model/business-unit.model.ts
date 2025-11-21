import { ICompany } from 'app/shared/model/company.model';

export interface IBusinessUnit {
  id?: string;
  code?: string | null;
  name?: string;
  description?: string | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<IBusinessUnit> = {};
