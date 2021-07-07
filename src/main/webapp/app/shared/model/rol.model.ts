import dayjs from 'dayjs';
import { IRelatie } from 'app/shared/model/relatie.model';

export interface IRol {
  id?: string;
  rolNaam?: string | null;
  jeugdschaatsen?: boolean | null;
  startDatumRol?: string | null;
  eindDatumRol?: string | null;
  relaties?: IRelatie[] | null;
}

export const defaultValue: Readonly<IRol> = {
  jeugdschaatsen: false,
};
