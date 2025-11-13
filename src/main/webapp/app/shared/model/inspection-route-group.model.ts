import { IInspectionRoute } from 'app/shared/model/inspection-route.model';
import { IEquipment } from 'app/shared/model/equipment.model';

export interface IInspectionRouteGroup {
  id?: string;
  name?: string;
  title?: string | null;
  description?: string | null;
  inspectionRoute?: IInspectionRoute | null;
  subGroup?: IInspectionRouteGroup | null;
  equipments?: IEquipment[] | null;
}

export const defaultValue: Readonly<IInspectionRouteGroup> = {};
