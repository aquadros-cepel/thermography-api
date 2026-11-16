import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedBlobField, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './company.reducer';

export const CompanyUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const companyEntity = useAppSelector(state => state.company.entity);
  const loading = useAppSelector(state => state.company.loading);
  const updating = useAppSelector(state => state.company.updating);
  const updateSuccess = useAppSelector(state => state.company.updateSuccess);

  const handleClose = () => {
    navigate('/company');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...companyEntity,
      ...values,
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
          ...companyEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.company.home.createOrEditLabel" data-cy="CompanyCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.company.home.createOrEditLabel">Create or edit a Company</Translate>
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
                  id="company-id"
                  label={translate('thermographyApiApp.company.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.company.code')}
                id="company-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.name')}
                id="company-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.description')}
                id="company-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedBlobField
                label={translate('thermographyApiApp.company.logo')}
                id="company-logo"
                name="logo"
                data-cy="logo"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.address')}
                id="company-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.zipCode')}
                id="company-zipCode"
                name="zipCode"
                data-cy="zipCode"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.street')}
                id="company-street"
                name="street"
                data-cy="street"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.number')}
                id="company-number"
                name="number"
                data-cy="number"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.complement')}
                id="company-complement"
                name="complement"
                data-cy="complement"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.neighborhood')}
                id="company-neighborhood"
                name="neighborhood"
                data-cy="neighborhood"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.city')}
                id="company-city"
                name="city"
                data-cy="city"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.state')}
                id="company-state"
                name="state"
                data-cy="state"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.country')}
                id="company-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.primaryPhoneNumber')}
                id="company-primaryPhoneNumber"
                name="primaryPhoneNumber"
                data-cy="primaryPhoneNumber"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.secondaryPhoneNumber')}
                id="company-secondaryPhoneNumber"
                name="secondaryPhoneNumber"
                data-cy="secondaryPhoneNumber"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.company.taxIdNumber')}
                id="company-taxIdNumber"
                name="taxIdNumber"
                data-cy="taxIdNumber"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/company" replace color="info">
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

export default CompanyUpdate;
