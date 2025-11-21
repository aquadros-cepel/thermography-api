import { IInspectionRoute } from 'app/shared/model/inspection-route.model';

export interface IInspectionRouteGroup {
  id?: string;
  code?: string | null;
  name?: string;
  description?: string | null;
  included?: boolean | null;
  orderIndex?: number | null;
  inspectionRoute?: IInspectionRoute | null;
  parentGroup?: IInspectionRouteGroup | null;
}

export const defaultValue: Readonly<IInspectionRouteGroup> = {
  included: false,
};
