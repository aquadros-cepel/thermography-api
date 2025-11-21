export interface ICompany {
  id?: string;
  code?: string | null;
  name?: string;
  description?: string | null;
  address?: string | null;
  primaryPhoneNumber?: string | null;
  secondaryPhoneNumber?: string | null;
  taxIdNumber?: string | null;
}

export const defaultValue: Readonly<ICompany> = {};
