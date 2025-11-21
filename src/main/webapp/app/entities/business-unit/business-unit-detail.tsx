import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './business-unit.reducer';

export const BusinessUnitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const businessUnitEntity = useAppSelector(state => state.businessUnit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="businessUnitDetailsHeading">
          <Translate contentKey="thermographyApiApp.businessUnit.detail.title">BusinessUnit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.businessUnit.id">Id</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="thermographyApiApp.businessUnit.code">Code</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.businessUnit.name">Name</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.businessUnit.description">Description</Translate>
            </span>
          </dt>
          <dd>{businessUnitEntity.description}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.businessUnit.company">Company</Translate>
          </dt>
          <dd>{businessUnitEntity.company ? businessUnitEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/business-unit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/business-unit/${businessUnitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BusinessUnitDetail;
