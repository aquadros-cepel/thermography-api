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

import { getEntities } from './thermographic-inspection-record.reducer';

export const ThermographicInspectionRecord = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const thermographicInspectionRecordList = useAppSelector(state => state.thermographicInspectionRecord.entities);
  const loading = useAppSelector(state => state.thermographicInspectionRecord.loading);

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
      <h2 id="thermographic-inspection-record-heading" data-cy="ThermographicInspectionRecordHeading">
        <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.home.title">Thermographic Inspection Records</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/thermographic-inspection-record/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.home.createLabel">
              Create new Thermographic Inspection Record
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {thermographicInspectionRecordList && thermographicInspectionRecordList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.type">Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('serviceOrder')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.serviceOrder">Service Order</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('serviceOrder')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('analysisDescription')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.analysisDescription">
                    Analysis Description
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('analysisDescription')} />
                </th>
                <th className="hand" onClick={sort('condition')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.condition">Condition</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('condition')} />
                </th>
                <th className="hand" onClick={sort('deltaT')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.deltaT">Delta T</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deltaT')} />
                </th>
                <th className="hand" onClick={sort('periodicity')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.periodicity">Periodicity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('periodicity')} />
                </th>
                <th className="hand" onClick={sort('deadlineExecution')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.deadlineExecution">Deadline Execution</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deadlineExecution')} />
                </th>
                <th className="hand" onClick={sort('nextMonitoring')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.nextMonitoring">Next Monitoring</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nextMonitoring')} />
                </th>
                <th className="hand" onClick={sort('recommendations')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.recommendations">Recommendations</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('recommendations')} />
                </th>
                <th className="hand" onClick={sort('finished')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.finished">Finished</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finished')} />
                </th>
                <th className="hand" onClick={sort('finishedAt')}>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.finishedAt">Finished At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finishedAt')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.plant">Plant</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.route">Route</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.equipment">Equipment</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.component">Component</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.createdBy">Created By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.finishedBy">Finished By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.thermogram">Thermogram</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.thermogramRef">Thermogram Ref</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {thermographicInspectionRecordList.map((thermographicInspectionRecord, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/thermographic-inspection-record/${thermographicInspectionRecord.id}`} color="link" size="sm">
                      {thermographicInspectionRecord.id}
                    </Button>
                  </td>
                  <td>{thermographicInspectionRecord.name}</td>
                  <td>
                    <Translate contentKey={`thermographyApiApp.ThermographicInspectionRecordType.${thermographicInspectionRecord.type}`} />
                  </td>
                  <td>{thermographicInspectionRecord.serviceOrder}</td>
                  <td>
                    {thermographicInspectionRecord.createdAt ? (
                      <TextFormat type="date" value={thermographicInspectionRecord.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{thermographicInspectionRecord.analysisDescription}</td>
                  <td>
                    <Translate contentKey={`thermographyApiApp.ConditionType.${thermographicInspectionRecord.condition}`} />
                  </td>
                  <td>{thermographicInspectionRecord.deltaT}</td>
                  <td>{thermographicInspectionRecord.periodicity}</td>
                  <td>
                    {thermographicInspectionRecord.deadlineExecution ? (
                      <TextFormat type="date" value={thermographicInspectionRecord.deadlineExecution} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {thermographicInspectionRecord.nextMonitoring ? (
                      <TextFormat type="date" value={thermographicInspectionRecord.nextMonitoring} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{thermographicInspectionRecord.recommendations}</td>
                  <td>{thermographicInspectionRecord.finished ? 'true' : 'false'}</td>
                  <td>
                    {thermographicInspectionRecord.finishedAt ? (
                      <TextFormat type="date" value={thermographicInspectionRecord.finishedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {thermographicInspectionRecord.plant ? (
                      <Link to={`/plant/${thermographicInspectionRecord.plant.id}`}>{thermographicInspectionRecord.plant.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicInspectionRecord.route ? (
                      <Link to={`/inspection-record/${thermographicInspectionRecord.route.id}`}>
                        {thermographicInspectionRecord.route.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicInspectionRecord.equipment ? (
                      <Link to={`/equipment/${thermographicInspectionRecord.equipment.id}`}>
                        {thermographicInspectionRecord.equipment.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicInspectionRecord.component ? (
                      <Link to={`/equipment-component/${thermographicInspectionRecord.component.id}`}>
                        {thermographicInspectionRecord.component.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicInspectionRecord.createdBy ? (
                      <Link to={`/user-info/${thermographicInspectionRecord.createdBy.id}`}>
                        {thermographicInspectionRecord.createdBy.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicInspectionRecord.finishedBy ? (
                      <Link to={`/user-info/${thermographicInspectionRecord.finishedBy.id}`}>
                        {thermographicInspectionRecord.finishedBy.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicInspectionRecord.thermogram ? (
                      <Link to={`/thermogram/${thermographicInspectionRecord.thermogram.id}`}>
                        {thermographicInspectionRecord.thermogram.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thermographicInspectionRecord.thermogramRef ? (
                      <Link to={`/thermogram/${thermographicInspectionRecord.thermogramRef.id}`}>
                        {thermographicInspectionRecord.thermogramRef.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/thermographic-inspection-record/${thermographicInspectionRecord.id}`}
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
                        to={`/thermographic-inspection-record/${thermographicInspectionRecord.id}/edit`}
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
                          (window.location.href = `/thermographic-inspection-record/${thermographicInspectionRecord.id}/delete`)
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
              <Translate contentKey="thermographyApiApp.thermographicInspectionRecord.home.notFound">
                No Thermographic Inspection Records found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ThermographicInspectionRecord;
