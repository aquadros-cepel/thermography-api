import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getInspectionRouteGroups } from 'app/entities/inspection-route-group/inspection-route-group.reducer';
import { getEntities as getEquipment } from 'app/entities/equipment/equipment.reducer';
import { createEntity, getEntity, reset, updateEntity } from './inspection-route-group-equipment.reducer';

export const InspectionRouteGroupEquipmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inspectionRouteGroups = useAppSelector(state => state.inspectionRouteGroup.entities);
  const equipment = useAppSelector(state => state.equipment.entities);
  const inspectionRouteGroupEquipmentEntity = useAppSelector(state => state.inspectionRouteGroupEquipment.entity);
  const loading = useAppSelector(state => state.inspectionRouteGroupEquipment.loading);
  const updating = useAppSelector(state => state.inspectionRouteGroupEquipment.updating);
  const updateSuccess = useAppSelector(state => state.inspectionRouteGroupEquipment.updateSuccess);

  const handleClose = () => {
    navigate('/inspection-route-group-equipment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getInspectionRouteGroups({}));
    dispatch(getEquipment({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.orderIndex !== undefined && typeof values.orderIndex !== 'number') {
      values.orderIndex = Number(values.orderIndex);
    }

    const entity = {
      ...inspectionRouteGroupEquipmentEntity,
      ...values,
      inspectionRouteGroup: inspectionRouteGroups.find(it => it.id.toString() === values.inspectionRouteGroup?.toString()),
      equipment: equipment.find(it => it.id.toString() === values.equipment?.toString()),
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
          ...inspectionRouteGroupEquipmentEntity,
          inspectionRouteGroup: inspectionRouteGroupEquipmentEntity?.inspectionRouteGroup?.id,
          equipment: inspectionRouteGroupEquipmentEntity?.equipment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="thermographyApiApp.inspectionRouteGroupEquipment.home.createOrEditLabel"
            data-cy="InspectionRouteGroupEquipmentCreateUpdateHeading"
          >
            <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.home.createOrEditLabel">
              Create or edit a InspectionRouteGroupEquipment
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
                  id="inspection-route-group-equipment-id"
                  label={translate('thermographyApiApp.inspectionRouteGroupEquipment.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteGroupEquipment.included')}
                id="inspection-route-group-equipment-included"
                name="included"
                data-cy="included"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteGroupEquipment.orderIndex')}
                id="inspection-route-group-equipment-orderIndex"
                name="orderIndex"
                data-cy="orderIndex"
                type="text"
              />
              <ValidatedField
                id="inspection-route-group-equipment-inspectionRouteGroup"
                name="inspectionRouteGroup"
                data-cy="inspectionRouteGroup"
                label={translate('thermographyApiApp.inspectionRouteGroupEquipment.inspectionRouteGroup')}
                type="select"
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
                id="inspection-route-group-equipment-equipment"
                name="equipment"
                data-cy="equipment"
                label={translate('thermographyApiApp.inspectionRouteGroupEquipment.equipment')}
                type="select"
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/inspection-route-group-equipment"
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

export default InspectionRouteGroupEquipmentUpdate;
