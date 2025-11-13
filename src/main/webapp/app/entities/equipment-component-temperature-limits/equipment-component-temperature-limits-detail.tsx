import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './equipment-component-temperature-limits.reducer';

export const EquipmentComponentTemperatureLimitsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const equipmentComponentTemperatureLimitsEntity = useAppSelector(state => state.equipmentComponentTemperatureLimits.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="equipmentComponentTemperatureLimitsDetailsHeading">
          <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.detail.title">
            EquipmentComponentTemperatureLimits
          </Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.id">Id</Translate>
            </span>
          </dt>
          <dd>{equipmentComponentTemperatureLimitsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.name">Name</Translate>
            </span>
          </dt>
          <dd>{equipmentComponentTemperatureLimitsEntity.name}</dd>
          <dt>
            <span id="normal">
              <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.normal">Normal</Translate>
            </span>
          </dt>
          <dd>{equipmentComponentTemperatureLimitsEntity.normal}</dd>
          <dt>
            <span id="lowRisk">
              <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.lowRisk">Low Risk</Translate>
            </span>
          </dt>
          <dd>{equipmentComponentTemperatureLimitsEntity.lowRisk}</dd>
          <dt>
            <span id="mediumRisk">
              <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.mediumRisk">Medium Risk</Translate>
            </span>
          </dt>
          <dd>{equipmentComponentTemperatureLimitsEntity.mediumRisk}</dd>
          <dt>
            <span id="highRisk">
              <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.highRisk">High Risk</Translate>
            </span>
          </dt>
          <dd>{equipmentComponentTemperatureLimitsEntity.highRisk}</dd>
          <dt>
            <span id="imminentHighRisk">
              <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.imminentHighRisk">Imminent High Risk</Translate>
            </span>
          </dt>
          <dd>{equipmentComponentTemperatureLimitsEntity.imminentHighRisk}</dd>
        </dl>
        <Button tag={Link} to="/equipment-component-temperature-limits" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/equipment-component-temperature-limits/${equipmentComponentTemperatureLimitsEntity.id}/edit`}
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

export default EquipmentComponentTemperatureLimitsDetail;
