import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inspection-route-record.reducer';

export const InspectionRouteRecordDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inspectionRouteRecordEntity = useAppSelector(state => state.inspectionRouteRecord.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inspectionRouteRecordDetailsHeading">
          <Translate contentKey="thermographyApiApp.inspectionRouteRecord.detail.title">InspectionRouteRecord</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.id">Id</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteRecordEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.code">Code</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteRecordEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.name">Name</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteRecordEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.description">Description</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteRecordEntity.description}</dd>
          <dt>
            <span id="maintenanceDocument">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.maintenanceDocument">Maintenance Document</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteRecordEntity.maintenanceDocument}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRouteRecordEntity.createdAt ? (
              <TextFormat value={inspectionRouteRecordEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="expectedStartDate">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.expectedStartDate">Expected Start Date</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRouteRecordEntity.expectedStartDate ? (
              <TextFormat value={inspectionRouteRecordEntity.expectedStartDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="expectedEndDate">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.expectedEndDate">Expected End Date</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRouteRecordEntity.expectedEndDate ? (
              <TextFormat value={inspectionRouteRecordEntity.expectedEndDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="started">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.started">Started</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteRecordEntity.started ? 'true' : 'false'}</dd>
          <dt>
            <span id="startedAt">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.startedAt">Started At</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRouteRecordEntity.startedAt ? (
              <TextFormat value={inspectionRouteRecordEntity.startedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="finished">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.finished">Finished</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteRecordEntity.finished ? 'true' : 'false'}</dd>
          <dt>
            <span id="finishedAt">
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.finishedAt">Finished At</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRouteRecordEntity.finishedAt ? (
              <TextFormat value={inspectionRouteRecordEntity.finishedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRouteRecord.startedBy">Started By</Translate>
          </dt>
          <dd>{inspectionRouteRecordEntity.startedBy ? inspectionRouteRecordEntity.startedBy.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRouteRecord.finishedBy">Finished By</Translate>
          </dt>
          <dd>{inspectionRouteRecordEntity.finishedBy ? inspectionRouteRecordEntity.finishedBy.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inspection-route-record" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inspection-route-record/${inspectionRouteRecordEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InspectionRouteRecordDetail;
