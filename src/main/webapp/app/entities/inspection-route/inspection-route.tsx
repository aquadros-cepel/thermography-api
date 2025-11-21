import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './inspection-route.reducer';

export const InspectionRoute = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const inspectionRouteList = useAppSelector(state => state.inspectionRoute.entities);
  const loading = useAppSelector(state => state.inspectionRoute.loading);

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
      <h2 id="inspection-route-heading" data-cy="InspectionRouteHeading">
        <Translate contentKey="thermographyApiApp.inspectionRoute.home.title">Inspection Routes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.inspectionRoute.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/inspection-route/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.inspectionRoute.home.createLabel">Create new Inspection Route</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {inspectionRouteList && inspectionRouteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('maintenancePlan')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.maintenancePlan">Maintenance Plan</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('maintenancePlan')} />
                </th>
                <th className="hand" onClick={sort('periodicity')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.periodicity">Periodicity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('periodicity')} />
                </th>
                <th className="hand" onClick={sort('duration')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.duration">Duration</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('duration')} />
                </th>
                <th className="hand" onClick={sort('expectedStartDate')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.expectedStartDate">Expected Start Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('expectedStartDate')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.plant">Plant</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.createdBy">Created By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inspectionRouteList.map((inspectionRoute, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/inspection-route/${inspectionRoute.id}`} color="link" size="sm">
                      {inspectionRoute.id}
                    </Button>
                  </td>
                  <td>{inspectionRoute.code}</td>
                  <td>{inspectionRoute.name}</td>
                  <td>{inspectionRoute.description}</td>
                  <td>{inspectionRoute.maintenancePlan}</td>
                  <td>
                    <Translate contentKey={`thermographyApiApp.Periodicity.${inspectionRoute.periodicity}`} />
                  </td>
                  <td>{inspectionRoute.duration}</td>
                  <td>
                    {inspectionRoute.expectedStartDate ? (
                      <TextFormat type="date" value={inspectionRoute.expectedStartDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inspectionRoute.createdAt ? (
                      <TextFormat type="date" value={inspectionRoute.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{inspectionRoute.plant ? <Link to={`/plant/${inspectionRoute.plant.id}`}>{inspectionRoute.plant.id}</Link> : ''}</td>
                  <td>
                    {inspectionRoute.createdBy ? (
                      <Link to={`/user-info/${inspectionRoute.createdBy.id}`}>{inspectionRoute.createdBy.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/inspection-route/${inspectionRoute.id}`}
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
                        to={`/inspection-route/${inspectionRoute.id}/edit`}
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
                        onClick={() => (window.location.href = `/inspection-route/${inspectionRoute.id}/delete`)}
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
              <Translate contentKey="thermographyApiApp.inspectionRoute.home.notFound">No Inspection Routes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default InspectionRoute;
