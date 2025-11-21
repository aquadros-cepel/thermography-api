import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getEquipmentComponentTemperatureLimits } from 'app/entities/equipment-component-temperature-limits/equipment-component-temperature-limits.reducer';
import { getEntities as getEquipment } from 'app/entities/equipment/equipment.reducer';
import { createEntity, getEntity, reset, updateEntity } from './equipment-component.reducer';

export const EquipmentComponentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const equipmentComponentTemperatureLimits = useAppSelector(state => state.equipmentComponentTemperatureLimits.entities);
  const equipment = useAppSelector(state => state.equipment.entities);
  const equipmentComponentEntity = useAppSelector(state => state.equipmentComponent.entity);
  const loading = useAppSelector(state => state.equipmentComponent.loading);
  const updating = useAppSelector(state => state.equipmentComponent.updating);
  const updateSuccess = useAppSelector(state => state.equipmentComponent.updateSuccess);

  const handleClose = () => {
    navigate('/equipment-component');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEquipmentComponentTemperatureLimits({}));
    dispatch(getEquipment({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...equipmentComponentEntity,
      ...values,
      componentTemperatureLimits: equipmentComponentTemperatureLimits.find(
        it => it.id.toString() === values.componentTemperatureLimits?.toString(),
      ),
      equipments: mapIdList(values.equipments),
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
          ...equipmentComponentEntity,
          componentTemperatureLimits: equipmentComponentEntity?.componentTemperatureLimits?.id,
          equipments: equipmentComponentEntity?.equipments?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.equipmentComponent.home.createOrEditLabel" data-cy="EquipmentComponentCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.equipmentComponent.home.createOrEditLabel">
              Create or edit a EquipmentComponent
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
                  id="equipment-component-id"
                  label={translate('thermographyApiApp.equipmentComponent.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.equipmentComponent.code')}
                id="equipment-component-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipmentComponent.name')}
                id="equipment-component-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipmentComponent.description')}
                id="equipment-component-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                id="equipment-component-componentTemperatureLimits"
                name="componentTemperatureLimits"
                data-cy="componentTemperatureLimits"
                label={translate('thermographyApiApp.equipmentComponent.componentTemperatureLimits')}
                type="select"
              >
                <option value="" key="0" />
                {equipmentComponentTemperatureLimits
                  ? equipmentComponentTemperatureLimits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('thermographyApiApp.equipmentComponent.equipments')}
                id="equipment-component-equipments"
                data-cy="equipments"
                type="select"
                multiple
                name="equipments"
              >
                <option value="" key="0" />
                {equipment
                  ? equipment.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/equipment-component" replace color="info">
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

export default EquipmentComponentUpdate;
