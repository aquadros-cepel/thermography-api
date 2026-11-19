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

import { getEntities } from './thermographic-record.reducer';

export const ThermographicRecord = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const thermographicRecordList = useAppSelector(state => state.thermographicRecord.entities);
  const loading = useAppSelector(state => state.thermographicRecord.loading);

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
      <h2 id="thermographic-record-heading" data-cy="ThermographicRecordHeading">
        <Translate contentKey="thermographyApiApp.thermographicRecord.home.title">Thermographic Records</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.thermographicRecord.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/thermographic-record/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.thermographicRecord.home.createLabel">Create new Thermographic Record</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {thermographicRecordList && thermographicRecordList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.type">Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('serviceOrder')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.serviceOrder">Service Order</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('serviceOrder')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('analysisDescription')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.analysisDescription">Analysis Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('analysisDescription')} />
                </th>
                <th className="hand" onClick={sort('condition')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.condition">Condition</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('condition')} />
                </th>
                <th className="hand" onClick={sort('deltaT')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.deltaT">Delta T</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deltaT')} />
                </th>
                <th className="hand" onClick={sort('periodicity')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.periodicity">Periodicity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('periodicity')} />
                </th>
                <th className="hand" onClick={sort('deadlineExecution')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.deadlineExecution">Deadline Execution</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deadlineExecution')} />
                </th>
                <th className="hand" onClick={sort('nextMonitoring')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.nextMonitoring">Next Monitoring</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nextMonitoring')} />
                </th>
                <th className="hand" onClick={sort('recommendations')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.recommendations">Recommendations</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('recommendations')} />
                </th>
                <th className="hand" onClick={sort('finished')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.finished">Finished</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finished')} />
                </th>
                <th className="hand" onClick={sort('finishedAt')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.finishedAt">Finished At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finishedAt')} />
                </th>
                <th className="hand" onClick={sort('current')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.current">Current</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('current')} />
                </th>
                <th className="hand" onClick={sort('voltage')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.voltage">Voltage</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('voltage')} />
                </th>
                <th className="hand" onClick={sort('windSpeed')}>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.windSpeed">Wind Speed</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('windSpeed')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.plant">Plant</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.route">Route</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.equipment">Equipment</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.component">Component</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.createdBy">Created By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.finishedBy">Finished By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.thermogram">Thermogram</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.thermogramRef">Thermogram Ref</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicRecord.parentRecord">Parent Record</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {thermographicRecordList.map((thermographicRecord, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/thermographic-record/${thermographicRecord.id}`} color="link" size="sm">
                      {thermographicRecord.id}
                    </Button>
                  </td>
                  <td>{thermographicRecord.name}</td>
                  <td>
                    <Translate contentKey={`thermographyApiApp.ThermographicRecordType.${thermographicRecord.type}`} />
                  </td>
                  <td>{thermographicRecord.serviceOrder}</td>
                  <td>
                    {thermographicRecord.createdAt ? (
                      <TextFormat type="date" value={thermographicRecord.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{thermographicRecord.analysisDescription}</td>
                  <td>
                    <Translate contentKey={`thermographyApiApp.ConditionType.${thermographicRecord.condition}`} />
                  </td>
                  <td>{thermographicRecord.deltaT}</td>
                  <td>{thermographicRecord.periodicity}</td>
                  <td>
                    {thermographicRecord.deadlineExecution ? (
                      <TextFormat type="date" value={thermographicRecord.deadlineExecution} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {thermographicRecord.nextMonitoring ? (
                      <TextFormat type="date" value={thermographicRecord.nextMonitoring} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{thermographicRecord.recommendations}</td>
                  <td>{thermographicRecord.finished ? 'true' : 'false'}</td>
                  <td>
                    {thermographicRecord.finishedAt ? (
                      <TextFormat type="date" value={thermographicRecord.finishedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{thermographicRecord.current}</td>
                  <td>{thermographicRecord.voltage}</td>
                  <td>{thermographicRecord.windSpeed}</td>
                  <td>
                    {thermographicRecord.plant ? (
                      <Link to={`/plant/${thermographicRecord.plant.id}`}>{thermographicRecord.plant.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicRecord.route ? (
                      <Link to={`/inspection-record/${thermographicRecord.route.id}`}>{thermographicRecord.route.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicRecord.equipment ? (
                      <Link to={`/equipment/${thermographicRecord.equipment.id}`}>{thermographicRecord.equipment.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicRecord.component ? (
                      <Link to={`/equipment-component/${thermographicRecord.component.id}`}>{thermographicRecord.component.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicRecord.createdBy ? (
                      <Link to={`/user-info/${thermographicRecord.createdBy.id}`}>{thermographicRecord.createdBy.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicRecord.finishedBy ? (
                      <Link to={`/user-info/${thermographicRecord.finishedBy.id}`}>{thermographicRecord.finishedBy.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicRecord.thermogram ? (
                      <Link to={`/thermogram/${thermographicRecord.thermogram.id}`}>{thermographicRecord.thermogram.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicRecord.thermogramRef ? (
                      <Link to={`/thermogram/${thermographicRecord.thermogramRef.id}`}>{thermographicRecord.thermogramRef.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicRecord.parentRecord ? (
                      <Link to={`/thermographic-record/${thermographicRecord.parentRecord.id}`}>{thermographicRecord.parentRecord.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/thermographic-record/${thermographicRecord.id}`}
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
                        to={`/thermographic-record/${thermographicRecord.id}/edit`}
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
                        onClick={() => (window.location.href = `/thermographic-record/${thermographicRecord.id}/delete`)}
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
              <Translate contentKey="thermographyApiApp.thermographicRecord.home.notFound">No Thermographic Records found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ThermographicRecord;
