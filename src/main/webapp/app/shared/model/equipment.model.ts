import dayjs from 'dayjs';
import { IPlant } from 'app/shared/model/plant.model';
import { IEquipmentGroup } from 'app/shared/model/equipment-group.model';
import { IInspectionRouteGroup } from 'app/shared/model/inspection-route-group.model';
import { IEquipmentComponent } from 'app/shared/model/equipment-component.model';
import { EquipmentType } from 'app/shared/model/enumerations/equipment-type.model';
import { PhaseType } from 'app/shared/model/enumerations/phase-type.model';

export interface IEquipment {
  id?: string;
  name?: string;
  title?: string | null;
  description?: string | null;
  type?: keyof typeof EquipmentType;
  manufacturer?: string | null;
  model?: string | null;
  serialNumber?: string | null;
  voltageClass?: number | null;
  phaseType?: keyof typeof PhaseType | null;
  startDate?: dayjs.Dayjs | null;
  latitude?: number | null;
  longitude?: number | null;
  plant?: IPlant;
  group?: IEquipmentGroup | null;
  inspectionRouteGroups?: IInspectionRouteGroup[] | null;
  components?: IEquipmentComponent[] | null;
}

export const defaultValue: Readonly<IEquipment> = {};
