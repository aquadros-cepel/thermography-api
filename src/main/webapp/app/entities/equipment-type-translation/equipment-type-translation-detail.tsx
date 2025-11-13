import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './equipment-type-translation.reducer';

export const EquipmentTypeTranslationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const equipmentTypeTranslationEntity = useAppSelector(state => state.equipmentTypeTranslation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="equipmentTypeTranslationDetailsHeading">
          <Translate contentKey="thermographyApiApp.equipmentTypeTranslation.detail.title">EquipmentTypeTranslation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.equipmentTypeTranslation.id">Id</Translate>
            </span>
          </dt>
          <dd>{equipmentTypeTranslationEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="thermographyApiApp.equipmentTypeTranslation.code">Code</Translate>
            </span>
          </dt>
          <dd>{equipmentTypeTranslationEntity.code}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="thermographyApiApp.equipmentTypeTranslation.language">Language</Translate>
            </span>
          </dt>
          <dd>{equipmentTypeTranslationEntity.language}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.equipmentTypeTranslation.name">Name</Translate>
            </span>
          </dt>
          <dd>{equipmentTypeTranslationEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/equipment-type-translation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/equipment-type-translation/${equipmentTypeTranslationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EquipmentTypeTranslationDetail;
