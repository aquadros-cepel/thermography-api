import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './inspection-route-group.reducer';

export const InspectionRouteGroup = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const inspectionRouteGroupList = useAppSelector(state => state.inspectionRouteGroup.entities);
  const loading = useAppSelector(state => state.inspectionRouteGroup.loading);

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
      <h2 id="inspection-route-group-heading" data-cy="InspectionRouteGroupHeading">
        <Translate contentKey="thermographyApiApp.inspectionRouteGroup.home.title">Inspection Route Groups</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.inspectionRouteGroup.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/inspection-route-group/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.inspectionRouteGroup.home.createLabel">Create new Inspection Route Group</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {inspectionRouteGroupList && inspectionRouteGroupList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroup.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroup.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroup.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroup.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('included')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroup.included">Included</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('included')} />
                </th>
                <th className="hand" onClick={sort('orderIndex')}>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroup.orderIndex">Order Index</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('orderIndex')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroup.inspectionRoute">Inspection Route</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.inspectionRouteGroup.parentGroup">Parent Group</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inspectionRouteGroupList.map((inspectionRouteGroup, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/inspection-route-group/${inspectionRouteGroup.id}`} color="link" size="sm">
                      {inspectionRouteGroup.id}
                    </Button>
                  </td>
                  <td>{inspectionRouteGroup.code}</td>
                  <td>{inspectionRouteGroup.name}</td>
                  <td>{inspectionRouteGroup.description}</td>
                  <td>{inspectionRouteGroup.included ? 'true' : 'false'}</td>
                  <td>{inspectionRouteGroup.orderIndex}</td>
                  <td>
                    {inspectionRouteGroup.inspectionRoute ? (
                      <Link to={`/inspection-route/${inspectionRouteGroup.inspectionRoute.id}`}>
                        {inspectionRouteGroup.inspectionRoute.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {inspectionRouteGroup.parentGroup ? (
                      <Link to={`/inspection-route-group/${inspectionRouteGroup.parentGroup.id}`}>
                        {inspectionRouteGroup.parentGroup.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/inspection-route-group/${inspectionRouteGroup.id}`}
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
                        to={`/inspection-route-group/${inspectionRouteGroup.id}/edit`}
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
                        onClick={() => (window.location.href = `/inspection-route-group/${inspectionRouteGroup.id}/delete`)}
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
              <Translate contentKey="thermographyApiApp.inspectionRouteGroup.home.notFound">No Inspection Route Groups found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default InspectionRouteGroup;
