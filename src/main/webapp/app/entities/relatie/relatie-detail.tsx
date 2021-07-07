import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './relatie.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RelatieDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const relatieEntity = useAppSelector(state => state.relatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="relatieDetailsHeading">
          <Translate contentKey="portaljhipApp.relatie.detail.title">Relatie</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="portaljhipApp.relatie.id">Id</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.id}</dd>
          <dt>
            <span id="voorNaam">
              <Translate contentKey="portaljhipApp.relatie.voorNaam">Voor Naam</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.voorNaam}</dd>
          <dt>
            <span id="achterNaam">
              <Translate contentKey="portaljhipApp.relatie.achterNaam">Achter Naam</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.achterNaam}</dd>
          <dt>
            <span id="initialen">
              <Translate contentKey="portaljhipApp.relatie.initialen">Initialen</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.initialen}</dd>
          <dt>
            <span id="weergaveNaam">
              <Translate contentKey="portaljhipApp.relatie.weergaveNaam">Weergave Naam</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.weergaveNaam}</dd>
          <dt>
            <span id="geslacht">
              <Translate contentKey="portaljhipApp.relatie.geslacht">Geslacht</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.geslacht}</dd>
          <dt>
            <span id="geboorteDatum">
              <Translate contentKey="portaljhipApp.relatie.geboorteDatum">Geboorte Datum</Translate>
            </span>
          </dt>
          <dd>
            {relatieEntity.geboorteDatum ? (
              <TextFormat value={relatieEntity.geboorteDatum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="relatieType">
              <Translate contentKey="portaljhipApp.relatie.relatieType">Relatie Type</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.relatieType}</dd>
          <dt>
            <span id="inschrijvingsDatum">
              <Translate contentKey="portaljhipApp.relatie.inschrijvingsDatum">Inschrijvings Datum</Translate>
            </span>
          </dt>
          <dd>
            {relatieEntity.inschrijvingsDatum ? (
              <TextFormat value={relatieEntity.inschrijvingsDatum} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="straatNaam">
              <Translate contentKey="portaljhipApp.relatie.straatNaam">Straat Naam</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.straatNaam}</dd>
          <dt>
            <span id="huisNummer">
              <Translate contentKey="portaljhipApp.relatie.huisNummer">Huis Nummer</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.huisNummer}</dd>
          <dt>
            <span id="postCode">
              <Translate contentKey="portaljhipApp.relatie.postCode">Post Code</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.postCode}</dd>
          <dt>
            <span id="woonPlaats">
              <Translate contentKey="portaljhipApp.relatie.woonPlaats">Woon Plaats</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.woonPlaats}</dd>
          <dt>
            <span id="land">
              <Translate contentKey="portaljhipApp.relatie.land">Land</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.land}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="portaljhipApp.relatie.email">Email</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.email}</dd>
          <dt>
            <span id="email2">
              <Translate contentKey="portaljhipApp.relatie.email2">Email 2</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.email2}</dd>
          <dt>
            <span id="telefoonNummer">
              <Translate contentKey="portaljhipApp.relatie.telefoonNummer">Telefoon Nummer</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.telefoonNummer}</dd>
          <dt>
            <span id="telefoonNummer2">
              <Translate contentKey="portaljhipApp.relatie.telefoonNummer2">Telefoon Nummer 2</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.telefoonNummer2}</dd>
          <dt>
            <span id="telefoonNummer3">
              <Translate contentKey="portaljhipApp.relatie.telefoonNummer3">Telefoon Nummer 3</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.telefoonNummer3}</dd>
          <dt>
            <span id="ibanCode">
              <Translate contentKey="portaljhipApp.relatie.ibanCode">Iban Code</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.ibanCode}</dd>
          <dt>
            <span id="knsbRelatieNummer">
              <Translate contentKey="portaljhipApp.relatie.knsbRelatieNummer">Knsb Relatie Nummer</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.knsbRelatieNummer}</dd>
          <dt>
            <span id="pasfoto">
              <Translate contentKey="portaljhipApp.relatie.pasfoto">Pasfoto</Translate>
            </span>
          </dt>
          <dd>
            {relatieEntity.pasfoto ? (
              <div>
                {relatieEntity.pasfotoContentType ? (
                  <a onClick={openFile(relatieEntity.pasfotoContentType, relatieEntity.pasfoto)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {relatieEntity.pasfotoContentType}, {byteSize(relatieEntity.pasfoto)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="privacyVerklaring">
              <Translate contentKey="portaljhipApp.relatie.privacyVerklaring">Privacy Verklaring</Translate>
            </span>
          </dt>
          <dd>
            {relatieEntity.privacyVerklaring ? (
              <div>
                {relatieEntity.privacyVerklaringContentType ? (
                  <a onClick={openFile(relatieEntity.privacyVerklaringContentType, relatieEntity.privacyVerklaring)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {relatieEntity.privacyVerklaringContentType}, {byteSize(relatieEntity.privacyVerklaring)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="portaljhipApp.relatie.rol">Rol</Translate>
          </dt>
          <dd>
            {relatieEntity.rols
              ? relatieEntity.rols.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {relatieEntity.rols && i === relatieEntity.rols.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/relatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/relatie/${relatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RelatieDetail;
