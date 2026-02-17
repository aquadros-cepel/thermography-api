import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './risk-periodicity-deadline.reducer';

export const RiskPeriodicityDeadlineDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const riskPeriodicityDeadlineEntity = useAppSelector(state => state.riskPeriodicityDeadline.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="riskPeriodicityDeadlineDetailsHeading">
          <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.detail.title">RiskPeriodicityDeadline</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.id">Id</Translate>
            </span>
          </dt>
          <dd>{riskPeriodicityDeadlineEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.name">Name</Translate>
            </span>
          </dt>
          <dd>{riskPeriodicityDeadlineEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.description">Description</Translate>
            </span>
          </dt>
          <dd>{riskPeriodicityDeadlineEntity.description}</dd>
          <dt>
            <span id="deadline">
              <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.deadline">Deadline</Translate>
            </span>
          </dt>
          <dd>{riskPeriodicityDeadlineEntity.deadline}</dd>
          <dt>
            <span id="deadlineUnit">
              <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.deadlineUnit">Deadline Unit</Translate>
            </span>
          </dt>
          <dd>{riskPeriodicityDeadlineEntity.deadlineUnit}</dd>
          <dt>
            <span id="periodicity">
              <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.periodicity">Periodicity</Translate>
            </span>
          </dt>
          <dd>{riskPeriodicityDeadlineEntity.periodicity}</dd>
          <dt>
            <span id="periodicityUnit">
              <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.periodicityUnit">Periodicity Unit</Translate>
            </span>
          </dt>
          <dd>{riskPeriodicityDeadlineEntity.periodicityUnit}</dd>
          <dt>
            <span id="recommendations">
              <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.recommendations">Recommendations</Translate>
            </span>
          </dt>
          <dd>{riskPeriodicityDeadlineEntity.recommendations}</dd>
        </dl>
        <Button tag={Link} to="/risk-periodicity-deadline" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/risk-periodicity-deadline/${riskPeriodicityDeadlineEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RiskPeriodicityDeadlineDetail;
