import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './rol.reducer';
import { IRol } from 'app/shared/model/rol.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Rol = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const rolList = useAppSelector(state => state.rol.entities);
  const loading = useAppSelector(state => state.rol.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="rol-heading" data-cy="RolHeading">
        <Translate contentKey="portaljhipApp.rol.home.title">Rols</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="portaljhipApp.rol.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="portaljhipApp.rol.home.createLabel">Create new Rol</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {rolList && rolList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="portaljhipApp.rol.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.rol.rolNaam">Rol Naam</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.rol.jeugdschaatsen">Jeugdschaatsen</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.rol.startDatumRol">Start Datum Rol</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.rol.eindDatumRol">Eind Datum Rol</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {rolList.map((rol, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${rol.id}`} color="link" size="sm">
                      {rol.id}
                    </Button>
                  </td>
                  <td>{rol.rolNaam}</td>
                  <td>{rol.jeugdschaatsen ? 'true' : 'false'}</td>
                  <td>{rol.startDatumRol ? <TextFormat type="date" value={rol.startDatumRol} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{rol.eindDatumRol ? <TextFormat type="date" value={rol.eindDatumRol} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${rol.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${rol.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${rol.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="portaljhipApp.rol.home.notFound">No Rols found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Rol;
