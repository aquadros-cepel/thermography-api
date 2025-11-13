import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getRiskPeriodicityDeadlines } from 'app/entities/risk-periodicity-deadline/risk-periodicity-deadline.reducer';
import { createEntity, getEntity, reset, updateEntity } from './risk-recommendation-translation.reducer';

export const RiskRecommendationTranslationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const riskPeriodicityDeadlines = useAppSelector(state => state.riskPeriodicityDeadline.entities);
  const riskRecommendationTranslationEntity = useAppSelector(state => state.riskRecommendationTranslation.entity);
  const loading = useAppSelector(state => state.riskRecommendationTranslation.loading);
  const updating = useAppSelector(state => state.riskRecommendationTranslation.updating);
  const updateSuccess = useAppSelector(state => state.riskRecommendationTranslation.updateSuccess);

  const handleClose = () => {
    navigate('/risk-recommendation-translation');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRiskPeriodicityDeadlines({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...riskRecommendationTranslationEntity,
      ...values,
      riskPeriodicityDeadline: riskPeriodicityDeadlines.find(it => it.id.toString() === values.riskPeriodicityDeadline?.toString()),
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
          ...riskRecommendationTranslationEntity,
          riskPeriodicityDeadline: riskRecommendationTranslationEntity?.riskPeriodicityDeadline?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="thermographyApiApp.riskRecommendationTranslation.home.createOrEditLabel"
            data-cy="RiskRecommendationTranslationCreateUpdateHeading"
          >
            <Translate contentKey="thermographyApiApp.riskRecommendationTranslation.home.createOrEditLabel">
              Create or edit a RiskRecommendationTranslation
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
                  id="risk-recommendation-translation-id"
                  label={translate('thermographyApiApp.riskRecommendationTranslation.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.riskRecommendationTranslation.language')}
                id="risk-recommendation-translation-language"
                name="language"
                data-cy="language"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.riskRecommendationTranslation.name')}
                id="risk-recommendation-translation-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="risk-recommendation-translation-riskPeriodicityDeadline"
                name="riskPeriodicityDeadline"
                data-cy="riskPeriodicityDeadline"
                label={translate('thermographyApiApp.riskRecommendationTranslation.riskPeriodicityDeadline')}
                type="select"
              >
                <option value="" key="0" />
                {riskPeriodicityDeadlines
                  ? riskPeriodicityDeadlines.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/risk-recommendation-translation"
                replace
                color="info"
              >
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

export default RiskRecommendationTranslationUpdate;
