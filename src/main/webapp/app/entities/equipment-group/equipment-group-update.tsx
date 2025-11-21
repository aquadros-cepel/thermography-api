import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getPlants } from 'app/entities/plant/plant.reducer';
import { getEntities as getEquipmentGroups } from 'app/entities/equipment-group/equipment-group.reducer';
import { createEntity, getEntity, reset, updateEntity } from './equipment-group.reducer';

export const EquipmentGroupUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plants = useAppSelector(state => state.plant.entities);
  const equipmentGroups = useAppSelector(state => state.equipmentGroup.entities);
  const equipmentGroupEntity = useAppSelector(state => state.equipmentGroup.entity);
  const loading = useAppSelector(state => state.equipmentGroup.loading);
  const updating = useAppSelector(state => state.equipmentGroup.updating);
  const updateSuccess = useAppSelector(state => state.equipmentGroup.updateSuccess);

  const handleClose = () => {
    navigate('/equipment-group');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlants({}));
    dispatch(getEquipmentGroups({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...equipmentGroupEntity,
      ...values,
      plant: plants.find(it => it.id.toString() === values.plant?.toString()),
      parentGroup: equipmentGroups.find(it => it.id.toString() === values.parentGroup?.toString()),
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
          ...equipmentGroupEntity,
          plant: equipmentGroupEntity?.plant?.id,
          parentGroup: equipmentGroupEntity?.parentGroup?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.equipmentGroup.home.createOrEditLabel" data-cy="EquipmentGroupCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.equipmentGroup.home.createOrEditLabel">Create or edit a EquipmentGroup</Translate>
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
                  id="equipment-group-id"
                  label={translate('thermographyApiApp.equipmentGroup.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.equipmentGroup.code')}
                id="equipment-group-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipmentGroup.name')}
                id="equipment-group-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipmentGroup.description')}
                id="equipment-group-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                id="equipment-group-plant"
                name="plant"
                data-cy="plant"
                label={translate('thermographyApiApp.equipmentGroup.plant')}
                type="select"
              >
                <option value="" key="0" />
                {plants
                  ? plants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="equipment-group-parentGroup"
                name="parentGroup"
                data-cy="parentGroup"
                label={translate('thermographyApiApp.equipmentGroup.parentGroup')}
                type="select"
              >
                <option value="" key="0" />
                {equipmentGroups
                  ? equipmentGroups.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/equipment-group" replace color="info">
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

export default EquipmentGroupUpdate;
