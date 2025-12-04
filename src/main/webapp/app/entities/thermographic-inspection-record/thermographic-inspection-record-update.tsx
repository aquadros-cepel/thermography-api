import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getPlants } from 'app/entities/plant/plant.reducer';
import { getEntities as getInspectionRecords } from 'app/entities/inspection-record/inspection-record.reducer';
import { getEntities as getEquipment } from 'app/entities/equipment/equipment.reducer';
import { getEntities as getEquipmentComponents } from 'app/entities/equipment-component/equipment-component.reducer';
import { getEntities as getUserInfos } from 'app/entities/user-info/user-info.reducer';
import { getEntities as getThermograms } from 'app/entities/thermogram/thermogram.reducer';
import { ThermographicInspectionRecordType } from 'app/shared/model/enumerations/thermographic-inspection-record-type.model';
import { ConditionType } from 'app/shared/model/enumerations/condition-type.model';
import { createEntity, getEntity, reset, updateEntity } from './thermographic-inspection-record.reducer';

export const ThermographicInspectionRecordUpdate = () => {
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
  const thermographicInspectionRecordEntity = useAppSelector(state => state.thermographicInspectionRecord.entity);
  const loading = useAppSelector(state => state.thermographicInspectionRecord.loading);
  const updating = useAppSelector(state => state.thermographicInspectionRecord.updating);
  const updateSuccess = useAppSelector(state => state.thermographicInspectionRecord.updateSuccess);
  const thermographicInspectionRecordTypeValues = Object.keys(ThermographicInspectionRecordType);
  const conditionTypeValues = Object.keys(ConditionType);

  const handleClose = () => {
    navigate('/thermographic-inspection-record');
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

    const entity = {
      ...thermographicInspectionRecordEntity,
      ...values,
      plant: plants.find(it => it.id.toString() === values.plant?.toString()),
      route: inspectionRecords.find(it => it.id.toString() === values.route?.toString()),
      equipment: equipment.find(it => it.id.toString() === values.equipment?.toString()),
      component: equipmentComponents.find(it => it.id.toString() === values.component?.toString()),
      createdBy: userInfos.find(it => it.id.toString() === values.createdBy?.toString()),
      finishedBy: userInfos.find(it => it.id.toString() === values.finishedBy?.toString()),
      thermogram: thermograms.find(it => it.id.toString() === values.thermogram?.toString()),
      thermogramRef: thermograms.find(it => it.id.toString() === values.thermogramRef?.toString()),
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
          ...thermographicInspectionRecordEntity,
          createdAt: convertDateTimeFromServer(thermographicInspectionRecordEntity.createdAt),
          finishedAt: convertDateTimeFromServer(thermographicInspectionRecordEntity.finishedAt),
          plant: thermographicInspectionRecordEntity?.plant?.id,
          route: thermographicInspectionRecordEntity?.route?.id,
          equipment: thermographicInspectionRecordEntity?.equipment?.id,
          component: thermographicInspectionRecordEntity?.component?.id,
          createdBy: thermographicInspectionRecordEntity?.createdBy?.id,
          finishedBy: thermographicInspectionRecordEntity?.finishedBy?.id,
          thermogram: thermographicInspectionRecordEntity?.thermogram?.id,
          thermogramRef: thermographicInspectionRecordEntity?.thermogramRef?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="thermographyApiApp.thermographicInspectionRecord.home.createOrEditLabel"
            data-cy="ThermographicInspectionRecordCreateUpdateHeading"
          >
            <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.home.createOrEditLabel">
              Create or edit a ThermographicInspectionRecord
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
                  id="thermographic-inspection-record-id"
                  label={translate('thermographyApiApp.thermographicInspectionRecord.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.name')}
                id="thermographic-inspection-record-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.type')}
                id="thermographic-inspection-record-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {thermographicInspectionRecordTypeValues.map(thermographicInspectionRecordType => (
                  <option value={thermographicInspectionRecordType} key={thermographicInspectionRecordType}>
                    {translate(`thermographyApiApp.ThermographicInspectionRecordType.${thermographicInspectionRecordType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.serviceOrder')}
                id="thermographic-inspection-record-serviceOrder"
                name="serviceOrder"
                data-cy="serviceOrder"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.createdAt')}
                id="thermographic-inspection-record-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.analysisDescription')}
                id="thermographic-inspection-record-analysisDescription"
                name="analysisDescription"
                data-cy="analysisDescription"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.condition')}
                id="thermographic-inspection-record-condition"
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
                label={translate('thermographyApiApp.thermographicInspectionRecord.deltaT')}
                id="thermographic-inspection-record-deltaT"
                name="deltaT"
                data-cy="deltaT"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.periodicity')}
                id="thermographic-inspection-record-periodicity"
                name="periodicity"
                data-cy="periodicity"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.deadlineExecution')}
                id="thermographic-inspection-record-deadlineExecution"
                name="deadlineExecution"
                data-cy="deadlineExecution"
                type="date"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.nextMonitoring')}
                id="thermographic-inspection-record-nextMonitoring"
                name="nextMonitoring"
                data-cy="nextMonitoring"
                type="date"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.recommendations')}
                id="thermographic-inspection-record-recommendations"
                name="recommendations"
                data-cy="recommendations"
                type="text"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.finished')}
                id="thermographic-inspection-record-finished"
                name="finished"
                data-cy="finished"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('thermographyApiApp.thermographicInspectionRecord.finishedAt')}
                id="thermographic-inspection-record-finishedAt"
                name="finishedAt"
                data-cy="finishedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="thermographic-inspection-record-plant"
                name="plant"
                data-cy="plant"
                label={translate('thermographyApiApp.thermographicInspectionRecord.plant')}
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
                id="thermographic-inspection-record-route"
                name="route"
                data-cy="route"
                label={translate('thermographyApiApp.thermographicInspectionRecord.route')}
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
                id="thermographic-inspection-record-equipment"
                name="equipment"
                data-cy="equipment"
                label={translate('thermographyApiApp.thermographicInspectionRecord.equipment')}
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
                id="thermographic-inspection-record-component"
                name="component"
                data-cy="component"
                label={translate('thermographyApiApp.thermographicInspectionRecord.component')}
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
                id="thermographic-inspection-record-createdBy"
                name="createdBy"
                data-cy="createdBy"
                label={translate('thermographyApiApp.thermographicInspectionRecord.createdBy')}
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
                id="thermographic-inspection-record-finishedBy"
                name="finishedBy"
                data-cy="finishedBy"
                label={translate('thermographyApiApp.thermographicInspectionRecord.finishedBy')}
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
                id="thermographic-inspection-record-thermogram"
                name="thermogram"
                data-cy="thermogram"
                label={translate('thermographyApiApp.thermographicInspectionRecord.thermogram')}
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
                id="thermographic-inspection-record-thermogramRef"
                name="thermogramRef"
                data-cy="thermogramRef"
                label={translate('thermographyApiApp.thermographicInspectionRecord.thermogramRef')}
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/thermographic-inspection-record"
                replace
                color="info"
              >
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

export default ThermographicInspectionRecordUpdate;
