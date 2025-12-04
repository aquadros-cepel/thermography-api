import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './inspection-record-group.reducer';

export const InspectionRecordGroup = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const inspectionRecordGroupList = useAppSelector(state => state.inspectionRecordGroup.entities);
  const loading = useAppSelector(state => state.inspectionRecordGroup.loading);

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
      <h2 id="inspection-record-group-heading" data-cy="InspectionRecordGroupHeading">
        <Translate contentKey="thermographyApiApp.inspectionRecordGroup.home.title">Inspection Record Groups</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.inspectionRecordGroup.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/inspection-record-group/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.inspectionRecordGroup.home.createLabel">Create new Inspection Record Group</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {inspectionRecordGroupList && inspectionRecordGroupList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroup.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroup.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroup.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroup.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('orderIndex')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroup.orderIndex">Order Index</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('orderIndex')} />
                </th>
                <th className="hand" onClick={sort('finished')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroup.finished">Finished</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finished')} />
                </th>
                <th className="hand" onClick={sort('finishedAt')}>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroup.finishedAt">Finished At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('finishedAt')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroup.inspectionRecord">Inspection Record</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRecordGroup.parentGroup">Parent Group</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inspectionRecordGroupList.map((inspectionRecordGroup, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/inspection-record-group/${inspectionRecordGroup.id}`} color="link" size="sm">
                      {inspectionRecordGroup.id}
                    </Button>
                  </td>
                  <td>{inspectionRecordGroup.code}</td>
                  <td>{inspectionRecordGroup.name}</td>
                  <td>{inspectionRecordGroup.description}</td>
                  <td>{inspectionRecordGroup.orderIndex}</td>
                  <td>{inspectionRecordGroup.finished ? 'true' : 'false'}</td>
                  <td>
                    {inspectionRecordGroup.finishedAt ? (
                      <TextFormat type="date" value={inspectionRecordGroup.finishedAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {inspectionRecordGroup.inspectionRecord ? (
                      <Link to={`/inspection-record/${inspectionRecordGroup.inspectionRecord.id}`}>
                        {inspectionRecordGroup.inspectionRecord.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inspectionRecordGroup.parentGroup ? (
                      <Link to={`/inspection-record-group/${inspectionRecordGroup.parentGroup.id}`}>
                        {inspectionRecordGroup.parentGroup.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/inspection-record-group/${inspectionRecordGroup.id}`}
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
                        to={`/inspection-record-group/${inspectionRecordGroup.id}/edit`}
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
                        onClick={() => (window.location.href = `/inspection-record-group/${inspectionRecordGroup.id}/delete`)}
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
              <Translate contentKey="thermographyApiApp.inspectionRecordGroup.home.notFound">No Inspection Record Groups found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default InspectionRecordGroup;
