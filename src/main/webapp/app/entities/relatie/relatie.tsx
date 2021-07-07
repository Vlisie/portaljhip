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
                  <Translate contentKey="portaljhipApp.relatie.voorNaam">Voor Naam</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.achterNaam">Achter Naam</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.initialen">Initialen</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.weergaveNaam">Weergave Naam</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.geslacht">Geslacht</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.geboorteDatum">Geboorte Datum</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.relatieType">Relatie Type</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.inschrijvingsDatum">Inschrijvings Datum</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.straatNaam">Straat Naam</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.huisNummer">Huis Nummer</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.postCode">Post Code</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.woonPlaats">Woon Plaats</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.land">Land</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.email2">Email 2</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.telefoonNummer">Telefoon Nummer</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.telefoonNummer2">Telefoon Nummer 2</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.telefoonNummer3">Telefoon Nummer 3</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.ibanCode">Iban Code</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.knsbRelatieNummer">Knsb Relatie Nummer</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.pasfoto">Pasfoto</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.privacyVerklaring">Privacy Verklaring</Translate>
                </th>
                <th>
                  <Translate contentKey="portaljhipApp.relatie.rol">Rol</Translate>
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
                  <td>{relatie.voorNaam}</td>
                  <td>{relatie.achterNaam}</td>
                  <td>{relatie.initialen}</td>
                  <td>{relatie.weergaveNaam}</td>
                  <td>
                    <Translate contentKey={`portaljhipApp.Geslacht.${relatie.geslacht}`} />
                  </td>
                  <td>
                    {relatie.geboorteDatum ? <TextFormat type="date" value={relatie.geboorteDatum} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`portaljhipApp.RelatieType.${relatie.relatieType}`} />
                  </td>
                  <td>
                    {relatie.inschrijvingsDatum ? (
                      <TextFormat type="date" value={relatie.inschrijvingsDatum} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{relatie.straatNaam}</td>
                  <td>{relatie.huisNummer}</td>
                  <td>{relatie.postCode}</td>
                  <td>{relatie.woonPlaats}</td>
                  <td>{relatie.land}</td>
                  <td>{relatie.email}</td>
                  <td>{relatie.email2}</td>
                  <td>{relatie.telefoonNummer}</td>
                  <td>{relatie.telefoonNummer2}</td>
                  <td>{relatie.telefoonNummer3}</td>
                  <td>{relatie.ibanCode}</td>
                  <td>{relatie.knsbRelatieNummer}</td>
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
                  <td>
                    {relatie.rols
                      ? relatie.rols.map((val, j) => (
                          <span key={j}>
                            <Link to={`rol/${val.id}`}>{val.id}</Link>
                            {j === relatie.rols.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
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
