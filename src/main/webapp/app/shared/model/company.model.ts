export interface ICompany {
  id?: string;
  name?: string;
  title?: string | null;
  description?: string | null;
  address?: string | null;
  primaryPhoneNumber?: string | null;
  secondaryPhoneNumber?: string | null;
  taxIdNumber?: string | null;
}

export const defaultValue: Readonly<ICompany> = {};
