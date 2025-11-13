export interface IEquipmentComponentTemperatureLimits {
  id?: string;
  name?: string | null;
  normal?: string | null;
  lowRisk?: string | null;
  mediumRisk?: string | null;
  highRisk?: string | null;
  imminentHighRisk?: string | null;
}

export const defaultValue: Readonly<IEquipmentComponentTemperatureLimits> = {};
