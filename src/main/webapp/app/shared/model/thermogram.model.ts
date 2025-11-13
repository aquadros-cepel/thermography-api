import dayjs from 'dayjs';
import { IEquipment } from 'app/shared/model/equipment.model';
import { IUserInfo } from 'app/shared/model/user-info.model';

export interface IThermogram {
  id?: string;
  imagePath?: string;
  audioPath?: string | null;
  imageRefPath?: string;
  minTemp?: number | null;
  avgTemp?: number | null;
  maxTemp?: number | null;
  emissivity?: number | null;
  subjectDistance?: number | null;
  atmosphericTemp?: number | null;
  reflectedTemp?: number | null;
  relativeHumidity?: number | null;
  cameraLens?: string | null;
  cameraModel?: string | null;
  imageResolution?: string | null;
  selectedRoiId?: string | null;
  maxTempRoi?: number | null;
  createdAt?: dayjs.Dayjs | null;
  latitude?: number | null;
  longitude?: number | null;
  equipment?: IEquipment;
  createdBy?: IUserInfo;
}

export const defaultValue: Readonly<IThermogram> = {};
