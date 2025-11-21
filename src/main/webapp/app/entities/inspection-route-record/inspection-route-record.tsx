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

import { getEntities } from './inspection-route-record.reducer';

export const InspectionRouteRecord = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const inspectionRouteRecordList = useAppSelector(state => state.inspectionRouteRecord.entities);
  const loading = useAppSelector(state => state.inspectionRouteRecord.loading);

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
      <h2 id="inspection-route-record-heading" data-cy="InspectionRouteRecordHeading">
        <Translate contentKey="thermographyApiApp.inspectionRouteRecord.home.title">Inspection Route Records</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.inspectionRouteRecord.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/inspection-route-record/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.inspectionRouteRecord.home.createLabel">Create new Inspection Route Record</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {inspectionRouteRecordList && inspectionRouteRecordList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('maintenanceDocument')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.maintenanceDocument">Maintenance Document</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('maintenanceDocument')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('expectedStartDate')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.expectedStartDate">Expected Start Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('expectedStartDate')} />
                </th>
                <th className="hand" onClick={sort('expectedEndDate')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.expectedEndDate">Expected End Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('expectedEndDate')} />
                </th>
                <th className="hand" onClick={sort('started')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.started">Started</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('started')} />
                </th>
                <th className="hand" onClick={sort('startedAt')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.startedAt">Started At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startedAt')} />
                </th>
                <th className="hand" onClick={sort('finished')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.finished">Finished</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finished')} />
                </th>
                <th className="hand" onClick={sort('finishedAt')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.finishedAt">Finished At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finishedAt')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.startedBy">Started By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRouteRecord.finishedBy">Finished By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inspectionRouteRecordList.map((inspectionRouteRecord, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/inspection-route-record/${inspectionRouteRecord.id}`} color="link" size="sm">
                      {inspectionRouteRecord.id}
                    </Button>
                  </td>
                  <td>{inspectionRouteRecord.code}</td>
                  <td>{inspectionRouteRecord.name}</td>
                  <td>{inspectionRouteRecord.description}</td>
                  <td>{inspectionRouteRecord.maintenanceDocument}</td>
                  <td>
                    {inspectionRouteRecord.createdAt ? (
                      <TextFormat type="date" value={inspectionRouteRecord.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inspectionRouteRecord.expectedStartDate ? (
                      <TextFormat type="date" value={inspectionRouteRecord.expectedStartDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inspectionRouteRecord.expectedEndDate ? (
                      <TextFormat type="date" value={inspectionRouteRecord.expectedEndDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{inspectionRouteRecord.started ? 'true' : 'false'}</td>
                  <td>
                    {inspectionRouteRecord.startedAt ? (
                      <TextFormat type="date" value={inspectionRouteRecord.startedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{inspectionRouteRecord.finished ? 'true' : 'false'}</td>
                  <td>
                    {inspectionRouteRecord.finishedAt ? (
                      <TextFormat type="date" value={inspectionRouteRecord.finishedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inspectionRouteRecord.startedBy ? (
                      <Link to={`/user-info/${inspectionRouteRecord.startedBy.id}`}>{inspectionRouteRecord.startedBy.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inspectionRouteRecord.finishedBy ? (
                      <Link to={`/user-info/${inspectionRouteRecord.finishedBy.id}`}>{inspectionRouteRecord.finishedBy.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/inspection-route-record/${inspectionRouteRecord.id}`}
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
                        to={`/inspection-route-record/${inspectionRouteRecord.id}/edit`}
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
                        onClick={() => (window.location.href = `/inspection-route-record/${inspectionRouteRecord.id}/delete`)}
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
              <Translate contentKey="thermographyApiApp.inspectionRouteRecord.home.notFound">No Inspection Route Records found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default InspectionRouteRecord;
