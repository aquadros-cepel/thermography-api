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

import { getEntities } from './inspection-record.reducer';

export const InspectionRecord = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const inspectionRecordList = useAppSelector(state => state.inspectionRecord.entities);
  const loading = useAppSelector(state => state.inspectionRecord.loading);

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
      <h2 id="inspection-record-heading" data-cy="InspectionRecordHeading">
        <Translate contentKey="thermographyApiApp.inspectionRecord.home.title">Inspection Records</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.inspectionRecord.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/inspection-record/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.inspectionRecord.home.createLabel">Create new Inspection Record</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {inspectionRecordList && inspectionRecordList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('maintenanceDocument')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.maintenanceDocument">Maintenance Document</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('maintenanceDocument')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('expectedStartDate')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.expectedStartDate">Expected Start Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('expectedStartDate')} />
                </th>
                <th className="hand" onClick={sort('expectedEndDate')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.expectedEndDate">Expected End Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('expectedEndDate')} />
                </th>
                <th className="hand" onClick={sort('started')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.started">Started</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('started')} />
                </th>
                <th className="hand" onClick={sort('startedAt')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.startedAt">Started At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startedAt')} />
                </th>
                <th className="hand" onClick={sort('finished')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.finished">Finished</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finished')} />
                </th>
                <th className="hand" onClick={sort('finishedAt')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.finishedAt">Finished At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finishedAt')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.plant">Plant</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.inspectionRoute">Inspection Route</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.createdBy">Created By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.startedBy">Started By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRecord.finishedBy">Finished By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inspectionRecordList.map((inspectionRecord, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/inspection-record/${inspectionRecord.id}`} color="link" size="sm">
                      {inspectionRecord.id}
                    </Button>
                  </td>
                  <td>{inspectionRecord.code}</td>
                  <td>{inspectionRecord.name}</td>
                  <td>{inspectionRecord.description}</td>
                  <td>{inspectionRecord.maintenanceDocument}</td>
                  <td>
                    {inspectionRecord.createdAt ? (
                      <TextFormat type="date" value={inspectionRecord.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inspectionRecord.expectedStartDate ? (
                      <TextFormat type="date" value={inspectionRecord.expectedStartDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inspectionRecord.expectedEndDate ? (
                      <TextFormat type="date" value={inspectionRecord.expectedEndDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{inspectionRecord.started ? 'true' : 'false'}</td>
                  <td>
                    {inspectionRecord.startedAt ? (
                      <TextFormat type="date" value={inspectionRecord.startedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{inspectionRecord.finished ? 'true' : 'false'}</td>
                  <td>
                    {inspectionRecord.finishedAt ? (
                      <TextFormat type="date" value={inspectionRecord.finishedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inspectionRecord.plant ? <Link to={`/plant/${inspectionRecord.plant.id}`}>{inspectionRecord.plant.id}</Link> : ''}
                  </td>
                  <td>
                    {inspectionRecord.inspectionRoute ? (
                      <Link to={`/inspection-route/${inspectionRecord.inspectionRoute.id}`}>{inspectionRecord.inspectionRoute.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inspectionRecord.createdBy ? (
                      <Link to={`/user-info/${inspectionRecord.createdBy.id}`}>{inspectionRecord.createdBy.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inspectionRecord.startedBy ? (
                      <Link to={`/user-info/${inspectionRecord.startedBy.id}`}>{inspectionRecord.startedBy.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inspectionRecord.finishedBy ? (
                      <Link to={`/user-info/${inspectionRecord.finishedBy.id}`}>{inspectionRecord.finishedBy.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/inspection-record/${inspectionRecord.id}`}
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
                        to={`/inspection-record/${inspectionRecord.id}/edit`}
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
                        onClick={() => (window.location.href = `/inspection-record/${inspectionRecord.id}/delete`)}
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
              <Translate contentKey="thermographyApiApp.inspectionRecord.home.notFound">No Inspection Records found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default InspectionRecord;
