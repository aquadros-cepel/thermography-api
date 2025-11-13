import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getThermograms } from 'app/entities/thermogram/thermogram.reducer';
import { createEntity, getEntity, reset, updateEntity } from './roi.reducer';

export const ROIUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const thermograms = useAppSelector(state => state.thermogram.entities);
  const rOIEntity = useAppSelector(state => state.rOI.entity);
  const loading = useAppSelector(state => state.rOI.loading);
  const updating = useAppSelector(state => state.rOI.updating);
  const updateSuccess = useAppSelector(state => state.rOI.updateSuccess);

  const handleClose = () => {
    navigate('/roi');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getThermograms({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.maxTemp !== undefined && typeof values.maxTemp !== 'number') {
      values.maxTemp = Number(values.maxTemp);
    }

    const entity = {
      ...rOIEntity,
      ...values,
      thermogram: thermograms.find(it => it.id.toString() === values.thermogram?.toString()),
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
          ...rOIEntity,
          thermogram: rOIEntity?.thermogram?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.rOI.home.createOrEditLabel" data-cy="ROICreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.rOI.home.createOrEditLabel">Create or edit a ROI</Translate>
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
                  id="roi-id"
                  label={translate('thermographyApiApp.rOI.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.rOI.type')}
                id="roi-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.rOI.label')}
                id="roi-label"
                name="label"
                data-cy="label"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.rOI.maxTemp')}
                id="roi-maxTemp"
                name="maxTemp"
                data-cy="maxTemp"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="roi-thermogram"
                name="thermogram"
                data-cy="thermogram"
                label={translate('thermographyApiApp.rOI.thermogram')}
                type="select"
                required
              >
                <option value="" key="0" />
                {thermograms
                  ? thermograms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/roi" replace color="info">
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

export default ROIUpdate;
