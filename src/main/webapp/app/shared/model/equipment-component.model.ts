import { IEquipmentComponentTemperatureLimits } from 'app/shared/model/equipment-component-temperature-limits.model';
import { IEquipment } from 'app/shared/model/equipment.model';

export interface IEquipmentComponent {
  id?: string;
  code?: string | null;
  name?: string;
  description?: string | null;
  componentTemperatureLimits?: IEquipmentComponentTemperatureLimits | null;
  equipments?: IEquipment[] | null;
}

export const defaultValue: Readonly<IEquipmentComponent> = {};
