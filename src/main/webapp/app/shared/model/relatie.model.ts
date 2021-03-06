import dayjs from 'dayjs';
import { IAdres } from 'app/shared/model/adres.model';
import { IRol } from 'app/shared/model/rol.model';
import { Geslacht } from 'app/shared/model/enumerations/geslacht.model';
import { RelatieType } from 'app/shared/model/enumerations/relatie-type.model';

export interface IRelatie {
  id?: string;
  rol?: string | null;
  voornaam?: string | null;
  achternaam?: string | null;
  initialen?: string | null;
  weergavenaam?: string | null;
  geslacht?: Geslacht | null;
  geboortedatum?: string | null;
  relatietype?: RelatieType | null;
  inschrijvingsdatum?: string | null;
  adres?: string | null;
  email?: string | null;
  email2?: string | null;
  telefoonnummer?: number | null;
  telefoonnummer2?: number | null;
  telefoonnummer3?: number | null;
  ibancode?: string | null;
  knsbRelatienummer?: number | null;
  pasfotoContentType?: string | null;
  pasfoto?: string | null;
  privacyVerklaringContentType?: string | null;
  privacyVerklaring?: string | null;
  adres?: IAdres[] | null;
  rols?: IRol[] | null;
}

export const defaultValue: Readonly<IRelatie> = {};
