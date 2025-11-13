import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './equipment-component-temperature-limits.reducer';

export const EquipmentComponentTemperatureLimits = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const equipmentComponentTemperatureLimitsList = useAppSelector(state => state.equipmentComponentTemperatureLimits.entities);
  const loading = useAppSelector(state => state.equipmentComponentTemperatureLimits.loading);

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
      <h2 id="equipment-component-temperature-limits-heading" data-cy="EquipmentComponentTemperatureLimitsHeading">
        <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.home.title">
          Equipment Component Temperature Limits
        </Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/equipment-component-temperature-limits/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.home.createLabel">
              Create new Equipment Component Temperature Limits
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {equipmentComponentTemperatureLimitsList && equipmentComponentTemperatureLimitsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('normal')}>
                  <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.normal">Normal</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('normal')} />
                </th>
                <th className="hand" onClick={sort('lowRisk')}>
                  <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.lowRisk">Low Risk</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lowRisk')} />
                </th>
                <th className="hand" onClick={sort('mediumRisk')}>
                  <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.mediumRisk">Medium Risk</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('mediumRisk')} />
                </th>
                <th className="hand" onClick={sort('highRisk')}>
                  <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.highRisk">High Risk</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('highRisk')} />
                </th>
                <th className="hand" onClick={sort('imminentHighRisk')}>
                  <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.imminentHighRisk">
                    Imminent High Risk
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('imminentHighRisk')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {equipmentComponentTemperatureLimitsList.map((equipmentComponentTemperatureLimits, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/equipment-component-temperature-limits/${equipmentComponentTemperatureLimits.id}`}
                      color="link"
                      size="sm"
                    >
                      {equipmentComponentTemperatureLimits.id}
                    </Button>
                  </td>
                  <td>{equipmentComponentTemperatureLimits.name}</td>
                  <td>{equipmentComponentTemperatureLimits.normal}</td>
                  <td>{equipmentComponentTemperatureLimits.lowRisk}</td>
                  <td>{equipmentComponentTemperatureLimits.mediumRisk}</td>
                  <td>{equipmentComponentTemperatureLimits.highRisk}</td>
                  <td>{equipmentComponentTemperatureLimits.imminentHighRisk}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/equipment-component-temperature-limits/${equipmentComponentTemperatureLimits.id}`}
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
                        to={`/equipment-component-temperature-limits/${equipmentComponentTemperatureLimits.id}/edit`}
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
                          (window.location.href = `/equipment-component-temperature-limits/${equipmentComponentTemperatureLimits.id}/delete`)
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
              <Translate contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.home.notFound">
                No Equipment Component Temperature Limits found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default EquipmentComponentTemperatureLimits;
