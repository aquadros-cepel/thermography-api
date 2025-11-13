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
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('planNote')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.planNote">Plan Note</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('planNote')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('startDate')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.startDate">Start Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startDate')} />
                </th>
                <th className="hand" onClick={sort('started')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.started">Started</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('started')} />
                </th>
                <th className="hand" onClick={sort('startedAt')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.startedAt">Started At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startedAt')} />
                </th>
                <th className="hand" onClick={sort('endDate')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.endDate">End Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endDate')} />
                </th>
                <th className="hand" onClick={sort('finished')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.finished">Finished</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finished')} />
                </th>
                <th className="hand" onClick={sort('finishedAt')}>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.finishedAt">Finished At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finishedAt')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.plant">Plant</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.createdBy">Created By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.startedBy">Started By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRoute.finishedBy">Finished By</Translate>{' '}
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
                  <td>{inspectionRoute.name}</td>
                  <td>{inspectionRoute.title}</td>
                  <td>{inspectionRoute.description}</td>
                  <td>{inspectionRoute.planNote}</td>
                  <td>
                    {inspectionRoute.createdAt ? (
                      <TextFormat type="date" value={inspectionRoute.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inspectionRoute.startDate ? (
                      <TextFormat type="date" value={inspectionRoute.startDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{inspectionRoute.started ? 'true' : 'false'}</td>
                  <td>
                    {inspectionRoute.startedAt ? (
                      <TextFormat type="date" value={inspectionRoute.startedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inspectionRoute.endDate ? (
                      <TextFormat type="date" value={inspectionRoute.endDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{inspectionRoute.finished ? 'true' : 'false'}</td>
                  <td>
                    {inspectionRoute.finishedAt ? (
                      <TextFormat type="date" value={inspectionRoute.finishedAt} format={APP_DATE_FORMAT} />
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
                  <td>
                    {inspectionRoute.startedBy ? (
                      <Link to={`/user-info/${inspectionRoute.startedBy.id}`}>{inspectionRoute.startedBy.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inspectionRoute.finishedBy ? (
                      <Link to={`/user-info/${inspectionRoute.finishedBy.id}`}>{inspectionRoute.finishedBy.id}</Link>
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
