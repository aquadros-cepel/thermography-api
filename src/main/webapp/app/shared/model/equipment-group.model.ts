import { IPlant } from 'app/shared/model/plant.model';

export interface IEquipmentGroup {
  id?: string;
  name?: string;
  title?: string | null;
  description?: string | null;
  plant?: IPlant | null;
  subGroup?: IEquipmentGroup | null;
}

export const defaultValue: Readonly<IEquipmentGroup> = {};
