import dayjs from 'dayjs';
import { IPlant } from 'app/shared/model/plant.model';
import { IInspectionRecord } from 'app/shared/model/inspection-record.model';
import { IEquipment } from 'app/shared/model/equipment.model';
import { IEquipmentComponent } from 'app/shared/model/equipment-component.model';
import { IUserInfo } from 'app/shared/model/user-info.model';
import { IThermogram } from 'app/shared/model/thermogram.model';
import { ThermographicInspectionRecordType } from 'app/shared/model/enumerations/thermographic-inspection-record-type.model';
import { ConditionType } from 'app/shared/model/enumerations/condition-type.model';
import { PhaseType } from 'app/shared/model/enumerations/phase-type.model';

export interface IThermographicInspectionRecord {
  id?: string;
  name?: string;
  type?: keyof typeof ThermographicInspectionRecordType;
  serviceOrder?: string | null;
  createdAt?: dayjs.Dayjs;
  analysisDescription?: string | null;
  condition?: keyof typeof ConditionType;
  deltaT?: number;
  periodicity?: number | null;
  deadlineExecution?: dayjs.Dayjs | null;
  nextMonitoring?: dayjs.Dayjs | null;
  recommendations?: string | null;
  finished?: boolean | null;
  finishedAt?: dayjs.Dayjs | null;
  current?: number | null;
  voltage?: number | null;
  windSpeed?: number | null;
  phase?: keyof typeof PhaseType | null;
  plant?: IPlant;
  route?: IInspectionRecord | null;
  equipment?: IEquipment;
  component?: IEquipmentComponent | null;
  createdBy?: IUserInfo;
  finishedBy?: IUserInfo;
  thermogram?: IThermogram;
  thermogramRef?: IThermogram | null;
  parentRecord?: IThermographicInspectionRecord | null;
}

export const defaultValue: Readonly<IThermographicInspectionRecord> = {
  finished: false,
};
