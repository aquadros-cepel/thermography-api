import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './company.reducer';

export const CompanyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const companyEntity = useAppSelector(state => state.company.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyDetailsHeading">
          <Translate contentKey="thermographyApiApp.company.detail.title">Company</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.company.id">Id</Translate>
            </span>
          </dt>
          <dd>{companyEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.company.name">Name</Translate>
            </span>
          </dt>
          <dd>{companyEntity.name}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="thermographyApiApp.company.title">Title</Translate>
            </span>
          </dt>
          <dd>{companyEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.company.description">Description</Translate>
            </span>
          </dt>
          <dd>{companyEntity.description}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="thermographyApiApp.company.address">Address</Translate>
            </span>
          </dt>
          <dd>{companyEntity.address}</dd>
          <dt>
            <span id="primaryPhoneNumber">
              <Translate contentKey="thermographyApiApp.company.primaryPhoneNumber">Primary Phone Number</Translate>
            </span>
          </dt>
          <dd>{companyEntity.primaryPhoneNumber}</dd>
          <dt>
            <span id="secondaryPhoneNumber">
              <Translate contentKey="thermographyApiApp.company.secondaryPhoneNumber">Secondary Phone Number</Translate>
            </span>
          </dt>
          <dd>{companyEntity.secondaryPhoneNumber}</dd>
          <dt>
            <span id="taxIdNumber">
              <Translate contentKey="thermographyApiApp.company.taxIdNumber">Tax Id Number</Translate>
            </span>
          </dt>
          <dd>{companyEntity.taxIdNumber}</dd>
        </dl>
        <Button tag={Link} to="/company" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company/${companyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyDetail;
