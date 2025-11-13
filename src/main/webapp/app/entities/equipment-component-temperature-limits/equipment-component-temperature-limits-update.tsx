import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './equipment-component-temperature-limits.reducer';

export const EquipmentComponentTemperatureLimitsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const equipmentComponentTemperatureLimitsEntity = useAppSelector(state => state.equipmentComponentTemperatureLimits.entity);
  const loading = useAppSelector(state => state.equipmentComponentTemperatureLimits.loading);
  const updating = useAppSelector(state => state.equipmentComponentTemperatureLimits.updating);
  const updateSuccess = useAppSelector(state => state.equipmentComponentTemperatureLimits.updateSuccess);

  const handleClose = () => {
    navigate('/equipment-component-temperature-limits');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...equipmentComponentTemperatureLimitsEntity,
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
      ? {}
      : {
          ...equipmentComponentTemperatureLimitsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="thermographyApiApp.equipmentComponentTemperatureLimits.home.createOrEditLabel"
            data-cy="EquipmentComponentTemperatureLimitsCreateUpdateHeading"
          >
            <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.home.createOrEditLabel">
              Create or edit a EquipmentComponentTemperatureLimits
            </Translate>
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
                  id="equipment-component-temperature-limits-id"
                  label={translate('thermographyApiApp.equipmentComponentTemperatureLimits.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.equipmentComponentTemperatureLimits.name')}
                id="equipment-component-temperature-limits-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipmentComponentTemperatureLimits.normal')}
                id="equipment-component-temperature-limits-normal"
                name="normal"
                data-cy="normal"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipmentComponentTemperatureLimits.lowRisk')}
                id="equipment-component-temperature-limits-lowRisk"
                name="lowRisk"
                data-cy="lowRisk"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipmentComponentTemperatureLimits.mediumRisk')}
                id="equipment-component-temperature-limits-mediumRisk"
                name="mediumRisk"
                data-cy="mediumRisk"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipmentComponentTemperatureLimits.highRisk')}
                id="equipment-component-temperature-limits-highRisk"
                name="highRisk"
                data-cy="highRisk"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipmentComponentTemperatureLimits.imminentHighRisk')}
                id="equipment-component-temperature-limits-imminentHighRisk"
                name="imminentHighRisk"
                data-cy="imminentHighRisk"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/equipment-component-temperature-limits"
                replace
                color="info"
              >
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

export default EquipmentComponentTemperatureLimitsUpdate;
