import { IRelatie } from 'app/shared/model/relatie.model';

export interface IAdres {
  id?: string;
  straatnaam?: string | null;
  huisnummer?: number | null;
  postcode?: string | null;
  woonplaats?: string | null;
  land?: string | null;
  relatie?: IRelatie | null;
}

export const defaultValue: Readonly<IAdres> = {};
