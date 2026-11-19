import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';

import { getEntities as getEquipmentComponents } from 'app/entities/equipment-component/equipment-component.reducer';
import { getEntities as getEquipment } from 'app/entities/equipment/equipment.reducer';
import { getEntities as getInspectionRecords } from 'app/entities/inspection-record/inspection-record.reducer';
import { getEntities as getPlants } from 'app/entities/plant/plant.reducer';
import { getEntities as getThermograms } from 'app/entities/thermogram/thermogram.reducer';
import { getEntities as getThermographicRecords } from 'app/entities/thermographic-record/thermographic-record.reducer';
import { getEntities as getUserInfos } from 'app/entities/user-info/user-info.reducer';
import { ConditionType } from 'app/shared/model/enumerations/condition-type.model';
import { PhaseType } from 'app/shared/model/enumerations/phase-type.model';
import { ThermographicRecordType } from 'app/shared/model/enumerations/thermographic-record-type.model';
import { createEntity, getEntity, reset, updateEntity } from './thermographic-record.reducer';

export const ThermographicRecordUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plants = useAppSelector(state => state.plant.entities);
  const inspectionRecords = useAppSelector(state => state.inspectionRecord.entities);
  const equipment = useAppSelector(state => state.equipment.entities);
  const equipmentComponents = useAppSelector(state => state.equipmentComponent.entities);
  const userInfos = useAppSelector(state => state.userInfo.entities);
  const thermograms = useAppSelector(state => state.thermogram.entities);
  const ThermographicRecords = useAppSelector(state => state.ThermographicRecord.entities);
  const ThermographicRecordEntity = useAppSelector(state => state.ThermographicRecord.entity);
  const loading = useAppSelector(state => state.ThermographicRecord.loading);
  const updating = useAppSelector(state => state.ThermographicRecord.updating);
  const updateSuccess = useAppSelector(state => state.ThermographicRecord.updateSuccess);
  const ThermographicRecordTypeValues = Object.keys(ThermographicRecordType);
  const conditionTypeValues = Object.keys(ConditionType);
  const phaseTypeValues = Object.keys(PhaseType);

  const handleClose = () => {
    navigate('/thermographic-record');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlants({}));
    dispatch(getInspectionRecords({}));
    dispatch(getEquipment({}));
    dispatch(getEquipmentComponents({}));
    dispatch(getUserInfos({}));
    dispatch(getThermograms({}));
    dispatch(getThermographicRecords({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createdAt = convertDateTimeToServer(values.createdAt);
    if (values.deltaT !== undefined && typeof values.deltaT !== 'number') {
      values.deltaT = Number(values.deltaT);
    }
    if (values.periodicity !== undefined && typeof values.periodicity !== 'number') {
      values.periodicity = Number(values.periodicity);
    }
    values.finishedAt = convertDateTimeToServer(values.finishedAt);
    if (values.current !== undefined && typeof values.current !== 'number') {
      values.current = Number(values.current);
    }
    if (values.voltage !== undefined && typeof values.voltage !== 'number') {
      values.voltage = Number(values.voltage);
    }
    if (values.windSpeed !== undefined && typeof values.windSpeed !== 'number') {
      values.windSpeed = Number(values.windSpeed);
    }

    const entity = {
      ...ThermographicRecordEntity,
      ...values,
      plant: plants.find(it => it.id.toString() === values.plant?.toString()),
      route: inspectionRecords.find(it => it.id.toString() === values.route?.toString()),
      equipment: equipment.find(it => it.id.toString() === values.equipment?.toString()),
      component: equipmentComponents.find(it => it.id.toString() === values.component?.toString()),
      createdBy: userInfos.find(it => it.id.toString() === values.createdBy?.toString()),
      finishedBy: userInfos.find(it => it.id.toString() === values.finishedBy?.toString()),
      thermogram: thermograms.find(it => it.id.toString() === values.thermogram?.toString()),
      thermogramRef: thermograms.find(it => it.id.toString() === values.thermogramRef?.toString()),
      parentRecord: ThermographicRecords.find(it => it.id.toString() === values.parentRecord?.toString()),
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
          finishedAt: displayDefaultDateTime(),
        }
      : {
          type: 'NO_ANOMALY',
          condition: 'NORMAL',
          phase: 'PHASE_A',
          ...ThermographicRecordEntity,
          createdAt: convertDateTimeFromServer(ThermographicRecordEntity.createdAt),
          finishedAt: convertDateTimeFromServer(ThermographicRecordEntity.finishedAt),
          plant: ThermographicRecordEntity?.plant?.id,
          route: ThermographicRecordEntity?.route?.id,
          equipment: ThermographicRecordEntity?.equipment?.id,
          component: ThermographicRecordEntity?.component?.id,
          createdBy: ThermographicRecordEntity?.createdBy?.id,
          finishedBy: ThermographicRecordEntity?.finishedBy?.id,
          thermogram: ThermographicRecordEntity?.thermogram?.id,
          thermogramRef: ThermographicRecordEntity?.thermogramRef?.id,
          parentRecord: ThermographicRecordEntity?.parentRecord?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="thermographyApiApp.ThermographicRecord.home.createOrEditLabel" data-cy="ThermographicRecordCreateUpdateHeading">
            <Translate contentKey="thermographyApiApp.ThermographicRecord.home.createOrEditLabel">
              Create or edit a ThermographicRecord
            </Translate>
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
                  id="thermographic-record-id"
                  label={translate('thermographyApiApp.ThermographicRecord.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.name')}
                id="thermographic-record-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.type')}
                id="thermographic-record-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {ThermographicRecordTypeValues.map(thermographicRecordType => (
                  <option value={thermographicRecordType} key={thermographicRecordType}>
                    {translate(`thermographyApiApp.ThermographicRecordType.${thermographicRecordType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.serviceOrder')}
                id="thermographic-record-serviceOrder"
                name="serviceOrder"
                data-cy="serviceOrder"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.createdAt')}
                id="thermographic-record-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.analysisDescription')}
                id="thermographic-record-analysisDescription"
                name="analysisDescription"
                data-cy="analysisDescription"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.condition')}
                id="thermographic-record-condition"
                name="condition"
                data-cy="condition"
                type="select"
              >
                {conditionTypeValues.map(conditionType => (
                  <option value={conditionType} key={conditionType}>
                    {translate(`thermographyApiApp.ConditionType.${conditionType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.deltaT')}
                id="thermographic-record-deltaT"
                name="deltaT"
                data-cy="deltaT"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.periodicity')}
                id="thermographic-record-periodicity"
                name="periodicity"
                data-cy="periodicity"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.deadlineExecution')}
                id="thermographic-record-deadlineExecution"
                name="deadlineExecution"
                data-cy="deadlineExecution"
                type="date"
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.nextMonitoring')}
                id="thermographic-record-nextMonitoring"
                name="nextMonitoring"
                data-cy="nextMonitoring"
                type="date"
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.recommendations')}
                id="thermographic-record-recommendations"
                name="recommendations"
                data-cy="recommendations"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.finished')}
                id="thermographic-record-finished"
                name="finished"
                data-cy="finished"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.finishedAt')}
                id="thermographic-record-finishedAt"
                name="finishedAt"
                data-cy="finishedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.current')}
                id="thermographic-record-current"
                name="current"
                data-cy="current"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.voltage')}
                id="thermographic-record-voltage"
                name="voltage"
                data-cy="voltage"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.windSpeed')}
                id="thermographic-record-windSpeed"
                name="windSpeed"
                data-cy="windSpeed"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.ThermographicRecord.phase')}
                id="thermographic-record-phase"
                name="phase"
                data-cy="phase"
                type="select"
              >
                {phaseTypeValues.map(phaseType => (
                  <option value={phaseType} key={phaseType}>
                    {translate(`thermographyApiApp.PhaseType.${phaseType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="thermographic-record-plant"
                name="plant"
                data-cy="plant"
                label={translate('thermographyApiApp.ThermographicRecord.plant')}
                type="select"
                required
              >
                <option value="" key="0" />
                {plants
                  ? plants.map(otherEntity => (
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
                id="thermographic-record-route"
                name="route"
                data-cy="route"
                label={translate('thermographyApiApp.ThermographicRecord.route')}
                type="select"
              >
                <option value="" key="0" />
                {inspectionRecords
                  ? inspectionRecords.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="thermographic-record-equipment"
                name="equipment"
                data-cy="equipment"
                label={translate('thermographyApiApp.ThermographicRecord.equipment')}
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
                id="thermographic-record-component"
                name="component"
                data-cy="component"
                label={translate('thermographyApiApp.ThermographicRecord.component')}
                type="select"
              >
                <option value="" key="0" />
                {equipmentComponents
                  ? equipmentComponents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="thermographic-record-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('thermographyApiApp.ThermographicRecord.createdBy')}
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
              <ValidatedField
                id="thermographic-record-finishedBy"
                name="finishedBy"
                data-cy="finishedBy"
                label={translate('thermographyApiApp.ThermographicRecord.finishedBy')}
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
              <ValidatedField
                id="thermographic-record-thermogram"
                name="thermogram"
                data-cy="thermogram"
                label={translate('thermographyApiApp.ThermographicRecord.thermogram')}
                type="select"
                required
              >
                <option value="" key="0" />
                {thermograms
                  ? thermograms.map(otherEntity => (
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
                id="thermographic-record-thermogramRef"
                name="thermogramRef"
                data-cy="thermogramRef"
                label={translate('thermographyApiApp.ThermographicRecord.thermogramRef')}
                type="select"
              >
                <option value="" key="0" />
                {thermograms
                  ? thermograms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="thermographic-record-parentRecord"
                name="parentRecord"
                data-cy="parentRecord"
                label={translate('thermographyApiApp.ThermographicRecord.parentRecord')}
                type="select"
              >
                <option value="" key="0" />
                {ThermographicRecords
                  ? ThermographicRecords.filter(otherEntity => otherEntity.id !== ThermographicRecordEntity?.id).map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/thermographic-record" replace color="info">
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

export default ThermographicRecordUpdate;
