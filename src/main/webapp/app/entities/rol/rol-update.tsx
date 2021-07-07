import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRelatie } from 'app/shared/model/relatie.model';
import { getEntities as getRelaties } from 'app/entities/relatie/relatie.reducer';
import { getEntity, updateEntity, createEntity, reset } from './rol.reducer';
import { IRol } from 'app/shared/model/rol.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RolUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const relaties = useAppSelector(state => state.relatie.entities);
  const rolEntity = useAppSelector(state => state.rol.entity);
  const loading = useAppSelector(state => state.rol.loading);
  const updating = useAppSelector(state => state.rol.updating);
  const updateSuccess = useAppSelector(state => state.rol.updateSuccess);

  const handleClose = () => {
    props.history.push('/rol');
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
    values.startDatumRol = convertDateTimeToServer(values.startDatumRol);
    values.eindDatumRol = convertDateTimeToServer(values.eindDatumRol);

    const entity = {
      ...rolEntity,
      ...values,
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
          startDatumRol: displayDefaultDateTime(),
          eindDatumRol: displayDefaultDateTime(),
        }
      : {
          ...rolEntity,
          startDatumRol: convertDateTimeFromServer(rolEntity.startDatumRol),
          eindDatumRol: convertDateTimeFromServer(rolEntity.eindDatumRol),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="portaljhipApp.rol.home.createOrEditLabel" data-cy="RolCreateUpdateHeading">
            <Translate contentKey="portaljhipApp.rol.home.createOrEditLabel">Create or edit a Rol</Translate>
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
                  id="rol-id"
                  label={translate('portaljhipApp.rol.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('portaljhipApp.rol.rolNaam')}
                id="rol-rolNaam"
                name="rolNaam"
                data-cy="rolNaam"
                type="text"
              />
              <ValidatedField
                label={translate('portaljhipApp.rol.jeugdschaatsen')}
                id="rol-jeugdschaatsen"
                name="jeugdschaatsen"
                data-cy="jeugdschaatsen"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('portaljhipApp.rol.startDatumRol')}
                id="rol-startDatumRol"
                name="startDatumRol"
                data-cy="startDatumRol"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('portaljhipApp.rol.eindDatumRol')}
                id="rol-eindDatumRol"
                name="eindDatumRol"
                data-cy="eindDatumRol"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rol" replace color="info">
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

export default RolUpdate;
