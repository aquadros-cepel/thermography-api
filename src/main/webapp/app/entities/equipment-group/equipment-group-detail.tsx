import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './equipment-group.reducer';

export const EquipmentGroupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const equipmentGroupEntity = useAppSelector(state => state.equipmentGroup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="equipmentGroupDetailsHeading">
          <Translate contentKey="thermographyApiApp.equipmentGroup.detail.title">EquipmentGroup</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.equipmentGroup.id">Id</Translate>
            </span>
          </dt>
          <dd>{equipmentGroupEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.equipmentGroup.name">Name</Translate>
            </span>
          </dt>
          <dd>{equipmentGroupEntity.name}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="thermographyApiApp.equipmentGroup.title">Title</Translate>
            </span>
          </dt>
          <dd>{equipmentGroupEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.equipmentGroup.description">Description</Translate>
            </span>
          </dt>
          <dd>{equipmentGroupEntity.description}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.equipmentGroup.plant">Plant</Translate>
          </dt>
          <dd>{equipmentGroupEntity.plant ? equipmentGroupEntity.plant.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.equipmentGroup.subGroup">Sub Group</Translate>
          </dt>
          <dd>{equipmentGroupEntity.subGroup ? equipmentGroupEntity.subGroup.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/equipment-group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/equipment-group/${equipmentGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EquipmentGroupDetail;
