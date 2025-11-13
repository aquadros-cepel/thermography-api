import { ICompany } from 'app/shared/model/company.model';

export interface IBusinessUnit {
  id?: string;
  name?: string;
  title?: string | null;
  description?: string | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<IBusinessUnit> = {};
