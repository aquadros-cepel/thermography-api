import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './inspection-record-group-equipment.reducer';

export const InspectionRecordGroupEquipment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const inspectionRecordGroupEquipmentList = useAppSelector(state => state.inspectionRecordGroupEquipment.entities);
  const loading = useAppSelector(state => state.inspectionRecordGroupEquipment.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="inspection-record-group-equipment-heading" data-cy="InspectionRecordGroupEquipmentHeading">
        <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.home.title">Inspection Record Group Equipments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/inspection-record-group-equipment/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.home.createLabel">
              Create new Inspection Record Group Equipment
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {inspectionRecordGroupEquipmentList && inspectionRecordGroupEquipmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('orderIndex')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.orderIndex">Order Index</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('orderIndex')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.inspectionRecordGroup">
                    Inspection Record Group
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.equipment">Equipment</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inspectionRecordGroupEquipmentList.map((inspectionRecordGroupEquipment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/inspection-record-group-equipment/${inspectionRecordGroupEquipment.id}`}
                      color="link"
                      size="sm"
                    >
                      {inspectionRecordGroupEquipment.id}
                    </Button>
                  </td>
                  <td>{inspectionRecordGroupEquipment.orderIndex}</td>
                  <td>
                    <Translate contentKey={`thermographyApiApp.EquipmentInspectionStatus.${inspectionRecordGroupEquipment.status}`} />
                  </td>
                  <td>
                    {inspectionRecordGroupEquipment.inspectionRecordGroup ? (
                      <Link to={`/inspection-record-group/${inspectionRecordGroupEquipment.inspectionRecordGroup.id}`}>
                        {inspectionRecordGroupEquipment.inspectionRecordGroup.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inspectionRecordGroupEquipment.equipment ? (
                      <Link to={`/equipment/${inspectionRecordGroupEquipment.equipment.id}`}>
                        {inspectionRecordGroupEquipment.equipment.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/inspection-record-group-equipment/${inspectionRecordGroupEquipment.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/inspection-record-group-equipment/${inspectionRecordGroupEquipment.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/inspection-record-group-equipment/${inspectionRecordGroupEquipment.id}/delete`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="thermographyApiApp.inspectionRecordGroupEquipment.home.notFound">
                No Inspection Record Group Equipments found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default InspectionRecordGroupEquipment;
