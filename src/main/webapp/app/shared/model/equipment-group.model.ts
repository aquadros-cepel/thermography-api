import { IPlant } from 'app/shared/model/plant.model';

export interface IEquipmentGroup {
  id?: string;
  code?: string | null;
  name?: string;
  description?: string | null;
  plant?: IPlant | null;
  parentGroup?: IEquipmentGroup | null;
}

export const defaultValue: Readonly<IEquipmentGroup> = {};
