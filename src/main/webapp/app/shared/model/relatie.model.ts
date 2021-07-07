import dayjs from 'dayjs';
import { IRol } from 'app/shared/model/rol.model';
import { Geslacht } from 'app/shared/model/enumerations/geslacht.model';
import { RelatieType } from 'app/shared/model/enumerations/relatie-type.model';

export interface IRelatie {
  id?: string;
  voorNaam?: string | null;
  achterNaam?: string | null;
  initialen?: string | null;
  weergaveNaam?: string | null;
  geslacht?: Geslacht | null;
  geboorteDatum?: string | null;
  relatieType?: RelatieType | null;
  inschrijvingsDatum?: string | null;
  straatNaam?: string | null;
  huisNummer?: number | null;
  postCode?: string | null;
  woonPlaats?: string | null;
  land?: string | null;
  email?: string | null;
  email2?: string | null;
  telefoonNummer?: number | null;
  telefoonNummer2?: number | null;
  telefoonNummer3?: number | null;
  ibanCode?: string | null;
  knsbRelatieNummer?: number | null;
  pasfotoContentType?: string | null;
  pasfoto?: string | null;
  privacyVerklaringContentType?: string | null;
  privacyVerklaring?: string | null;
  rols?: IRol[] | null;
}

export const defaultValue: Readonly<IRelatie> = {};
