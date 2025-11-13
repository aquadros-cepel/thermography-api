import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './equipment.reducer';

export const Equipment = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const equipmentList = useAppSelector(state => state.equipment.entities);
  const loading = useAppSelector(state => state.equipment.loading);

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
      <h2 id="equipment-heading" data-cy="EquipmentHeading">
        <Translate contentKey="thermographyApiApp.equipment.home.title">Equipment</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.equipment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/equipment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.equipment.home.createLabel">Create new Equipment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {equipmentList && equipmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.equipment.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="thermographyApiApp.equipment.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="thermographyApiApp.equipment.title">Title</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('title')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="thermographyApiApp.equipment.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  <Translate contentKey="thermographyApiApp.equipment.type">Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('manufacturer')}>
                  <Translate contentKey="thermographyApiApp.equipment.manufacturer">Manufacturer</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('manufacturer')} />
                </th>
                <th className="hand" onClick={sort('model')}>
                  <Translate contentKey="thermographyApiApp.equipment.model">Model</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('model')} />
                </th>
                <th className="hand" onClick={sort('serialNumber')}>
                  <Translate contentKey="thermographyApiApp.equipment.serialNumber">Serial Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('serialNumber')} />
                </th>
                <th className="hand" onClick={sort('voltageClass')}>
                  <Translate contentKey="thermographyApiApp.equipment.voltageClass">Voltage Class</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('voltageClass')} />
                </th>
                <th className="hand" onClick={sort('phaseType')}>
                  <Translate contentKey="thermographyApiApp.equipment.phaseType">Phase Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('phaseType')} />
                </th>
                <th className="hand" onClick={sort('startDate')}>
                  <Translate contentKey="thermographyApiApp.equipment.startDate">Start Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startDate')} />
                </th>
                <th className="hand" onClick={sort('latitude')}>
                  <Translate contentKey="thermographyApiApp.equipment.latitude">Latitude</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('latitude')} />
                </th>
                <th className="hand" onClick={sort('longitude')}>
                  <Translate contentKey="thermographyApiApp.equipment.longitude">Longitude</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('longitude')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.equipment.plant">Plant</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.equipment.group">Group</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.equipment.inspectionRouteGroups">Inspection Route Groups</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.equipment.components">Components</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {equipmentList.map((equipment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/equipment/${equipment.id}`} color="link" size="sm">
                      {equipment.id}
                    </Button>
                  </td>
                  <td>{equipment.name}</td>
                  <td>{equipment.title}</td>
                  <td>{equipment.description}</td>
                  <td>
                    <Translate contentKey={`thermographyApiApp.EquipmentType.${equipment.type}`} />
                  </td>
                  <td>{equipment.manufacturer}</td>
                  <td>{equipment.model}</td>
                  <td>{equipment.serialNumber}</td>
                  <td>{equipment.voltageClass}</td>
                  <td>
                    <Translate contentKey={`thermographyApiApp.PhaseType.${equipment.phaseType}`} />
                  </td>
                  <td>
                    {equipment.startDate ? <TextFormat type="date" value={equipment.startDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{equipment.latitude}</td>
                  <td>{equipment.longitude}</td>
                  <td>{equipment.plant ? <Link to={`/plant/${equipment.plant.id}`}>{equipment.plant.id}</Link> : ''}</td>
                  <td>{equipment.group ? <Link to={`/equipment-group/${equipment.group.id}`}>{equipment.group.id}</Link> : ''}</td>
                  <td>
                    {equipment.inspectionRouteGroups
                      ? equipment.inspectionRouteGroups.map((val, j) => (
                          <span key={j}>
                            <Link to={`/inspection-route-group/${val.id}`}>{val.id}</Link>
                            {j === equipment.inspectionRouteGroups.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {equipment.components
                      ? equipment.components.map((val, j) => (
                          <span key={j}>
                            <Link to={`/equipment-component/${val.id}`}>{val.id}</Link>
                            {j === equipment.components.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/equipment/${equipment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/equipment/${equipment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/equipment/${equipment.id}/delete`)}
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
              <Translate contentKey="thermographyApiApp.equipment.home.notFound">No Equipment found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Equipment;
