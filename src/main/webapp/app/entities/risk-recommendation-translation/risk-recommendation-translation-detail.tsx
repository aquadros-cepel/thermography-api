import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './risk-recommendation-translation.reducer';

export const RiskRecommendationTranslationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const riskRecommendationTranslationEntity = useAppSelector(state => state.riskRecommendationTranslation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="riskRecommendationTranslationDetailsHeading">
          <Translate contentKey="thermographyApiApp.riskRecommendationTranslation.detail.title">RiskRecommendationTranslation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.riskRecommendationTranslation.id">Id</Translate>
            </span>
          </dt>
          <dd>{riskRecommendationTranslationEntity.id}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="thermographyApiApp.riskRecommendationTranslation.language">Language</Translate>
            </span>
          </dt>
          <dd>{riskRecommendationTranslationEntity.language}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.riskRecommendationTranslation.name">Name</Translate>
            </span>
          </dt>
          <dd>{riskRecommendationTranslationEntity.name}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.riskRecommendationTranslation.riskPeriodicityDeadline">
              Risk Periodicity Deadline
            </Translate>
          </dt>
          <dd>
            {riskRecommendationTranslationEntity.riskPeriodicityDeadline
              ? riskRecommendationTranslationEntity.riskPeriodicityDeadline.id
              : ''}
          </dd>
        </dl>
        <Button tag={Link} to="/risk-recommendation-translation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/risk-recommendation-translation/${riskRecommendationTranslationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RiskRecommendationTranslationDetail;
