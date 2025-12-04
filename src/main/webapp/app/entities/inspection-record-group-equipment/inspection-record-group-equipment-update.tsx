import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getInspectionRecordGroups } from 'app/entities/inspection-record-group/inspection-record-group.reducer';
import { getEntities as getEquipment } from 'app/entities/equipment/equipment.reducer';
import { EquipmentInspectionStatus } from 'app/shared/model/enumerations/equipment-inspection-status.model';
import { createEntity, getEntity, reset, updateEntity } from './inspection-record-group-equipment.reducer';

export const InspectionRecordGroupEquipmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inspectionRecordGroups = useAppSelector(state => state.inspectionRecordGroup.entities);
  const equipment = useAppSelector(state => state.equipment.entities);
  const inspectionRecordGroupEquipmentEntity = useAppSelector(state => state.inspectionRecordGroupEquipment.entity);
  const loading = useAppSelector(state => state.inspectionRecordGroupEquipment.loading);
  const updating = useAppSelector(state => state.inspectionRecordGroupEquipment.updating);
  const updateSuccess = useAppSelector(state => state.inspectionRecordGroupEquipment.updateSuccess);
  const equipmentInspectionStatusValues = Object.keys(EquipmentInspectionStatus);

  const handleClose = () => {
    navigate('/inspection-record-group-equipment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getInspectionRecordGroups({}));
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
      ...inspectionRecordGroupEquipmentEntity,
      ...values,
      inspectionRecordGroup: inspectionRecordGroups.find(it => it.id.toString() === values.inspectionRecordGroup?.toString()),
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
          status: 'INSPECTED',
          ...inspectionRecordGroupEquipmentEntity,
          inspectionRecordGroup: inspectionRecordGroupEquipmentEntity?.inspectionRecordGroup?.id,
          equipment: inspectionRecordGroupEquipmentEntity?.equipment?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="thermographyApiApp.inspectionRecordGroupEquipment.home.createOrEditLabel"
            data-cy="InspectionRecordGroupEquipmentCreateUpdateHeading"
          >
            <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.home.createOrEditLabel">
              Create or edit a InspectionRecordGroupEquipment
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
                  id="inspection-record-group-equipment-id"
                  label={translate('thermographyApiApp.inspectionRecordGroupEquipment.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecordGroupEquipment.orderIndex')}
                id="inspection-record-group-equipment-orderIndex"
                name="orderIndex"
                data-cy="orderIndex"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecordGroupEquipment.status')}
                id="inspection-record-group-equipment-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {equipmentInspectionStatusValues.map(equipmentInspectionStatus => (
                  <option value={equipmentInspectionStatus} key={equipmentInspectionStatus}>
                    {translate(`thermographyApiApp.EquipmentInspectionStatus.${equipmentInspectionStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="inspection-record-group-equipment-inspectionRecordGroup"
                name="inspectionRecordGroup"
                data-cy="inspectionRecordGroup"
                label={translate('thermographyApiApp.inspectionRecordGroupEquipment.inspectionRecordGroup')}
                type="select"
              >
                <option value="" key="0" />
                {inspectionRecordGroups
                  ? inspectionRecordGroups.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inspection-record-group-equipment-equipment"
                name="equipment"
                data-cy="equipment"
                label={translate('thermographyApiApp.inspectionRecordGroupEquipment.equipment')}
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
                to="/inspection-record-group-equipment"
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

export default InspectionRecordGroupEquipmentUpdate;
