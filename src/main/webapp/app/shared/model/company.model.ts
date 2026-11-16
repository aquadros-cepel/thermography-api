export interface ICompany {
  id?: string;
  code?: string | null;
  name?: string;
  description?: string | null;
  logoContentType?: string | null;
  logo?: string | null;
  address?: string | null;
  zipCode?: string | null;
  street?: string | null;
  number?: string | null;
  complement?: string | null;
  neighborhood?: string | null;
  city?: string | null;
  state?: string | null;
  country?: string | null;
  primaryPhoneNumber?: string | null;
  secondaryPhoneNumber?: string | null;
  taxIdNumber?: string | null;
}

export const defaultValue: Readonly<ICompany> = {};
