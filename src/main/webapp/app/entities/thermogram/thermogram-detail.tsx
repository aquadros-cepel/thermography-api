import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './thermogram.reducer';

export const ThermogramDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const thermogramEntity = useAppSelector(state => state.thermogram.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="thermogramDetailsHeading">
          <Translate contentKey="thermographyApiApp.thermogram.detail.title">Thermogram</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="thermographyApiApp.thermogram.id">Id</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.id}</dd>
          <dt>
            <span id="imagePath">
              <Translate contentKey="thermographyApiApp.thermogram.imagePath">Image Path</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.imagePath}</dd>
          <dt>
            <span id="audioPath">
              <Translate contentKey="thermographyApiApp.thermogram.audioPath">Audio Path</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.audioPath}</dd>
          <dt>
            <span id="imageRefPath">
              <Translate contentKey="thermographyApiApp.thermogram.imageRefPath">Image Ref Path</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.imageRefPath}</dd>
          <dt>
            <span id="minTemp">
              <Translate contentKey="thermographyApiApp.thermogram.minTemp">Min Temp</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.minTemp}</dd>
          <dt>
            <span id="avgTemp">
              <Translate contentKey="thermographyApiApp.thermogram.avgTemp">Avg Temp</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.avgTemp}</dd>
          <dt>
            <span id="maxTemp">
              <Translate contentKey="thermographyApiApp.thermogram.maxTemp">Max Temp</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.maxTemp}</dd>
          <dt>
            <span id="emissivity">
              <Translate contentKey="thermographyApiApp.thermogram.emissivity">Emissivity</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.emissivity}</dd>
          <dt>
            <span id="subjectDistance">
              <Translate contentKey="thermographyApiApp.thermogram.subjectDistance">Subject Distance</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.subjectDistance}</dd>
          <dt>
            <span id="atmosphericTemp">
              <Translate contentKey="thermographyApiApp.thermogram.atmosphericTemp">Atmospheric Temp</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.atmosphericTemp}</dd>
          <dt>
            <span id="reflectedTemp">
              <Translate contentKey="thermographyApiApp.thermogram.reflectedTemp">Reflected Temp</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.reflectedTemp}</dd>
          <dt>
            <span id="relativeHumidity">
              <Translate contentKey="thermographyApiApp.thermogram.relativeHumidity">Relative Humidity</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.relativeHumidity}</dd>
          <dt>
            <span id="cameraLens">
              <Translate contentKey="thermographyApiApp.thermogram.cameraLens">Camera Lens</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.cameraLens}</dd>
          <dt>
            <span id="cameraModel">
              <Translate contentKey="thermographyApiApp.thermogram.cameraModel">Camera Model</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.cameraModel}</dd>
          <dt>
            <span id="imageResolution">
              <Translate contentKey="thermographyApiApp.thermogram.imageResolution">Image Resolution</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.imageResolution}</dd>
          <dt>
            <span id="selectedRoiId">
              <Translate contentKey="thermographyApiApp.thermogram.selectedRoiId">Selected Roi Id</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.selectedRoiId}</dd>
          <dt>
            <span id="maxTempRoi">
              <Translate contentKey="thermographyApiApp.thermogram.maxTempRoi">Max Temp Roi</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.maxTempRoi}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="thermographyApiApp.thermogram.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {thermogramEntity.createdAt ? <TextFormat value={thermogramEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="thermographyApiApp.thermogram.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="thermographyApiApp.thermogram.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{thermogramEntity.longitude}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermogram.equipment">Equipment</Translate>
          </dt>
          <dd>{thermogramEntity.equipment ? thermogramEntity.equipment.id : ''}</dd>
          <dt>
            <Translate contentKey="thermographyApiApp.thermogram.createdBy">Created By</Translate>
          </dt>
          <dd>{thermogramEntity.createdBy ? thermogramEntity.createdBy.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/thermogram" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/thermogram/${thermogramEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ThermogramDetail;
