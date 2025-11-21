import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plant.reducer';

export const PlantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const plantEntity = useAppSelector(state => state.plant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plantDetailsHeading">
          <Translate contentKey="thermographyApiApp.plant.detail.title">Plant</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.plant.id">Id</Translate>
            </span>
          </dt>
          <dd>{plantEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="thermographyApiApp.plant.code">Code</Translate>
            </span>
          </dt>
          <dd>{plantEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.plant.name">Name</Translate>
            </span>
          </dt>
          <dd>{plantEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.plant.description">Description</Translate>
            </span>
          </dt>
          <dd>{plantEntity.description}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="thermographyApiApp.plant.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{plantEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="thermographyApiApp.plant.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{plantEntity.longitude}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="thermographyApiApp.plant.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>{plantEntity.startDate ? <TextFormat value={plantEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.plant.company">Company</Translate>
          </dt>
          <dd>{plantEntity.company ? plantEntity.company.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.plant.businessUnit">Business Unit</Translate>
          </dt>
          <dd>{plantEntity.businessUnit ? plantEntity.businessUnit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/plant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plant/${plantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlantDetail;
