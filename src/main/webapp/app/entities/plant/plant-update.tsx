import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCompanies } from 'app/entities/company/company.reducer';
import { getEntities as getBusinessUnits } from 'app/entities/business-unit/business-unit.reducer';
import { createEntity, getEntity, reset, updateEntity } from './plant.reducer';

export const PlantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const companies = useAppSelector(state => state.company.entities);
  const businessUnits = useAppSelector(state => state.businessUnit.entities);
  const plantEntity = useAppSelector(state => state.plant.entity);
  const loading = useAppSelector(state => state.plant.loading);
  const updating = useAppSelector(state => state.plant.updating);
  const updateSuccess = useAppSelector(state => state.plant.updateSuccess);

  const handleClose = () => {
    navigate('/plant');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCompanies({}));
    dispatch(getBusinessUnits({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.latitude !== undefined && typeof values.latitude !== 'number') {
      values.latitude = Number(values.latitude);
    }
    if (values.longitude !== undefined && typeof values.longitude !== 'number') {
      values.longitude = Number(values.longitude);
    }

    const entity = {
      ...plantEntity,
      ...values,
      company: companies.find(it => it.id.toString() === values.company?.toString()),
      businessUnit: businessUnits.find(it => it.id.toString() === values.businessUnit?.toString()),
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
          ...plantEntity,
          company: plantEntity?.company?.id,
          businessUnit: plantEntity?.businessUnit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.plant.home.createOrEditLabel" data-cy="PlantCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.plant.home.createOrEditLabel">Create or edit a Plant</Translate>
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
                  id="plant-id"
                  label={translate('thermographyApiApp.plant.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.plant.name')}
                id="plant-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.plant.title')}
                id="plant-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.plant.description')}
                id="plant-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.plant.latitude')}
                id="plant-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.plant.longitude')}
                id="plant-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.plant.startDate')}
                id="plant-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
              />
              <ValidatedField
                id="plant-company"
                name="company"
                data-cy="company"
                label={translate('thermographyApiApp.plant.company')}
                type="select"
              >
                <option value="" key="0" />
                {companies
                  ? companies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="plant-businessUnit"
                name="businessUnit"
                data-cy="businessUnit"
                label={translate('thermographyApiApp.plant.businessUnit')}
                type="select"
              >
                <option value="" key="0" />
                {businessUnits
                  ? businessUnits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plant" replace color="info">
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

export default PlantUpdate;
