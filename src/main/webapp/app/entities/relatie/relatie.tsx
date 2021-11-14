import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './relatie.reducer';
import { IRelatie } from 'app/shared/model/relatie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Relatie = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const relatieList = useAppSelector(state => state.relatie.entities);
  const loading = useAppSelector(state => state.relatie.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="relatie-heading" data-cy="RelatieHeading">
        <Translate contentKey="portaljhipApp.relatie.home.title">Relaties</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="portaljhipApp.relatie.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="portaljhipApp.relatie.home.createLabel">Create new Relatie</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {relatieList && relatieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.rol">Rol</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.voornaam">Voornaam</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.achternaam">Achternaam</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.initialen">Initialen</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.weergavenaam">Weergavenaam</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.geslacht">Geslacht</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.geboortedatum">Geboortedatum</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.relatietype">Relatietype</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.inschrijvingsdatum">Inschrijvingsdatum</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.adres">Adres</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.email2">Email 2</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.telefoonnummer">Telefoonnummer</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.telefoonnummer2">Telefoonnummer 2</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.telefoonnummer3">Telefoonnummer 3</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.ibancode">Ibancode</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.knsbRelatienummer">Knsb Relatienummer</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.pasfoto">Pasfoto</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.privacyVerklaring">Privacy Verklaring</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {relatieList.map((relatie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${relatie.id}`} color="link" size="sm">
                      {relatie.id}
                    </Button>
                  </td>
                  <td>{relatie.rol}</td>
                  <td>{relatie.voornaam}</td>
                  <td>{relatie.achternaam}</td>
                  <td>{relatie.initialen}</td>
                  <td>{relatie.weergavenaam}</td>
                  <td>
                    <Translate contentKey={`portaljhipApp.Geslacht.${relatie.geslacht}`} />
                  </td>
                  <td>
                    {relatie.geboortedatum ? <TextFormat type="date" value={relatie.geboortedatum} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`portaljhipApp.RelatieType.${relatie.relatietype}`} />
                  </td>
                  <td>
                    {relatie.inschrijvingsdatum ? (
                      <TextFormat type="date" value={relatie.inschrijvingsdatum} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{relatie.adres}</td>
                  <td>{relatie.email}</td>
                  <td>{relatie.email2}</td>
                  <td>{relatie.telefoonnummer}</td>
                  <td>{relatie.telefoonnummer2}</td>
                  <td>{relatie.telefoonnummer3}</td>
                  <td>{relatie.ibancode}</td>
                  <td>{relatie.knsbRelatienummer}</td>
                  <td>
                    {relatie.pasfoto ? (
                      <div>
                        {relatie.pasfotoContentType ? (
                          <a onClick={openFile(relatie.pasfotoContentType, relatie.pasfoto)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {relatie.pasfotoContentType}, {byteSize(relatie.pasfoto)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {relatie.privacyVerklaring ? (
                      <div>
                        {relatie.privacyVerklaringContentType ? (
                          <a onClick={openFile(relatie.privacyVerklaringContentType, relatie.privacyVerklaring)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {relatie.privacyVerklaringContentType}, {byteSize(relatie.privacyVerklaring)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${relatie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${relatie.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${relatie.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="portaljhipApp.relatie.home.notFound">No Relaties found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Relatie;
