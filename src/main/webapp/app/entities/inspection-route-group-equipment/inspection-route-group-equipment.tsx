import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './inspection-route-group-equipment.reducer';

export const InspectionRouteGroupEquipment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const inspectionRouteGroupEquipmentList = useAppSelector(state => state.inspectionRouteGroupEquipment.entities);
  const loading = useAppSelector(state => state.inspectionRouteGroupEquipment.loading);

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
      <h2 id="inspection-route-group-equipment-heading" data-cy="InspectionRouteGroupEquipmentHeading">
        <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.home.title">Inspection Route Group Equipments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/inspection-route-group-equipment/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.home.createLabel">
              Create new Inspection Route Group Equipment
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {inspectionRouteGroupEquipmentList && inspectionRouteGroupEquipmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('included')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.included">Included</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('included')} />
                </th>
                <th className="hand" onClick={sort('orderIndex')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.orderIndex">Order Index</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('orderIndex')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.inspectionRouteGroup">
                    Inspection Route Group
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.equipment">Equipment</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inspectionRouteGroupEquipmentList.map((inspectionRouteGroupEquipment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/inspection-route-group-equipment/${inspectionRouteGroupEquipment.id}`} color="link" size="sm">
                      {inspectionRouteGroupEquipment.id}
                    </Button>
                  </td>
                  <td>{inspectionRouteGroupEquipment.included ? 'true' : 'false'}</td>
                  <td>{inspectionRouteGroupEquipment.orderIndex}</td>
                  <td>
                    {inspectionRouteGroupEquipment.inspectionRouteGroup ? (
                      <Link to={`/inspection-route-group/${inspectionRouteGroupEquipment.inspectionRouteGroup.id}`}>
                        {inspectionRouteGroupEquipment.inspectionRouteGroup.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inspectionRouteGroupEquipment.equipment ? (
                      <Link to={`/equipment/${inspectionRouteGroupEquipment.equipment.id}`}>
                        {inspectionRouteGroupEquipment.equipment.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/inspection-route-group-equipment/${inspectionRouteGroupEquipment.id}`}
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
                        to={`/inspection-route-group-equipment/${inspectionRouteGroupEquipment.id}/edit`}
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
                          (window.location.href = `/inspection-route-group-equipment/${inspectionRouteGroupEquipment.id}/delete`)
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
              <Translate contentKey="thermographyApiApp.inspectionRouteGroupEquipment.home.notFound">
                No Inspection Route Group Equipments found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default InspectionRouteGroupEquipment;
