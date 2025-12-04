import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getPlants } from 'app/entities/plant/plant.reducer';
import { getEntities as getInspectionRoutes } from 'app/entities/inspection-route/inspection-route.reducer';
import { getEntities as getUserInfos } from 'app/entities/user-info/user-info.reducer';
import { createEntity, getEntity, reset, updateEntity } from './inspection-record.reducer';

export const InspectionRecordUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plants = useAppSelector(state => state.plant.entities);
  const inspectionRoutes = useAppSelector(state => state.inspectionRoute.entities);
  const userInfos = useAppSelector(state => state.userInfo.entities);
  const inspectionRecordEntity = useAppSelector(state => state.inspectionRecord.entity);
  const loading = useAppSelector(state => state.inspectionRecord.loading);
  const updating = useAppSelector(state => state.inspectionRecord.updating);
  const updateSuccess = useAppSelector(state => state.inspectionRecord.updateSuccess);

  const handleClose = () => {
    navigate('/inspection-record');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlants({}));
    dispatch(getInspectionRoutes({}));
    dispatch(getUserInfos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.startedAt = convertDateTimeToServer(values.startedAt);
    values.finishedAt = convertDateTimeToServer(values.finishedAt);

    const entity = {
      ...inspectionRecordEntity,
      ...values,
      plant: plants.find(it => it.id.toString() === values.plant?.toString()),
      inspectionRoute: inspectionRoutes.find(it => it.id.toString() === values.inspectionRoute?.toString()),
      createdBy: userInfos.find(it => it.id.toString() === values.createdBy?.toString()),
      startedBy: userInfos.find(it => it.id.toString() === values.startedBy?.toString()),
      finishedBy: userInfos.find(it => it.id.toString() === values.finishedBy?.toString()),
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
          createdAt: displayDefaultDateTime(),
          startedAt: displayDefaultDateTime(),
          finishedAt: displayDefaultDateTime(),
        }
      : {
          ...inspectionRecordEntity,
          createdAt: convertDateTimeFromServer(inspectionRecordEntity.createdAt),
          startedAt: convertDateTimeFromServer(inspectionRecordEntity.startedAt),
          finishedAt: convertDateTimeFromServer(inspectionRecordEntity.finishedAt),
          plant: inspectionRecordEntity?.plant?.id,
          inspectionRoute: inspectionRecordEntity?.inspectionRoute?.id,
          createdBy: inspectionRecordEntity?.createdBy?.id,
          startedBy: inspectionRecordEntity?.startedBy?.id,
          finishedBy: inspectionRecordEntity?.finishedBy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.inspectionRecord.home.createOrEditLabel" data-cy="InspectionRecordCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.inspectionRecord.home.createOrEditLabel">Create or edit a InspectionRecord</Translate>
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
                  id="inspection-record-id"
                  label={translate('thermographyApiApp.inspectionRecord.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecord.code')}
                id="inspection-record-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecord.name')}
                id="inspection-record-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecord.description')}
                id="inspection-record-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecord.maintenanceDocument')}
                id="inspection-record-maintenanceDocument"
                name="maintenanceDocument"
                data-cy="maintenanceDocument"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecord.createdAt')}
                id="inspection-record-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecord.expectedStartDate')}
                id="inspection-record-expectedStartDate"
                name="expectedStartDate"
                data-cy="expectedStartDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecord.expectedEndDate')}
                id="inspection-record-expectedEndDate"
                name="expectedEndDate"
                data-cy="expectedEndDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecord.started')}
                id="inspection-record-started"
                name="started"
                data-cy="started"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecord.startedAt')}
                id="inspection-record-startedAt"
                name="startedAt"
                data-cy="startedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecord.finished')}
                id="inspection-record-finished"
                name="finished"
                data-cy="finished"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRecord.finishedAt')}
                id="inspection-record-finishedAt"
                name="finishedAt"
                data-cy="finishedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="inspection-record-plant"
                name="plant"
                data-cy="plant"
                label={translate('thermographyApiApp.inspectionRecord.plant')}
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
                id="inspection-record-inspectionRoute"
                name="inspectionRoute"
                data-cy="inspectionRoute"
                label={translate('thermographyApiApp.inspectionRecord.inspectionRoute')}
                type="select"
              >
                <option value="" key="0" />
                {inspectionRoutes
                  ? inspectionRoutes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inspection-record-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('thermographyApiApp.inspectionRecord.createdBy')}
                type="select"
                required
              >
                <option value="" key="0" />
                {userInfos
                  ? userInfos.map(otherEntity => (
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
                id="inspection-record-startedBy"
                name="startedBy"
                data-cy="startedBy"
                label={translate('thermographyApiApp.inspectionRecord.startedBy')}
                type="select"
              >
                <option value="" key="0" />
                {userInfos
                  ? userInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inspection-record-finishedBy"
                name="finishedBy"
                data-cy="finishedBy"
                label={translate('thermographyApiApp.inspectionRecord.finishedBy')}
                type="select"
              >
                <option value="" key="0" />
                {userInfos
                  ? userInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inspection-record" replace color="info">
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

export default InspectionRecordUpdate;
