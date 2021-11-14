import dayjs from 'dayjs';
import { IRelatie } from 'app/shared/model/relatie.model';

export interface IRol {
  id?: string;
  relatie?: string | null;
  rolnaam?: string | null;
  jeugdschaatsen?: boolean | null;
  startdatumRol?: string | null;
  einddatumRol?: string | null;
  relatie?: IRelatie | null;
}

export const defaultValue: Readonly<IRol> = {
  jeugdschaatsen: false,
};
