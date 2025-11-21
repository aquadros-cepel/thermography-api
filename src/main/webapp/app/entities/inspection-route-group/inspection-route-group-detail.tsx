import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inspection-route-group.reducer';

export const InspectionRouteGroupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inspectionRouteGroupEntity = useAppSelector(state => state.inspectionRouteGroup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inspectionRouteGroupDetailsHeading">
          <Translate contentKey="thermographyApiApp.inspectionRouteGroup.detail.title">InspectionRouteGroup</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.inspectionRouteGroup.id">Id</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteGroupEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="thermographyApiApp.inspectionRouteGroup.code">Code</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteGroupEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.inspectionRouteGroup.name">Name</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteGroupEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.inspectionRouteGroup.description">Description</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteGroupEntity.description}</dd>
          <dt>
            <span id="included">
              <Translate contentKey="thermographyApiApp.inspectionRouteGroup.included">Included</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteGroupEntity.included ? 'true' : 'false'}</dd>
          <dt>
            <span id="orderIndex">
              <Translate contentKey="thermographyApiApp.inspectionRouteGroup.orderIndex">Order Index</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteGroupEntity.orderIndex}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRouteGroup.inspectionRoute">Inspection Route</Translate>
          </dt>
          <dd>{inspectionRouteGroupEntity.inspectionRoute ? inspectionRouteGroupEntity.inspectionRoute.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRouteGroup.parentGroup">Parent Group</Translate>
          </dt>
          <dd>{inspectionRouteGroupEntity.parentGroup ? inspectionRouteGroupEntity.parentGroup.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inspection-route-group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inspection-route-group/${inspectionRouteGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InspectionRouteGroupDetail;
