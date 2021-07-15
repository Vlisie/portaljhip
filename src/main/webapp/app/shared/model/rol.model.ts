import dayjs from 'dayjs';
import { IRelatie } from 'app/shared/model/relatie.model';

export interface IRol {
  id?: string;
  rolnaam?: string | null;
  jeugdschaatsen?: boolean | null;
  startdatumRol?: string | null;
  einddatumRol?: string | null;
  relaties?: IRelatie[] | null;
}

export const defaultValue: Readonly<IRol> = {
  jeugdschaatsen: false,
};
