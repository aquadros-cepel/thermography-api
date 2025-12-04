import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getInspectionRecords } from 'app/entities/inspection-record/inspection-record.reducer';
import { getEntities as getInspectionRecordGroups } from 'app/entities/inspection-record-group/inspection-record-group.reducer';
import { createEntity, getEntity, reset, updateEntity } from './inspection-record-group.reducer';

export const InspectionRecordGroupUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inspectionRecords = useAppSelector(state => state.inspectionRecord.entities);
  const inspectionRecordGroups = useAppSelector(state => state.inspectionRecordGroup.entities);
  const inspectionRecordGroupEntity = useAppSelector(state => state.inspectionRecordGroup.entity);
  const loading = useAppSelector(state => state.inspectionRecordGroup.loading);
  const updating = useAppSelector(state => state.inspectionRecordGroup.updating);
  const updateSuccess = useAppSelector(state => state.inspectionRecordGroup.updateSuccess);

  const handleClose = () => {
    navigate('/inspection-record-group');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getInspectionRecords({}));
    dispatch(getInspectionRecordGroups({}));
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
    values.finishedAt = convertDateTimeToServer(values.finishedAt);

    const entity = {
      ...inspectionRecordGroupEntity,
      ...values,
      inspectionRecord: inspectionRecords.find(it => it.id.toString() === values.inspectionRecord?.toString()),
      parentGroup: inspectionRecordGroups.find(it => it.id.toString() === values.parentGroup?.toString()),
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
          finishedAt: displayDefaultDateTime(),
        }
      : {
          ...inspectionRecordGroupEntity,
          finishedAt: convertDateTimeFromServer(inspectionRecordGroupEntity.finishedAt),
          inspectionRecord: inspectionRecordGroupEntity?.inspectionRecord?.id,
          parentGroup: inspectionRecordGroupEntity?.parentGroup?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.inspectionRecordGroup.home.createOrEditLabel" data-cy="InspectionRecordGroupCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.inspectionRecordGroup.home.createOrEditLabel">
              Create or edit a InspectionRecordGroup
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
                  id="inspection-record-group-id"
                  label={translate('thermographyApiApp.inspectionRecordGroup.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecordGroup.code')}
                id="inspection-record-group-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecordGroup.name')}
                id="inspection-record-group-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecordGroup.description')}
                id="inspection-record-group-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecordGroup.orderIndex')}
                id="inspection-record-group-orderIndex"
                name="orderIndex"
                data-cy="orderIndex"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecordGroup.finished')}
                id="inspection-record-group-finished"
                name="finished"
                data-cy="finished"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecordGroup.finishedAt')}
                id="inspection-record-group-finishedAt"
                name="finishedAt"
                data-cy="finishedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="inspection-record-group-inspectionRecord"
                name="inspectionRecord"
                data-cy="inspectionRecord"
                label={translate('thermographyApiApp.inspectionRecordGroup.inspectionRecord')}
                type="select"
              >
                <option value="" key="0" />
                {inspectionRecords
                  ? inspectionRecords.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inspection-record-group-parentGroup"
                name="parentGroup"
                data-cy="parentGroup"
                label={translate('thermographyApiApp.inspectionRecordGroup.parentGroup')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inspection-record-group" replace color="info">
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

export default InspectionRecordGroupUpdate;
