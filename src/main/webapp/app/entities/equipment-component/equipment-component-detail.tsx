import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './equipment-component.reducer';

export const EquipmentComponentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const equipmentComponentEntity = useAppSelector(state => state.equipmentComponent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="equipmentComponentDetailsHeading">
          <Translate contentKey="thermographyApiApp.equipmentComponent.detail.title">EquipmentComponent</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.equipmentComponent.id">Id</Translate>
            </span>
          </dt>
          <dd>{equipmentComponentEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.equipmentComponent.name">Name</Translate>
            </span>
          </dt>
          <dd>{equipmentComponentEntity.name}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="thermographyApiApp.equipmentComponent.title">Title</Translate>
            </span>
          </dt>
          <dd>{equipmentComponentEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.equipmentComponent.description">Description</Translate>
            </span>
          </dt>
          <dd>{equipmentComponentEntity.description}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.equipmentComponent.componentTemperatureLimits">
              Component Temperature Limits
            </Translate>
          </dt>
          <dd>{equipmentComponentEntity.componentTemperatureLimits ? equipmentComponentEntity.componentTemperatureLimits.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.equipmentComponent.equipments">Equipments</Translate>
          </dt>
          <dd>
            {equipmentComponentEntity.equipments
              ? equipmentComponentEntity.equipments.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {equipmentComponentEntity.equipments && i === equipmentComponentEntity.equipments.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/equipment-component" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/equipment-component/${equipmentComponentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EquipmentComponentDetail;
