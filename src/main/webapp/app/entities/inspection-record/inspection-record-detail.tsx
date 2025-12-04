import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inspection-record.reducer';

export const InspectionRecordDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inspectionRecordEntity = useAppSelector(state => state.inspectionRecord.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inspectionRecordDetailsHeading">
          <Translate contentKey="thermographyApiApp.inspectionRecord.detail.title">InspectionRecord</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.inspectionRecord.id">Id</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="thermographyApiApp.inspectionRecord.code">Code</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.inspectionRecord.name">Name</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.inspectionRecord.description">Description</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordEntity.description}</dd>
          <dt>
            <span id="maintenanceDocument">
              <Translate contentKey="thermographyApiApp.inspectionRecord.maintenanceDocument">Maintenance Document</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordEntity.maintenanceDocument}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="thermographyApiApp.inspectionRecord.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRecordEntity.createdAt ? (
              <TextFormat value={inspectionRecordEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="expectedStartDate">
              <Translate contentKey="thermographyApiApp.inspectionRecord.expectedStartDate">Expected Start Date</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRecordEntity.expectedStartDate ? (
              <TextFormat value={inspectionRecordEntity.expectedStartDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="expectedEndDate">
              <Translate contentKey="thermographyApiApp.inspectionRecord.expectedEndDate">Expected End Date</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRecordEntity.expectedEndDate ? (
              <TextFormat value={inspectionRecordEntity.expectedEndDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="started">
              <Translate contentKey="thermographyApiApp.inspectionRecord.started">Started</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordEntity.started ? 'true' : 'false'}</dd>
          <dt>
            <span id="startedAt">
              <Translate contentKey="thermographyApiApp.inspectionRecord.startedAt">Started At</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRecordEntity.startedAt ? (
              <TextFormat value={inspectionRecordEntity.startedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="finished">
              <Translate contentKey="thermographyApiApp.inspectionRecord.finished">Finished</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordEntity.finished ? 'true' : 'false'}</dd>
          <dt>
            <span id="finishedAt">
              <Translate contentKey="thermographyApiApp.inspectionRecord.finishedAt">Finished At</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRecordEntity.finishedAt ? (
              <TextFormat value={inspectionRecordEntity.finishedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRecord.plant">Plant</Translate>
          </dt>
          <dd>{inspectionRecordEntity.plant ? inspectionRecordEntity.plant.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRecord.inspectionRoute">Inspection Route</Translate>
          </dt>
          <dd>{inspectionRecordEntity.inspectionRoute ? inspectionRecordEntity.inspectionRoute.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRecord.createdBy">Created By</Translate>
          </dt>
          <dd>{inspectionRecordEntity.createdBy ? inspectionRecordEntity.createdBy.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRecord.startedBy">Started By</Translate>
          </dt>
          <dd>{inspectionRecordEntity.startedBy ? inspectionRecordEntity.startedBy.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRecord.finishedBy">Finished By</Translate>
          </dt>
          <dd>{inspectionRecordEntity.finishedBy ? inspectionRecordEntity.finishedBy.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inspection-record" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inspection-record/${inspectionRecordEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InspectionRecordDetail;
