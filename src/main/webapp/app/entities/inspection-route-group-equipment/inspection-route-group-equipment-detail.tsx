import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inspection-route-group-equipment.reducer';

export const InspectionRouteGroupEquipmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inspectionRouteGroupEquipmentEntity = useAppSelector(state => state.inspectionRouteGroupEquipment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inspectionRouteGroupEquipmentDetailsHeading">
          <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.detail.title">InspectionRouteGroupEquipment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.id">Id</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteGroupEquipmentEntity.id}</dd>
          <dt>
            <span id="included">
              <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.included">Included</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteGroupEquipmentEntity.included ? 'true' : 'false'}</dd>
          <dt>
            <span id="orderIndex">
              <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.orderIndex">Order Index</Translate>
            </span>
          </dt>
          <dd>{inspectionRouteGroupEquipmentEntity.orderIndex}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.inspectionRouteGroup">Inspection Route Group</Translate>
          </dt>
          <dd>
            {inspectionRouteGroupEquipmentEntity.inspectionRouteGroup ? inspectionRouteGroupEquipmentEntity.inspectionRouteGroup.id : ''}
          </dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.equipment">Equipment</Translate>
          </dt>
          <dd>{inspectionRouteGroupEquipmentEntity.equipment ? inspectionRouteGroupEquipmentEntity.equipment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inspection-route-group-equipment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inspection-route-group-equipment/${inspectionRouteGroupEquipmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InspectionRouteGroupEquipmentDetail;
