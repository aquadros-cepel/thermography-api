import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-info.reducer';

export const UserInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userInfoEntity = useAppSelector(state => state.userInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userInfoDetailsHeading">
          <Translate contentKey="thermographyApiApp.userInfo.detail.title">UserInfo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.userInfo.id">Id</Translate>
            </span>
          </dt>
          <dd>{userInfoEntity.id}</dd>
          <dt>
            <span id="position">
              <Translate contentKey="thermographyApiApp.userInfo.position">Position</Translate>
            </span>
          </dt>
          <dd>{userInfoEntity.position}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="thermographyApiApp.userInfo.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{userInfoEntity.phoneNumber}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.userInfo.user">User</Translate>
          </dt>
          <dd>{userInfoEntity.user ? userInfoEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.userInfo.company">Company</Translate>
          </dt>
          <dd>{userInfoEntity.company ? userInfoEntity.company.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-info/${userInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserInfoDetail;
