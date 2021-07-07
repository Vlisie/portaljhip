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
            <span id="rolNaam">
              <Translate contentKey="portaljhipApp.rol.rolNaam">Rol Naam</Translate>
            </span>
          </dt>
          <dd>{rolEntity.rolNaam}</dd>
          <dt>
            <span id="jeugdschaatsen">
              <Translate contentKey="portaljhipApp.rol.jeugdschaatsen">Jeugdschaatsen</Translate>
            </span>
          </dt>
          <dd>{rolEntity.jeugdschaatsen ? 'true' : 'false'}</dd>
          <dt>
            <span id="startDatumRol">
              <Translate contentKey="portaljhipApp.rol.startDatumRol">Start Datum Rol</Translate>
            </span>
          </dt>
          <dd>{rolEntity.startDatumRol ? <TextFormat value={rolEntity.startDatumRol} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="eindDatumRol">
              <Translate contentKey="portaljhipApp.rol.eindDatumRol">Eind Datum Rol</Translate>
            </span>
          </dt>
          <dd>{rolEntity.eindDatumRol ? <TextFormat value={rolEntity.eindDatumRol} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
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
