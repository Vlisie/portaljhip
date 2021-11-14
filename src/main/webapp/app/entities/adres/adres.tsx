import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './adres.reducer';
import { IAdres } from 'app/shared/model/adres.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Adres = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const adresList = useAppSelector(state => state.adres.entities);
  const loading = useAppSelector(state => state.adres.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="adres-heading" data-cy="AdresHeading">
        <Translate contentKey="portaljhipApp.adres.home.title">Adres</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="portaljhipApp.adres.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="portaljhipApp.adres.home.createLabel">Create new Adres</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {adresList && adresList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="portaljhipApp.adres.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.adres.straatnaam">Straatnaam</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.adres.huisnummer">Huisnummer</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.adres.postcode">Postcode</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.adres.woonplaats">Woonplaats</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.adres.land">Land</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.adres.relatie">Relatie</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {adresList.map((adres, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${adres.id}`} color="link" size="sm">
                      {adres.id}
                    </Button>
                  </td>
                  <td>{adres.straatnaam}</td>
                  <td>{adres.huisnummer}</td>
                  <td>{adres.postcode}</td>
                  <td>{adres.woonplaats}</td>
                  <td>{adres.land}</td>
                  <td>{adres.relatie ? <Link to={`relatie/${adres.relatie.id}`}>{adres.relatie.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${adres.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${adres.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${adres.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="portaljhipApp.adres.home.notFound">No Adres found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Adres;
