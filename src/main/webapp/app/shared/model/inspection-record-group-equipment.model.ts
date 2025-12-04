import { IInspectionRecordGroup } from 'app/shared/model/inspection-record-group.model';
import { IEquipment } from 'app/shared/model/equipment.model';
import { EquipmentInspectionStatus } from 'app/shared/model/enumerations/equipment-inspection-status.model';

export interface IInspectionRecordGroupEquipment {
  id?: string;
  orderIndex?: number | null;
  status?: keyof typeof EquipmentInspectionStatus | null;
  inspectionRecordGroup?: IInspectionRecordGroup | null;
  equipment?: IEquipment | null;
}

export const defaultValue: Readonly<IInspectionRecordGroupEquipment> = {};
