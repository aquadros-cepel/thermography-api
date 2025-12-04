import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inspection-record-group-equipment.reducer';

export const InspectionRecordGroupEquipmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inspectionRecordGroupEquipmentEntity = useAppSelector(state => state.inspectionRecordGroupEquipment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inspectionRecordGroupEquipmentDetailsHeading">
          <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.detail.title">InspectionRecordGroupEquipment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.id">Id</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordGroupEquipmentEntity.id}</dd>
          <dt>
            <span id="orderIndex">
              <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.orderIndex">Order Index</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordGroupEquipmentEntity.orderIndex}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.status">Status</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordGroupEquipmentEntity.status}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.inspectionRecordGroup">
              Inspection Record Group
            </Translate>
          </dt>
          <dd>
            {inspectionRecordGroupEquipmentEntity.inspectionRecordGroup
              ? inspectionRecordGroupEquipmentEntity.inspectionRecordGroup.id
              : ''}
          </dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.equipment">Equipment</Translate>
          </dt>
          <dd>{inspectionRecordGroupEquipmentEntity.equipment ? inspectionRecordGroupEquipmentEntity.equipment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inspection-record-group-equipment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/inspection-record-group-equipment/${inspectionRecordGroupEquipmentEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InspectionRecordGroupEquipmentDetail;
