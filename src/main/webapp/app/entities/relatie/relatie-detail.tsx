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
            <span id="voornaam">
              <Translate contentKey="portaljhipApp.relatie.voornaam">Voornaam</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.voornaam}</dd>
          <dt>
            <span id="achternaam">
              <Translate contentKey="portaljhipApp.relatie.achternaam">Achternaam</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.achternaam}</dd>
          <dt>
            <span id="initialen">
              <Translate contentKey="portaljhipApp.relatie.initialen">Initialen</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.initialen}</dd>
          <dt>
            <span id="weergavenaam">
              <Translate contentKey="portaljhipApp.relatie.weergavenaam">Weergavenaam</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.weergavenaam}</dd>
          <dt>
            <span id="geslacht">
              <Translate contentKey="portaljhipApp.relatie.geslacht">Geslacht</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.geslacht}</dd>
          <dt>
            <span id="geboortedatum">
              <Translate contentKey="portaljhipApp.relatie.geboortedatum">Geboortedatum</Translate>
            </span>
          </dt>
          <dd>
            {relatieEntity.geboortedatum ? (
              <TextFormat value={relatieEntity.geboortedatum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="relatietype">
              <Translate contentKey="portaljhipApp.relatie.relatietype">Relatietype</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.relatietype}</dd>
          <dt>
            <span id="inschrijvingsdatum">
              <Translate contentKey="portaljhipApp.relatie.inschrijvingsdatum">Inschrijvingsdatum</Translate>
            </span>
          </dt>
          <dd>
            {relatieEntity.inschrijvingsdatum ? (
              <TextFormat value={relatieEntity.inschrijvingsdatum} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="straatnaam">
              <Translate contentKey="portaljhipApp.relatie.straatnaam">Straatnaam</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.straatnaam}</dd>
          <dt>
            <span id="huisnummer">
              <Translate contentKey="portaljhipApp.relatie.huisnummer">Huisnummer</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.huisnummer}</dd>
          <dt>
            <span id="postcode">
              <Translate contentKey="portaljhipApp.relatie.postcode">Postcode</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.postcode}</dd>
          <dt>
            <span id="woonplaats">
              <Translate contentKey="portaljhipApp.relatie.woonplaats">Woonplaats</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.woonplaats}</dd>
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
            <span id="telefoonnummer">
              <Translate contentKey="portaljhipApp.relatie.telefoonnummer">Telefoonnummer</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.telefoonnummer}</dd>
          <dt>
            <span id="telefoonnummer2">
              <Translate contentKey="portaljhipApp.relatie.telefoonnummer2">Telefoonnummer 2</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.telefoonnummer2}</dd>
          <dt>
            <span id="telefoonnummer3">
              <Translate contentKey="portaljhipApp.relatie.telefoonnummer3">Telefoonnummer 3</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.telefoonnummer3}</dd>
          <dt>
            <span id="ibancode">
              <Translate contentKey="portaljhipApp.relatie.ibancode">Ibancode</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.ibancode}</dd>
          <dt>
            <span id="knsbRelatienummer">
              <Translate contentKey="portaljhipApp.relatie.knsbRelatienummer">Knsb Relatienummer</Translate>
            </span>
          </dt>
          <dd>{relatieEntity.knsbRelatienummer}</dd>
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
