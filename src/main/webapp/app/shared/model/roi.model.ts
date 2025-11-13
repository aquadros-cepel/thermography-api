import { IThermogram } from 'app/shared/model/thermogram.model';

export interface IROI {
  id?: string;
  type?: string;
  label?: string;
  maxTemp?: number;
  thermogram?: IThermogram;
}

export const defaultValue: Readonly<IROI> = {};
