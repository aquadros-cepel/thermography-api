import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getUserInfos } from 'app/entities/user-info/user-info.reducer';
import { createEntity, getEntity, reset, updateEntity } from './inspection-route-record.reducer';

export const InspectionRouteRecordUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userInfos = useAppSelector(state => state.userInfo.entities);
  const inspectionRouteRecordEntity = useAppSelector(state => state.inspectionRouteRecord.entity);
  const loading = useAppSelector(state => state.inspectionRouteRecord.loading);
  const updating = useAppSelector(state => state.inspectionRouteRecord.updating);
  const updateSuccess = useAppSelector(state => state.inspectionRouteRecord.updateSuccess);

  const handleClose = () => {
    navigate('/inspection-route-record');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
      ...inspectionRouteRecordEntity,
      ...values,
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
          ...inspectionRouteRecordEntity,
          createdAt: convertDateTimeFromServer(inspectionRouteRecordEntity.createdAt),
          startedAt: convertDateTimeFromServer(inspectionRouteRecordEntity.startedAt),
          finishedAt: convertDateTimeFromServer(inspectionRouteRecordEntity.finishedAt),
          startedBy: inspectionRouteRecordEntity?.startedBy?.id,
          finishedBy: inspectionRouteRecordEntity?.finishedBy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.inspectionRouteRecord.home.createOrEditLabel" data-cy="InspectionRouteRecordCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.inspectionRouteRecord.home.createOrEditLabel">
              Create or edit a InspectionRouteRecord
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
                  id="inspection-route-record-id"
                  label={translate('thermographyApiApp.inspectionRouteRecord.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteRecord.code')}
                id="inspection-route-record-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteRecord.name')}
                id="inspection-route-record-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteRecord.description')}
                id="inspection-route-record-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteRecord.maintenanceDocument')}
                id="inspection-route-record-maintenanceDocument"
                name="maintenanceDocument"
                data-cy="maintenanceDocument"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteRecord.createdAt')}
                id="inspection-route-record-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteRecord.expectedStartDate')}
                id="inspection-route-record-expectedStartDate"
                name="expectedStartDate"
                data-cy="expectedStartDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteRecord.expectedEndDate')}
                id="inspection-route-record-expectedEndDate"
                name="expectedEndDate"
                data-cy="expectedEndDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteRecord.started')}
                id="inspection-route-record-started"
                name="started"
                data-cy="started"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteRecord.startedAt')}
                id="inspection-route-record-startedAt"
                name="startedAt"
                data-cy="startedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteRecord.finished')}
                id="inspection-route-record-finished"
                name="finished"
                data-cy="finished"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteRecord.finishedAt')}
                id="inspection-route-record-finishedAt"
                name="finishedAt"
                data-cy="finishedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="inspection-route-record-startedBy"
                name="startedBy"
                data-cy="startedBy"
                label={translate('thermographyApiApp.inspectionRouteRecord.startedBy')}
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
                id="inspection-route-record-finishedBy"
                name="finishedBy"
                data-cy="finishedBy"
                label={translate('thermographyApiApp.inspectionRouteRecord.finishedBy')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inspection-route-record" replace color="info">
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

export default InspectionRouteRecordUpdate;
