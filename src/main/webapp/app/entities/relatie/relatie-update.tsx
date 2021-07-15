import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRol } from 'app/shared/model/rol.model';
import { getEntities as getRols } from 'app/entities/rol/rol.reducer';
import { getEntity, updateEntity, createEntity, reset } from './relatie.reducer';
import { IRelatie } from 'app/shared/model/relatie.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RelatieUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const rols = useAppSelector(state => state.rol.entities);
  const relatieEntity = useAppSelector(state => state.relatie.entity);
  const loading = useAppSelector(state => state.relatie.loading);
  const updating = useAppSelector(state => state.relatie.updating);
  const updateSuccess = useAppSelector(state => state.relatie.updateSuccess);

  const handleClose = () => {
    props.history.push('/relatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getRols({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.inschrijvingsdatum = convertDateTimeToServer(values.inschrijvingsdatum);

    const entity = {
      ...relatieEntity,
      ...values,
      rols: mapIdList(values.rols),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          inschrijvingsdatum: displayDefaultDateTime(),
        }
      : {
          ...relatieEntity,
          geslacht: 'MAN',
          relatietype: 'LID',
          inschrijvingsdatum: convertDateTimeFromServer(relatieEntity.inschrijvingsdatum),
          rols: relatieEntity?.rols?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="portaljhipApp.relatie.home.createOrEditLabel" data-cy="RelatieCreateUpdateHeading">
            <Translate contentKey="portaljhipApp.relatie.home.createOrEditLabel">Create or edit a Relatie</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="relatie-id"
                  label={translate('portaljhipApp.relatie.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('portaljhipApp.relatie.voornaam')}
                id="relatie-voornaam"
                name="voornaam"
                data-cy="voornaam"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.achternaam')}
                id="relatie-achternaam"
                name="achternaam"
                data-cy="achternaam"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.initialen')}
                id="relatie-initialen"
                name="initialen"
                data-cy="initialen"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.weergavenaam')}
                id="relatie-weergavenaam"
                name="weergavenaam"
                data-cy="weergavenaam"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.geslacht')}
                id="relatie-geslacht"
                name="geslacht"
                data-cy="geslacht"
                type="select"
              >
                <option value="MAN">{translate('portaljhipApp.Geslacht.MAN')}</option>
                <option value="VROUW">{translate('portaljhipApp.Geslacht.VROUW')}</option>
                <option value="X">{translate('portaljhipApp.Geslacht.X')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('portaljhipApp.relatie.geboortedatum')}
                id="relatie-geboortedatum"
                name="geboortedatum"
                data-cy="geboortedatum"
                type="date"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.relatietype')}
                id="relatie-relatietype"
                name="relatietype"
                data-cy="relatietype"
                type="select"
              >
                <option value="LID">{translate('portaljhipApp.RelatieType.LID')}</option>
                <option value="JEUGDSCHAATSLID">{translate('portaljhipApp.RelatieType.JEUGDSCHAATSLID')}</option>
                <option value="DONATEUR">{translate('portaljhipApp.RelatieType.DONATEUR')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('portaljhipApp.relatie.inschrijvingsdatum')}
                id="relatie-inschrijvingsdatum"
                name="inschrijvingsdatum"
                data-cy="inschrijvingsdatum"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.straatnaam')}
                id="relatie-straatnaam"
                name="straatnaam"
                data-cy="straatnaam"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.huisnummer')}
                id="relatie-huisnummer"
                name="huisnummer"
                data-cy="huisnummer"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.postcode')}
                id="relatie-postcode"
                name="postcode"
                data-cy="postcode"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.woonplaats')}
                id="relatie-woonplaats"
                name="woonplaats"
                data-cy="woonplaats"
                type="text"
              />
              <ValidatedField label={translate('portaljhipApp.relatie.land')} id="relatie-land" name="land" data-cy="land" type="text" />
              <ValidatedField
                label={translate('portaljhipApp.relatie.email')}
                id="relatie-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.email2')}
                id="relatie-email2"
                name="email2"
                data-cy="email2"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.telefoonnummer')}
                id="relatie-telefoonnummer"
                name="telefoonnummer"
                data-cy="telefoonnummer"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.telefoonnummer2')}
                id="relatie-telefoonnummer2"
                name="telefoonnummer2"
                data-cy="telefoonnummer2"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.telefoonnummer3')}
                id="relatie-telefoonnummer3"
                name="telefoonnummer3"
                data-cy="telefoonnummer3"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.ibancode')}
                id="relatie-ibancode"
                name="ibancode"
                data-cy="ibancode"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.knsbRelatienummer')}
                id="relatie-knsbRelatienummer"
                name="knsbRelatienummer"
                data-cy="knsbRelatienummer"
                type="text"
              />
              <ValidatedBlobField
                label={translate('portaljhipApp.relatie.pasfoto')}
                id="relatie-pasfoto"
                name="pasfoto"
                data-cy="pasfoto"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedBlobField
                label={translate('portaljhipApp.relatie.privacyVerklaring')}
                id="relatie-privacyVerklaring"
                name="privacyVerklaring"
                data-cy="privacyVerklaring"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('portaljhipApp.relatie.rol')}
                id="relatie-rol"
                data-cy="rol"
                type="select"
                multiple
                name="rols"
              >
                <option value="" key="0" />
                {rols
                  ? rols.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/relatie" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RelatieUpdate;
