import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { EquipmentType } from 'app/shared/model/enumerations/equipment-type.model';
import { createEntity, getEntity, reset, updateEntity } from './equipment-type-translation.reducer';

export const EquipmentTypeTranslationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const equipmentTypeTranslationEntity = useAppSelector(state => state.equipmentTypeTranslation.entity);
  const loading = useAppSelector(state => state.equipmentTypeTranslation.loading);
  const updating = useAppSelector(state => state.equipmentTypeTranslation.updating);
  const updateSuccess = useAppSelector(state => state.equipmentTypeTranslation.updateSuccess);
  const equipmentTypeValues = Object.keys(EquipmentType);

  const handleClose = () => {
    navigate('/equipment-type-translation');
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
      ...equipmentTypeTranslationEntity,
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
          code: 'AUTOTRANSFORMER',
          ...equipmentTypeTranslationEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.equipmentTypeTranslation.home.createOrEditLabel" data-cy="EquipmentTypeTranslationCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.equipmentTypeTranslation.home.createOrEditLabel">
              Create or edit a EquipmentTypeTranslation
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
                  id="equipment-type-translation-id"
                  label={translate('thermographyApiApp.equipmentTypeTranslation.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.equipmentTypeTranslation.code')}
                id="equipment-type-translation-code"
                name="code"
                data-cy="code"
                type="select"
              >
                {equipmentTypeValues.map(equipmentType => (
                  <option value={equipmentType} key={equipmentType}>
                    {translate(`thermographyApiApp.EquipmentType.${equipmentType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('thermographyApiApp.equipmentTypeTranslation.language')}
                id="equipment-type-translation-language"
                name="language"
                data-cy="language"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.equipmentTypeTranslation.name')}
                id="equipment-type-translation-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/equipment-type-translation" replace color="info">
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

export default EquipmentTypeTranslationUpdate;
