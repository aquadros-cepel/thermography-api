import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inspection-route.reducer';

export const InspectionRouteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inspectionRouteEntity = useAppSelector(state => state.inspectionRoute.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inspectionRouteDetailsHeading">
          <Translate contentKey="thermographyApiApp.inspectionRoute.detail.title">InspectionRoute</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.inspectionRoute.id">Id</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="thermographyApiApp.inspectionRoute.code">Code</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.inspectionRoute.name">Name</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.inspectionRoute.description">Description</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteEntity.description}</dd>
          <dt>
            <span id="maintenancePlan">
              <Translate contentKey="thermographyApiApp.inspectionRoute.maintenancePlan">Maintenance Plan</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteEntity.maintenancePlan}</dd>
          <dt>
            <span id="periodicity">
              <Translate contentKey="thermographyApiApp.inspectionRoute.periodicity">Periodicity</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteEntity.periodicity}</dd>
          <dt>
            <span id="duration">
              <Translate contentKey="thermographyApiApp.inspectionRoute.duration">Duration</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteEntity.duration}</dd>
          <dt>
            <span id="expectedStartDate">
              <Translate contentKey="thermographyApiApp.inspectionRoute.expectedStartDate">Expected Start Date</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRouteEntity.expectedStartDate ? (
              <TextFormat value={inspectionRouteEntity.expectedStartDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="thermographyApiApp.inspectionRoute.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRouteEntity.createdAt ? (
              <TextFormat value={inspectionRouteEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRoute.plant">Plant</Translate>
          </dt>
          <dd>{inspectionRouteEntity.plant ? inspectionRouteEntity.plant.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRoute.createdBy">Created By</Translate>
          </dt>
          <dd>{inspectionRouteEntity.createdBy ? inspectionRouteEntity.createdBy.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inspection-route" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inspection-route/${inspectionRouteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InspectionRouteDetail;
