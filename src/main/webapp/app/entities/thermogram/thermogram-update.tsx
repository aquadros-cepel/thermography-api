import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getEquipment } from 'app/entities/equipment/equipment.reducer';
import { getEntities as getUserInfos } from 'app/entities/user-info/user-info.reducer';
import { createEntity, getEntity, reset, updateEntity } from './thermogram.reducer';

export const ThermogramUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const equipment = useAppSelector(state => state.equipment.entities);
  const userInfos = useAppSelector(state => state.userInfo.entities);
  const thermogramEntity = useAppSelector(state => state.thermogram.entity);
  const loading = useAppSelector(state => state.thermogram.loading);
  const updating = useAppSelector(state => state.thermogram.updating);
  const updateSuccess = useAppSelector(state => state.thermogram.updateSuccess);

  const handleClose = () => {
    navigate('/thermogram');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEquipment({}));
    dispatch(getUserInfos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.minTemp !== undefined && typeof values.minTemp !== 'number') {
      values.minTemp = Number(values.minTemp);
    }
    if (values.avgTemp !== undefined && typeof values.avgTemp !== 'number') {
      values.avgTemp = Number(values.avgTemp);
    }
    if (values.maxTemp !== undefined && typeof values.maxTemp !== 'number') {
      values.maxTemp = Number(values.maxTemp);
    }
    if (values.emissivity !== undefined && typeof values.emissivity !== 'number') {
      values.emissivity = Number(values.emissivity);
    }
    if (values.subjectDistance !== undefined && typeof values.subjectDistance !== 'number') {
      values.subjectDistance = Number(values.subjectDistance);
    }
    if (values.atmosphericTemp !== undefined && typeof values.atmosphericTemp !== 'number') {
      values.atmosphericTemp = Number(values.atmosphericTemp);
    }
    if (values.reflectedTemp !== undefined && typeof values.reflectedTemp !== 'number') {
      values.reflectedTemp = Number(values.reflectedTemp);
    }
    if (values.relativeHumidity !== undefined && typeof values.relativeHumidity !== 'number') {
      values.relativeHumidity = Number(values.relativeHumidity);
    }
    if (values.maxTempRoi !== undefined && typeof values.maxTempRoi !== 'number') {
      values.maxTempRoi = Number(values.maxTempRoi);
    }
    values.createdAt = convertDateTimeToServer(values.createdAt);
    if (values.latitude !== undefined && typeof values.latitude !== 'number') {
      values.latitude = Number(values.latitude);
    }
    if (values.longitude !== undefined && typeof values.longitude !== 'number') {
      values.longitude = Number(values.longitude);
    }

    const entity = {
      ...thermogramEntity,
      ...values,
      equipment: equipment.find(it => it.id.toString() === values.equipment?.toString()),
      createdBy: userInfos.find(it => it.id.toString() === values.createdBy?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdAt: displayDefaultDateTime(),
        }
      : {
          ...thermogramEntity,
          createdAt: convertDateTimeFromServer(thermogramEntity.createdAt),
          equipment: thermogramEntity?.equipment?.id,
          createdBy: thermogramEntity?.createdBy?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.thermogram.home.createOrEditLabel" data-cy="ThermogramCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.thermogram.home.createOrEditLabel">Create or edit a Thermogram</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="thermogram-id"
                  label={translate('thermographyApiApp.thermogram.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.imagePath')}
                id="thermogram-imagePath"
                name="imagePath"
                data-cy="imagePath"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.audioPath')}
                id="thermogram-audioPath"
                name="audioPath"
                data-cy="audioPath"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.imageRefPath')}
                id="thermogram-imageRefPath"
                name="imageRefPath"
                data-cy="imageRefPath"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.minTemp')}
                id="thermogram-minTemp"
                name="minTemp"
                data-cy="minTemp"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.avgTemp')}
                id="thermogram-avgTemp"
                name="avgTemp"
                data-cy="avgTemp"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.maxTemp')}
                id="thermogram-maxTemp"
                name="maxTemp"
                data-cy="maxTemp"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.emissivity')}
                id="thermogram-emissivity"
                name="emissivity"
                data-cy="emissivity"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.subjectDistance')}
                id="thermogram-subjectDistance"
                name="subjectDistance"
                data-cy="subjectDistance"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.atmosphericTemp')}
                id="thermogram-atmosphericTemp"
                name="atmosphericTemp"
                data-cy="atmosphericTemp"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.reflectedTemp')}
                id="thermogram-reflectedTemp"
                name="reflectedTemp"
                data-cy="reflectedTemp"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.relativeHumidity')}
                id="thermogram-relativeHumidity"
                name="relativeHumidity"
                data-cy="relativeHumidity"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.cameraLens')}
                id="thermogram-cameraLens"
                name="cameraLens"
                data-cy="cameraLens"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.cameraModel')}
                id="thermogram-cameraModel"
                name="cameraModel"
                data-cy="cameraModel"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.imageResolution')}
                id="thermogram-imageResolution"
                name="imageResolution"
                data-cy="imageResolution"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.selectedRoiId')}
                id="thermogram-selectedRoiId"
                name="selectedRoiId"
                data-cy="selectedRoiId"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.maxTempRoi')}
                id="thermogram-maxTempRoi"
                name="maxTempRoi"
                data-cy="maxTempRoi"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.createdAt')}
                id="thermogram-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.latitude')}
                id="thermogram-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermogram.longitude')}
                id="thermogram-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField
                id="thermogram-equipment"
                name="equipment"
                data-cy="equipment"
                label={translate('thermographyApiApp.thermogram.equipment')}
                type="select"
                required
              >
                <option value="" key="0" />
                {equipment
                  ? equipment.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="thermogram-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('thermographyApiApp.thermogram.createdBy')}
                type="select"
                required
              >
                <option value="" key="0" />
                {userInfos
                  ? userInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/thermogram" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ThermogramUpdate;
