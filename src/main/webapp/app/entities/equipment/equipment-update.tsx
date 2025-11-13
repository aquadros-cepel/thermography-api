import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getPlants } from 'app/entities/plant/plant.reducer';
import { getEntities as getEquipmentGroups } from 'app/entities/equipment-group/equipment-group.reducer';
import { getEntities as getInspectionRouteGroups } from 'app/entities/inspection-route-group/inspection-route-group.reducer';
import { getEntities as getEquipmentComponents } from 'app/entities/equipment-component/equipment-component.reducer';
import { EquipmentType } from 'app/shared/model/enumerations/equipment-type.model';
import { PhaseType } from 'app/shared/model/enumerations/phase-type.model';
import { createEntity, getEntity, reset, updateEntity } from './equipment.reducer';

export const EquipmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plants = useAppSelector(state => state.plant.entities);
  const equipmentGroups = useAppSelector(state => state.equipmentGroup.entities);
  const inspectionRouteGroups = useAppSelector(state => state.inspectionRouteGroup.entities);
  const equipmentComponents = useAppSelector(state => state.equipmentComponent.entities);
  const equipmentEntity = useAppSelector(state => state.equipment.entity);
  const loading = useAppSelector(state => state.equipment.loading);
  const updating = useAppSelector(state => state.equipment.updating);
  const updateSuccess = useAppSelector(state => state.equipment.updateSuccess);
  const equipmentTypeValues = Object.keys(EquipmentType);
  const phaseTypeValues = Object.keys(PhaseType);

  const handleClose = () => {
    navigate('/equipment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlants({}));
    dispatch(getEquipmentGroups({}));
    dispatch(getInspectionRouteGroups({}));
    dispatch(getEquipmentComponents({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.voltageClass !== undefined && typeof values.voltageClass !== 'number') {
      values.voltageClass = Number(values.voltageClass);
    }
    if (values.latitude !== undefined && typeof values.latitude !== 'number') {
      values.latitude = Number(values.latitude);
    }
    if (values.longitude !== undefined && typeof values.longitude !== 'number') {
      values.longitude = Number(values.longitude);
    }

    const entity = {
      ...equipmentEntity,
      ...values,
      plant: plants.find(it => it.id.toString() === values.plant?.toString()),
      group: equipmentGroups.find(it => it.id.toString() === values.group?.toString()),
      inspectionRouteGroups: mapIdList(values.inspectionRouteGroups),
      components: mapIdList(values.components),
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
          type: 'AUTOTRANSFORMER',
          phaseType: 'PHASE_A',
          ...equipmentEntity,
          plant: equipmentEntity?.plant?.id,
          group: equipmentEntity?.group?.id,
          inspectionRouteGroups: equipmentEntity?.inspectionRouteGroups?.map(e => e.id.toString()),
          components: equipmentEntity?.components?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.equipment.home.createOrEditLabel" data-cy="EquipmentCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.equipment.home.createOrEditLabel">Create or edit a Equipment</Translate>
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
                  id="equipment-id"
                  label={translate('thermographyApiApp.equipment.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.equipment.name')}
                id="equipment-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipment.title')}
                id="equipment-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipment.description')}
                id="equipment-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipment.type')}
                id="equipment-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {equipmentTypeValues.map(equipmentType => (
                  <option value={equipmentType} key={equipmentType}>
                    {translate(`thermographyApiApp.EquipmentType.${equipmentType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('thermographyApiApp.equipment.manufacturer')}
                id="equipment-manufacturer"
                name="manufacturer"
                data-cy="manufacturer"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipment.model')}
                id="equipment-model"
                name="model"
                data-cy="model"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipment.serialNumber')}
                id="equipment-serialNumber"
                name="serialNumber"
                data-cy="serialNumber"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipment.voltageClass')}
                id="equipment-voltageClass"
                name="voltageClass"
                data-cy="voltageClass"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipment.phaseType')}
                id="equipment-phaseType"
                name="phaseType"
                data-cy="phaseType"
                type="select"
              >
                {phaseTypeValues.map(phaseType => (
                  <option value={phaseType} key={phaseType}>
                    {translate(`thermographyApiApp.PhaseType.${phaseType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('thermographyApiApp.equipment.startDate')}
                id="equipment-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipment.latitude')}
                id="equipment-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipment.longitude')}
                id="equipment-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField
                id="equipment-plant"
                name="plant"
                data-cy="plant"
                label={translate('thermographyApiApp.equipment.plant')}
                type="select"
                required
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="equipment-group"
                name="group"
                data-cy="group"
                label={translate('thermographyApiApp.equipment.group')}
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
              <ValidatedField
                label={translate('thermographyApiApp.equipment.inspectionRouteGroups')}
                id="equipment-inspectionRouteGroups"
                data-cy="inspectionRouteGroups"
                type="select"
                multiple
                name="inspectionRouteGroups"
              >
                <option value="" key="0" />
                {inspectionRouteGroups
                  ? inspectionRouteGroups.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('thermographyApiApp.equipment.components')}
                id="equipment-components"
                data-cy="components"
                type="select"
                multiple
                name="components"
              >
                <option value="" key="0" />
                {equipmentComponents
                  ? equipmentComponents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/equipment" replace color="info">
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

export default EquipmentUpdate;
