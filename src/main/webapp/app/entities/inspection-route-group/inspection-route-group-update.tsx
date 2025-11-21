import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getInspectionRoutes } from 'app/entities/inspection-route/inspection-route.reducer';
import { getEntities as getInspectionRouteGroups } from 'app/entities/inspection-route-group/inspection-route-group.reducer';
import { createEntity, getEntity, reset, updateEntity } from './inspection-route-group.reducer';

export const InspectionRouteGroupUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const inspectionRoutes = useAppSelector(state => state.inspectionRoute.entities);
  const inspectionRouteGroups = useAppSelector(state => state.inspectionRouteGroup.entities);
  const inspectionRouteGroupEntity = useAppSelector(state => state.inspectionRouteGroup.entity);
  const loading = useAppSelector(state => state.inspectionRouteGroup.loading);
  const updating = useAppSelector(state => state.inspectionRouteGroup.updating);
  const updateSuccess = useAppSelector(state => state.inspectionRouteGroup.updateSuccess);

  const handleClose = () => {
    navigate('/inspection-route-group');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getInspectionRoutes({}));
    dispatch(getInspectionRouteGroups({}));
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
      ...inspectionRouteGroupEntity,
      ...values,
      inspectionRoute: inspectionRoutes.find(it => it.id.toString() === values.inspectionRoute?.toString()),
      subGroup: inspectionRouteGroups.find(it => it.id.toString() === values.subGroup?.toString()),
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
          ...inspectionRouteGroupEntity,
          inspectionRoute: inspectionRouteGroupEntity?.inspectionRoute?.id,
          subGroup: inspectionRouteGroupEntity?.subGroup?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.inspectionRouteGroup.home.createOrEditLabel" data-cy="InspectionRouteGroupCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.inspectionRouteGroup.home.createOrEditLabel">
              Create or edit a InspectionRouteGroup
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
                  id="inspection-route-group-id"
                  label={translate('thermographyApiApp.inspectionRouteGroup.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteGroup.code')}
                id="inspection-route-group-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteGroup.name')}
                id="inspection-route-group-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteGroup.description')}
                id="inspection-route-group-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteGroup.included')}
                id="inspection-route-group-included"
                name="included"
                data-cy="included"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('thermographyApiApp.inspectionRouteGroup.orderIndex')}
                id="inspection-route-group-orderIndex"
                name="orderIndex"
                data-cy="orderIndex"
                type="text"
              />
              <ValidatedField
                id="inspection-route-group-inspectionRoute"
                name="inspectionRoute"
                data-cy="inspectionRoute"
                label={translate('thermographyApiApp.inspectionRouteGroup.inspectionRoute')}
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
                id="inspection-route-group-subGroup"
                name="subGroup"
                data-cy="subGroup"
                label={translate('thermographyApiApp.inspectionRouteGroup.subGroup')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inspection-route-group" replace color="info">
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

export default InspectionRouteGroupUpdate;
