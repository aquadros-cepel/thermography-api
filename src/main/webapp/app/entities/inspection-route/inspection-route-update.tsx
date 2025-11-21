import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getPlants } from 'app/entities/plant/plant.reducer';
import { getEntities as getUserInfos } from 'app/entities/user-info/user-info.reducer';
import { Periodicity } from 'app/shared/model/enumerations/periodicity.model';
import { createEntity, getEntity, reset, updateEntity } from './inspection-route.reducer';

export const InspectionRouteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plants = useAppSelector(state => state.plant.entities);
  const userInfos = useAppSelector(state => state.userInfo.entities);
  const inspectionRouteEntity = useAppSelector(state => state.inspectionRoute.entity);
  const loading = useAppSelector(state => state.inspectionRoute.loading);
  const updating = useAppSelector(state => state.inspectionRoute.updating);
  const updateSuccess = useAppSelector(state => state.inspectionRoute.updateSuccess);
  const periodicityValues = Object.keys(Periodicity);

  const handleClose = () => {
    navigate('/inspection-route');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlants({}));
    dispatch(getUserInfos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.duration !== undefined && typeof values.duration !== 'number') {
      values.duration = Number(values.duration);
    }
    values.createdAt = convertDateTimeToServer(values.createdAt);

    const entity = {
      ...inspectionRouteEntity,
      ...values,
      plant: plants.find(it => it.id.toString() === values.plant?.toString()),
      createdBy: userInfos.find(it => it.id.toString() === values.createdBy?.toString()),
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
        }
      : {
          periodicity: 'MONTHLY',
          ...inspectionRouteEntity,
          createdAt: convertDateTimeFromServer(inspectionRouteEntity.createdAt),
          plant: inspectionRouteEntity?.plant?.id,
          createdBy: inspectionRouteEntity?.createdBy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.inspectionRoute.home.createOrEditLabel" data-cy="InspectionRouteCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.inspectionRoute.home.createOrEditLabel">Create or edit a InspectionRoute</Translate>
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
                  id="inspection-route-id"
                  label={translate('thermographyApiApp.inspectionRoute.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRoute.code')}
                id="inspection-route-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRoute.name')}
                id="inspection-route-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRoute.description')}
                id="inspection-route-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRoute.maintenancePlan')}
                id="inspection-route-maintenancePlan"
                name="maintenancePlan"
                data-cy="maintenancePlan"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRoute.periodicity')}
                id="inspection-route-periodicity"
                name="periodicity"
                data-cy="periodicity"
                type="select"
              >
                {periodicityValues.map(periodicity => (
                  <option value={periodicity} key={periodicity}>
                    {translate(`thermographyApiApp.Periodicity.${periodicity}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRoute.duration')}
                id="inspection-route-duration"
                name="duration"
                data-cy="duration"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRoute.expectedStartDate')}
                id="inspection-route-expectedStartDate"
                name="expectedStartDate"
                data-cy="expectedStartDate"
                type="date"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRoute.createdAt')}
                id="inspection-route-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="inspection-route-plant"
                name="plant"
                data-cy="plant"
                label={translate('thermographyApiApp.inspectionRoute.plant')}
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
                id="inspection-route-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('thermographyApiApp.inspectionRoute.createdBy')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inspection-route" replace color="info">
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

export default InspectionRouteUpdate;
