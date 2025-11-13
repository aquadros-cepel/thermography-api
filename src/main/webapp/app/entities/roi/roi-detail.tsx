import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './roi.reducer';

export const ROIDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rOIEntity = useAppSelector(state => state.rOI.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rOIDetailsHeading">
          <Translate contentKey="thermographyApiApp.rOI.detail.title">ROI</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.rOI.id">Id</Translate>
            </span>
          </dt>
          <dd>{rOIEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="thermographyApiApp.rOI.type">Type</Translate>
            </span>
          </dt>
          <dd>{rOIEntity.type}</dd>
          <dt>
            <span id="label">
              <Translate contentKey="thermographyApiApp.rOI.label">Label</Translate>
            </span>
          </dt>
          <dd>{rOIEntity.label}</dd>
          <dt>
            <span id="maxTemp">
              <Translate contentKey="thermographyApiApp.rOI.maxTemp">Max Temp</Translate>
            </span>
          </dt>
          <dd>{rOIEntity.maxTemp}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.rOI.thermogram">Thermogram</Translate>
          </dt>
          <dd>{rOIEntity.thermogram ? rOIEntity.thermogram.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/roi" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/roi/${rOIEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ROIDetail;
