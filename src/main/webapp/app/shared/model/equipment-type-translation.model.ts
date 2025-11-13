import { EquipmentType } from 'app/shared/model/enumerations/equipment-type.model';

export interface IEquipmentTypeTranslation {
  id?: string;
  code?: keyof typeof EquipmentType;
  language?: string;
  name?: string;
}

export const defaultValue: Readonly<IEquipmentTypeTranslation> = {};
