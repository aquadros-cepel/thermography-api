import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './risk-periodicity-deadline.reducer';

export const RiskPeriodicityDeadline = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const riskPeriodicityDeadlineList = useAppSelector(state => state.riskPeriodicityDeadline.entities);
  const loading = useAppSelector(state => state.riskPeriodicityDeadline.loading);

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
      <h2 id="risk-periodicity-deadline-heading" data-cy="RiskPeriodicityDeadlineHeading">
        <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.home.title">Risk Periodicity Deadlines</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/risk-periodicity-deadline/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.home.createLabel">
              Create new Risk Periodicity Deadline
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {riskPeriodicityDeadlineList && riskPeriodicityDeadlineList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('deadline')}>
                  <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.deadline">Deadline</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deadline')} />
                </th>
                <th className="hand" onClick={sort('deadlineUnit')}>
                  <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.deadlineUnit">Deadline Unit</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deadlineUnit')} />
                </th>
                <th className="hand" onClick={sort('periodicity')}>
                  <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.periodicity">Periodicity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('periodicity')} />
                </th>
                <th className="hand" onClick={sort('periodicityUnit')}>
                  <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.periodicityUnit">Periodicity Unit</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('periodicityUnit')} />
                </th>
                <th className="hand" onClick={sort('recommendations')}>
                  <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.recommendations">Recommendations</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('recommendations')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {riskPeriodicityDeadlineList.map((riskPeriodicityDeadline, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/risk-periodicity-deadline/${riskPeriodicityDeadline.id}`} color="link" size="sm">
                      {riskPeriodicityDeadline.id}
                    </Button>
                  </td>
                  <td>{riskPeriodicityDeadline.name}</td>
                  <td>{riskPeriodicityDeadline.deadline}</td>
                  <td>
                    <Translate contentKey={`thermographyApiApp.DatetimeUnit.${riskPeriodicityDeadline.deadlineUnit}`} />
                  </td>
                  <td>{riskPeriodicityDeadline.periodicity}</td>
                  <td>
                    <Translate contentKey={`thermographyApiApp.DatetimeUnit.${riskPeriodicityDeadline.periodicityUnit}`} />
                  </td>
                  <td>{riskPeriodicityDeadline.recommendations}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/risk-periodicity-deadline/${riskPeriodicityDeadline.id}`}
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
                        to={`/risk-periodicity-deadline/${riskPeriodicityDeadline.id}/edit`}
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
                        onClick={() => (window.location.href = `/risk-periodicity-deadline/${riskPeriodicityDeadline.id}/delete`)}
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
              <Translate contentKey="thermographyApiApp.riskPeriodicityDeadline.home.notFound">
                No Risk Periodicity Deadlines found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default RiskPeriodicityDeadline;
