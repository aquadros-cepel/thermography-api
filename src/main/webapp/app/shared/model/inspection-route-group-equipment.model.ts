import { IInspectionRouteGroup } from 'app/shared/model/inspection-route-group.model';
import { IEquipment } from 'app/shared/model/equipment.model';

export interface IInspectionRouteGroupEquipment {
  id?: string;
  included?: boolean | null;
  orderIndex?: number | null;
  inspectionRouteGroup?: IInspectionRouteGroup | null;
  equipment?: IEquipment | null;
}

export const defaultValue: Readonly<IInspectionRouteGroupEquipment> = {
  included: false,
};
