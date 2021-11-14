import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './rol.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RolDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const rolEntity = useAppSelector(state => state.rol.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rolDetailsHeading">
          <Translate contentKey="portaljhipApp.rol.detail.title">Rol</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="portaljhipApp.rol.id">Id</Translate>
            </span>
          </dt>
          <dd>{rolEntity.id}</dd>
          <dt>
            <span id="relatie">
              <Translate contentKey="portaljhipApp.rol.relatie">Relatie</Translate>
            </span>
          </dt>
          <dd>{rolEntity.relatie}</dd>
          <dt>
            <span id="rolnaam">
              <Translate contentKey="portaljhipApp.rol.rolnaam">Rolnaam</Translate>
            </span>
          </dt>
          <dd>{rolEntity.rolnaam}</dd>
          <dt>
            <span id="jeugdschaatsen">
              <Translate contentKey="portaljhipApp.rol.jeugdschaatsen">Jeugdschaatsen</Translate>
            </span>
          </dt>
          <dd>{rolEntity.jeugdschaatsen ? 'true' : 'false'}</dd>
          <dt>
            <span id="startdatumRol">
              <Translate contentKey="portaljhipApp.rol.startdatumRol">Startdatum Rol</Translate>
            </span>
          </dt>
          <dd>{rolEntity.startdatumRol ? <TextFormat value={rolEntity.startdatumRol} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="einddatumRol">
              <Translate contentKey="portaljhipApp.rol.einddatumRol">Einddatum Rol</Translate>
            </span>
          </dt>
          <dd>{rolEntity.einddatumRol ? <TextFormat value={rolEntity.einddatumRol} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="portaljhipApp.rol.relatie">Relatie</Translate>
          </dt>
          <dd>{rolEntity.relatie ? rolEntity.relatie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rol" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rol/${rolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RolDetail;
