import { IUser } from 'app/shared/model/user.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IUserInfo {
  id?: string;
  position?: string | null;
  phoneNumber?: string | null;
  user?: IUser | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<IUserInfo> = {};
