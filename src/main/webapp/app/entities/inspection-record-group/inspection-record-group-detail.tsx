import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inspection-record-group.reducer';

export const InspectionRecordGroupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inspectionRecordGroupEntity = useAppSelector(state => state.inspectionRecordGroup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inspectionRecordGroupDetailsHeading">
          <Translate contentKey="thermographyApiApp.inspectionRecordGroup.detail.title">InspectionRecordGroup</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.inspectionRecordGroup.id">Id</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordGroupEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="thermographyApiApp.inspectionRecordGroup.code">Code</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordGroupEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="thermographyApiApp.inspectionRecordGroup.name">Name</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordGroupEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="thermographyApiApp.inspectionRecordGroup.description">Description</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordGroupEntity.description}</dd>
          <dt>
            <span id="orderIndex">
              <Translate contentKey="thermographyApiApp.inspectionRecordGroup.orderIndex">Order Index</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordGroupEntity.orderIndex}</dd>
          <dt>
            <span id="finished">
              <Translate contentKey="thermographyApiApp.inspectionRecordGroup.finished">Finished</Translate>
            </span>
          </dt>
          <dd>{inspectionRecordGroupEntity.finished ? 'true' : 'false'}</dd>
          <dt>
            <span id="finishedAt">
              <Translate contentKey="thermographyApiApp.inspectionRecordGroup.finishedAt">Finished At</Translate>
            </span>
          </dt>
          <dd>
            {inspectionRecordGroupEntity.finishedAt ? (
              <TextFormat value={inspectionRecordGroupEntity.finishedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRecordGroup.inspectionRecord">Inspection Record</Translate>
          </dt>
          <dd>{inspectionRecordGroupEntity.inspectionRecord ? inspectionRecordGroupEntity.inspectionRecord.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.inspectionRecordGroup.parentGroup">Parent Group</Translate>
          </dt>
          <dd>{inspectionRecordGroupEntity.parentGroup ? inspectionRecordGroupEntity.parentGroup.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inspection-record-group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inspection-record-group/${inspectionRecordGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InspectionRecordGroupDetail;
