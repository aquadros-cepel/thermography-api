import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './thermographic-inspection-record.reducer';

export const ThermographicInspectionRecordDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const thermographicInspectionRecordEntity = useAppSelector(state => state.thermographicInspectionRecord.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="thermographicInspectionRecordDetailsHeading">
          <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.detail.title">ThermographicInspectionRecord</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.id">Id</Translate>
            </span>
          </dt>
          <dd>{thermographicInspectionRecordEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.name">Name</Translate>
            </span>
          </dt>
          <dd>{thermographicInspectionRecordEntity.name}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.type">Type</Translate>
            </span>
          </dt>
          <dd>{thermographicInspectionRecordEntity.type}</dd>
          <dt>
            <span id="serviceOrder">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.serviceOrder">Service Order</Translate>
            </span>
          </dt>
          <dd>{thermographicInspectionRecordEntity.serviceOrder}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {thermographicInspectionRecordEntity.createdAt ? (
              <TextFormat value={thermographicInspectionRecordEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="analysisDescription">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.analysisDescription">Analysis Description</Translate>
            </span>
          </dt>
          <dd>{thermographicInspectionRecordEntity.analysisDescription}</dd>
          <dt>
            <span id="condition">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.condition">Condition</Translate>
            </span>
          </dt>
          <dd>{thermographicInspectionRecordEntity.condition}</dd>
          <dt>
            <span id="deltaT">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.deltaT">Delta T</Translate>
            </span>
          </dt>
          <dd>{thermographicInspectionRecordEntity.deltaT}</dd>
          <dt>
            <span id="periodicity">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.periodicity">Periodicity</Translate>
            </span>
          </dt>
          <dd>{thermographicInspectionRecordEntity.periodicity}</dd>
          <dt>
            <span id="deadlineExecution">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.deadlineExecution">Deadline Execution</Translate>
            </span>
          </dt>
          <dd>
            {thermographicInspectionRecordEntity.deadlineExecution ? (
              <TextFormat value={thermographicInspectionRecordEntity.deadlineExecution} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="nextMonitoring">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.nextMonitoring">Next Monitoring</Translate>
            </span>
          </dt>
          <dd>
            {thermographicInspectionRecordEntity.nextMonitoring ? (
              <TextFormat value={thermographicInspectionRecordEntity.nextMonitoring} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="recommendations">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.recommendations">Recommendations</Translate>
            </span>
          </dt>
          <dd>{thermographicInspectionRecordEntity.recommendations}</dd>
          <dt>
            <span id="finished">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.finished">Finished</Translate>
            </span>
          </dt>
          <dd>{thermographicInspectionRecordEntity.finished ? 'true' : 'false'}</dd>
          <dt>
            <span id="finishedAt">
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.finishedAt">Finished At</Translate>
            </span>
          </dt>
          <dd>
            {thermographicInspectionRecordEntity.finishedAt ? (
              <TextFormat value={thermographicInspectionRecordEntity.finishedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.plant">Plant</Translate>
          </dt>
          <dd>{thermographicInspectionRecordEntity.plant ? thermographicInspectionRecordEntity.plant.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.route">Route</Translate>
          </dt>
          <dd>{thermographicInspectionRecordEntity.route ? thermographicInspectionRecordEntity.route.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.equipment">Equipment</Translate>
          </dt>
          <dd>{thermographicInspectionRecordEntity.equipment ? thermographicInspectionRecordEntity.equipment.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.component">Component</Translate>
          </dt>
          <dd>{thermographicInspectionRecordEntity.component ? thermographicInspectionRecordEntity.component.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.createdBy">Created By</Translate>
          </dt>
          <dd>{thermographicInspectionRecordEntity.createdBy ? thermographicInspectionRecordEntity.createdBy.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.finishedBy">Finished By</Translate>
          </dt>
          <dd>{thermographicInspectionRecordEntity.finishedBy ? thermographicInspectionRecordEntity.finishedBy.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.thermogram">Thermogram</Translate>
          </dt>
          <dd>{thermographicInspectionRecordEntity.thermogram ? thermographicInspectionRecordEntity.thermogram.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.thermogramRef">Thermogram Ref</Translate>
          </dt>
          <dd>{thermographicInspectionRecordEntity.thermogramRef ? thermographicInspectionRecordEntity.thermogramRef.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/thermographic-inspection-record" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/thermographic-inspection-record/${thermographicInspectionRecordEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ThermographicInspectionRecordDetail;
