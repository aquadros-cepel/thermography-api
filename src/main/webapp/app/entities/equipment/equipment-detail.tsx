import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './equipment.reducer';

export const EquipmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const equipmentEntity = useAppSelector(state => state.equipment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="equipmentDetailsHeading">
          <Translate contentKey="thermographyApiApp.equipment.detail.title">Equipment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.equipment.id">Id</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="thermographyApiApp.equipment.code">Code</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.equipment.name">Name</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.equipment.description">Description</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.description}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="thermographyApiApp.equipment.type">Type</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.type}</dd>
          <dt>
            <span id="manufacturer">
              <Translate contentKey="thermographyApiApp.equipment.manufacturer">Manufacturer</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.manufacturer}</dd>
          <dt>
            <span id="model">
              <Translate contentKey="thermographyApiApp.equipment.model">Model</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.model}</dd>
          <dt>
            <span id="serialNumber">
              <Translate contentKey="thermographyApiApp.equipment.serialNumber">Serial Number</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.serialNumber}</dd>
          <dt>
            <span id="voltageClass">
              <Translate contentKey="thermographyApiApp.equipment.voltageClass">Voltage Class</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.voltageClass}</dd>
          <dt>
            <span id="phaseType">
              <Translate contentKey="thermographyApiApp.equipment.phaseType">Phase Type</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.phaseType}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="thermographyApiApp.equipment.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {equipmentEntity.startDate ? <TextFormat value={equipmentEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="thermographyApiApp.equipment.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="thermographyApiApp.equipment.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{equipmentEntity.longitude}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.equipment.plant">Plant</Translate>
          </dt>
          <dd>{equipmentEntity.plant ? equipmentEntity.plant.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.equipment.group">Group</Translate>
          </dt>
          <dd>{equipmentEntity.group ? equipmentEntity.group.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.equipment.components">Components</Translate>
          </dt>
          <dd>
            {equipmentEntity.components
              ? equipmentEntity.components.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {equipmentEntity.components && i === equipmentEntity.components.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/equipment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/equipment/${equipmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EquipmentDetail;
