import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './adres.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdresDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const adresEntity = useAppSelector(state => state.adres.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adresDetailsHeading">
          <Translate contentKey="portaljhipApp.adres.detail.title">Adres</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="portaljhipApp.adres.id">Id</Translate>
            </span>
          </dt>
          <dd>{adresEntity.id}</dd>
          <dt>
            <span id="straatnaam">
              <Translate contentKey="portaljhipApp.adres.straatnaam">Straatnaam</Translate>
            </span>
          </dt>
          <dd>{adresEntity.straatnaam}</dd>
          <dt>
            <span id="huisnummer">
              <Translate contentKey="portaljhipApp.adres.huisnummer">Huisnummer</Translate>
            </span>
          </dt>
          <dd>{adresEntity.huisnummer}</dd>
          <dt>
            <span id="postcode">
              <Translate contentKey="portaljhipApp.adres.postcode">Postcode</Translate>
            </span>
          </dt>
          <dd>{adresEntity.postcode}</dd>
          <dt>
            <span id="woonplaats">
              <Translate contentKey="portaljhipApp.adres.woonplaats">Woonplaats</Translate>
            </span>
          </dt>
          <dd>{adresEntity.woonplaats}</dd>
          <dt>
            <span id="land">
              <Translate contentKey="portaljhipApp.adres.land">Land</Translate>
            </span>
          </dt>
          <dd>{adresEntity.land}</dd>
          <dt>
            <Translate contentKey="portaljhipApp.adres.relatie">Relatie</Translate>
          </dt>
          <dd>{adresEntity.relatie ? adresEntity.relatie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adres" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adres/${adresEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdresDetail;
