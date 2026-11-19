import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './thermographic-record.reducer';

export const ThermographicRecordDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const thermographicRecordEntity = useAppSelector(state => state.thermographicRecord.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="thermographicRecordDetailsHeading">
          <Translate contentKey="thermographyApiApp.thermographicRecord.detail.title">ThermographicRecord</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.thermographicRecord.id">Id</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.thermographicRecord.name">Name</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.name}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="thermographyApiApp.thermographicRecord.type">Type</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.type}</dd>
          <dt>
            <span id="serviceOrder">
              <Translate contentKey="thermographyApiApp.thermographicRecord.serviceOrder">Service Order</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.serviceOrder}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="thermographyApiApp.thermographicRecord.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {thermographicRecordEntity.createdAt ? (
              <TextFormat value={thermographicRecordEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="analysisDescription">
              <Translate contentKey="thermographyApiApp.thermographicRecord.analysisDescription">Analysis Description</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.analysisDescription}</dd>
          <dt>
            <span id="condition">
              <Translate contentKey="thermographyApiApp.thermographicRecord.condition">Condition</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.condition}</dd>
          <dt>
            <span id="deltaT">
              <Translate contentKey="thermographyApiApp.thermographicRecord.deltaT">Delta T</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.deltaT}</dd>
          <dt>
            <span id="periodicity">
              <Translate contentKey="thermographyApiApp.thermographicRecord.periodicity">Periodicity</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.periodicity}</dd>
          <dt>
            <span id="deadlineExecution">
              <Translate contentKey="thermographyApiApp.thermographicRecord.deadlineExecution">Deadline Execution</Translate>
            </span>
          </dt>
          <dd>
            {thermographicRecordEntity.deadlineExecution ? (
              <TextFormat value={thermographicRecordEntity.deadlineExecution} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="nextMonitoring">
              <Translate contentKey="thermographyApiApp.thermographicRecord.nextMonitoring">Next Monitoring</Translate>
            </span>
          </dt>
          <dd>
            {thermographicRecordEntity.nextMonitoring ? (
              <TextFormat value={thermographicRecordEntity.nextMonitoring} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="recommendations">
              <Translate contentKey="thermographyApiApp.thermographicRecord.recommendations">Recommendations</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.recommendations}</dd>
          <dt>
            <span id="finished">
              <Translate contentKey="thermographyApiApp.thermographicRecord.finished">Finished</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.finished ? 'true' : 'false'}</dd>
          <dt>
            <span id="finishedAt">
              <Translate contentKey="thermographyApiApp.thermographicRecord.finishedAt">Finished At</Translate>
            </span>
          </dt>
          <dd>
            {thermographicRecordEntity.finishedAt ? (
              <TextFormat value={thermographicRecordEntity.finishedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="current">
              <Translate contentKey="thermographyApiApp.thermographicRecord.current">Current</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.current}</dd>
          <dt>
            <span id="voltage">
              <Translate contentKey="thermographyApiApp.thermographicRecord.voltage">Voltage</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.voltage}</dd>
          <dt>
            <span id="windSpeed">
              <Translate contentKey="thermographyApiApp.thermographicRecord.windSpeed">Wind Speed</Translate>
            </span>
          </dt>
          <dd>{thermographicRecordEntity.windSpeed}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicRecord.plant">Plant</Translate>
          </dt>
          <dd>{thermographicRecordEntity.plant ? thermographicRecordEntity.plant.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicRecord.route">Route</Translate>
          </dt>
          <dd>{thermographicRecordEntity.route ? thermographicRecordEntity.route.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicRecord.equipment">Equipment</Translate>
          </dt>
          <dd>{thermographicRecordEntity.equipment ? thermographicRecordEntity.equipment.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicRecord.component">Component</Translate>
          </dt>
          <dd>{thermographicRecordEntity.component ? thermographicRecordEntity.component.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicRecord.createdBy">Created By</Translate>
          </dt>
          <dd>{thermographicRecordEntity.createdBy ? thermographicRecordEntity.createdBy.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicRecord.finishedBy">Finished By</Translate>
          </dt>
          <dd>{thermographicRecordEntity.finishedBy ? thermographicRecordEntity.finishedBy.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicRecord.thermogram">Thermogram</Translate>
          </dt>
          <dd>{thermographicRecordEntity.thermogram ? thermographicRecordEntity.thermogram.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicRecord.thermogramRef">Thermogram Ref</Translate>
          </dt>
          <dd>{thermographicRecordEntity.thermogramRef ? thermographicRecordEntity.thermogramRef.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicRecord.parentRecord">Parent Record</Translate>
          </dt>
          <dd>{thermographicRecordEntity.parentRecord ? thermographicRecordEntity.parentRecord.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/thermographic-record" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/thermographic-record/${thermographicRecordEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ThermographicRecordDetail;
