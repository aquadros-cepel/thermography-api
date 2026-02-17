import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { DatetimeUnit } from 'app/shared/model/enumerations/datetime-unit.model';
import { createEntity, getEntity, reset, updateEntity } from './risk-periodicity-deadline.reducer';

export const RiskPeriodicityDeadlineUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const riskPeriodicityDeadlineEntity = useAppSelector(state => state.riskPeriodicityDeadline.entity);
  const loading = useAppSelector(state => state.riskPeriodicityDeadline.loading);
  const updating = useAppSelector(state => state.riskPeriodicityDeadline.updating);
  const updateSuccess = useAppSelector(state => state.riskPeriodicityDeadline.updateSuccess);
  const datetimeUnitValues = Object.keys(DatetimeUnit);

  const handleClose = () => {
    navigate('/risk-periodicity-deadline');
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
    if (values.deadline !== undefined && typeof values.deadline !== 'number') {
      values.deadline = Number(values.deadline);
    }
    if (values.periodicity !== undefined && typeof values.periodicity !== 'number') {
      values.periodicity = Number(values.periodicity);
    }

    const entity = {
      ...riskPeriodicityDeadlineEntity,
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
          deadlineUnit: 'HOUR',
          periodicityUnit: 'HOUR',
          ...riskPeriodicityDeadlineEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.riskPeriodicityDeadline.home.createOrEditLabel" data-cy="RiskPeriodicityDeadlineCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.home.createOrEditLabel">
              Create or edit a RiskPeriodicityDeadline
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
                  id="risk-periodicity-deadline-id"
                  label={translate('thermographyApiApp.riskPeriodicityDeadline.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.riskPeriodicityDeadline.name')}
                id="risk-periodicity-deadline-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.riskPeriodicityDeadline.description')}
                id="risk-periodicity-deadline-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.riskPeriodicityDeadline.deadline')}
                id="risk-periodicity-deadline-deadline"
                name="deadline"
                data-cy="deadline"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.riskPeriodicityDeadline.deadlineUnit')}
                id="risk-periodicity-deadline-deadlineUnit"
                name="deadlineUnit"
                data-cy="deadlineUnit"
                type="select"
              >
                {datetimeUnitValues.map(datetimeUnit => (
                  <option value={datetimeUnit} key={datetimeUnit}>
                    {translate(`thermographyApiApp.DatetimeUnit.${datetimeUnit}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('thermographyApiApp.riskPeriodicityDeadline.periodicity')}
                id="risk-periodicity-deadline-periodicity"
                name="periodicity"
                data-cy="periodicity"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.riskPeriodicityDeadline.periodicityUnit')}
                id="risk-periodicity-deadline-periodicityUnit"
                name="periodicityUnit"
                data-cy="periodicityUnit"
                type="select"
              >
                {datetimeUnitValues.map(datetimeUnit => (
                  <option value={datetimeUnit} key={datetimeUnit}>
                    {translate(`thermographyApiApp.DatetimeUnit.${datetimeUnit}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('thermographyApiApp.riskPeriodicityDeadline.recommendations')}
                id="risk-periodicity-deadline-recommendations"
                name="recommendations"
                data-cy="recommendations"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/risk-periodicity-deadline" replace color="info">
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

export default RiskPeriodicityDeadlineUpdate;
