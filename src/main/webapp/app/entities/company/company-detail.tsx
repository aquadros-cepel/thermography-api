import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, byteSize, openFile } from 'react-jhipster';
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
            <span id="code">
              <Translate contentKey="thermographyApiApp.company.code">Code</Translate>
            </span>
          </dt>
          <dd>{companyEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.company.name">Name</Translate>
            </span>
          </dt>
          <dd>{companyEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.company.description">Description</Translate>
            </span>
          </dt>
          <dd>{companyEntity.description}</dd>
          <dt>
            <span id="logo">
              <Translate contentKey="thermographyApiApp.company.logo">Logo</Translate>
            </span>
          </dt>
          <dd>
            {companyEntity.logo ? (
              <div>
                {companyEntity.logoContentType ? (
                  <a onClick={openFile(companyEntity.logoContentType, companyEntity.logo)}>
                    <img src={`data:${companyEntity.logoContentType};base64,${companyEntity.logo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {companyEntity.logoContentType}, {byteSize(companyEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="address">
              <Translate contentKey="thermographyApiApp.company.address">Address</Translate>
            </span>
          </dt>
          <dd>{companyEntity.address}</dd>
          <dt>
            <span id="zipCode">
              <Translate contentKey="thermographyApiApp.company.zipCode">Zip Code</Translate>
            </span>
          </dt>
          <dd>{companyEntity.zipCode}</dd>
          <dt>
            <span id="street">
              <Translate contentKey="thermographyApiApp.company.street">Street</Translate>
            </span>
          </dt>
          <dd>{companyEntity.street}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="thermographyApiApp.company.number">Number</Translate>
            </span>
          </dt>
          <dd>{companyEntity.number}</dd>
          <dt>
            <span id="complement">
              <Translate contentKey="thermographyApiApp.company.complement">Complement</Translate>
            </span>
          </dt>
          <dd>{companyEntity.complement}</dd>
          <dt>
            <span id="neighborhood">
              <Translate contentKey="thermographyApiApp.company.neighborhood">Neighborhood</Translate>
            </span>
          </dt>
          <dd>{companyEntity.neighborhood}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="thermographyApiApp.company.city">City</Translate>
            </span>
          </dt>
          <dd>{companyEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="thermographyApiApp.company.state">State</Translate>
            </span>
          </dt>
          <dd>{companyEntity.state}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="thermographyApiApp.company.country">Country</Translate>
            </span>
          </dt>
          <dd>{companyEntity.country}</dd>
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
