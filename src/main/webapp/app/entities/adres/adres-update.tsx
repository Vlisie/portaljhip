import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRelatie } from 'app/shared/model/relatie.model';
import { getEntities as getRelaties } from 'app/entities/relatie/relatie.reducer';
import { getEntity, updateEntity, createEntity, reset } from './adres.reducer';
import { IAdres } from 'app/shared/model/adres.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdresUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const relaties = useAppSelector(state => state.relatie.entities);
  const adresEntity = useAppSelector(state => state.adres.entity);
  const loading = useAppSelector(state => state.adres.loading);
  const updating = useAppSelector(state => state.adres.updating);
  const updateSuccess = useAppSelector(state => state.adres.updateSuccess);
  const handleClose = () => {
    props.history.push('/adres');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getRelaties({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...adresEntity,
      ...values,
      relatie: relaties.find(it => it.id.toString() === values.relatie.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...adresEntity,
          relatie: adresEntity?.relatie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="portaljhipApp.adres.home.createOrEditLabel" data-cy="AdresCreateUpdateHeading">
            <Translate contentKey="portaljhipApp.adres.home.createOrEditLabel">Create or edit a Adres</Translate>
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
                  id="adres-id"
                  label={translate('portaljhipApp.adres.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('portaljhipApp.adres.straatnaam')}
                id="adres-straatnaam"
                name="straatnaam"
                data-cy="straatnaam"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.adres.huisnummer')}
                id="adres-huisnummer"
                name="huisnummer"
                data-cy="huisnummer"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.adres.postcode')}
                id="adres-postcode"
                name="postcode"
                data-cy="postcode"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.adres.woonplaats')}
                id="adres-woonplaats"
                name="woonplaats"
                data-cy="woonplaats"
                type="text"
              />
              <ValidatedField label={translate('portaljhipApp.adres.land')} id="adres-land" name="land" data-cy="land" type="text" />
              <ValidatedField
                id="adres-relatie"
                name="relatie"
                data-cy="relatie"
                label={translate('portaljhipApp.adres.relatie')}
                type="select"
              >
                <option value="" key="0" />
                {relaties
                  ? relaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/adres" replace color="info">
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

export default AdresUpdate;
