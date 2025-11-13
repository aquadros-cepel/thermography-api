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

import { getEntities } from './thermogram.reducer';

export const Thermogram = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const thermogramList = useAppSelector(state => state.thermogram.entities);
  const loading = useAppSelector(state => state.thermogram.loading);

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
      <h2 id="thermogram-heading" data-cy="ThermogramHeading">
        <Translate contentKey="thermographyApiApp.thermogram.home.title">Thermograms</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="thermographyApiApp.thermogram.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/thermogram/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="thermographyApiApp.thermogram.home.createLabel">Create new Thermogram</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {thermogramList && thermogramList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="thermographyApiApp.thermogram.id">Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('imagePath')}>
                  <Translate contentKey="thermographyApiApp.thermogram.imagePath">Image Path</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('imagePath')} />
                </th>
                <th className="hand" onClick={sort('audioPath')}>
                  <Translate contentKey="thermographyApiApp.thermogram.audioPath">Audio Path</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('audioPath')} />
                </th>
                <th className="hand" onClick={sort('imageRefPath')}>
                  <Translate contentKey="thermographyApiApp.thermogram.imageRefPath">Image Ref Path</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('imageRefPath')} />
                </th>
                <th className="hand" onClick={sort('minTemp')}>
                  <Translate contentKey="thermographyApiApp.thermogram.minTemp">Min Temp</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('minTemp')} />
                </th>
                <th className="hand" onClick={sort('avgTemp')}>
                  <Translate contentKey="thermographyApiApp.thermogram.avgTemp">Avg Temp</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('avgTemp')} />
                </th>
                <th className="hand" onClick={sort('maxTemp')}>
                  <Translate contentKey="thermographyApiApp.thermogram.maxTemp">Max Temp</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('maxTemp')} />
                </th>
                <th className="hand" onClick={sort('emissivity')}>
                  <Translate contentKey="thermographyApiApp.thermogram.emissivity">Emissivity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('emissivity')} />
                </th>
                <th className="hand" onClick={sort('subjectDistance')}>
                  <Translate contentKey="thermographyApiApp.thermogram.subjectDistance">Subject Distance</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('subjectDistance')} />
                </th>
                <th className="hand" onClick={sort('atmosphericTemp')}>
                  <Translate contentKey="thermographyApiApp.thermogram.atmosphericTemp">Atmospheric Temp</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('atmosphericTemp')} />
                </th>
                <th className="hand" onClick={sort('reflectedTemp')}>
                  <Translate contentKey="thermographyApiApp.thermogram.reflectedTemp">Reflected Temp</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('reflectedTemp')} />
                </th>
                <th className="hand" onClick={sort('relativeHumidity')}>
                  <Translate contentKey="thermographyApiApp.thermogram.relativeHumidity">Relative Humidity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('relativeHumidity')} />
                </th>
                <th className="hand" onClick={sort('cameraLens')}>
                  <Translate contentKey="thermographyApiApp.thermogram.cameraLens">Camera Lens</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cameraLens')} />
                </th>
                <th className="hand" onClick={sort('cameraModel')}>
                  <Translate contentKey="thermographyApiApp.thermogram.cameraModel">Camera Model</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('cameraModel')} />
                </th>
                <th className="hand" onClick={sort('imageResolution')}>
                  <Translate contentKey="thermographyApiApp.thermogram.imageResolution">Image Resolution</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('imageResolution')} />
                </th>
                <th className="hand" onClick={sort('selectedRoiId')}>
                  <Translate contentKey="thermographyApiApp.thermogram.selectedRoiId">Selected Roi Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('selectedRoiId')} />
                </th>
                <th className="hand" onClick={sort('maxTempRoi')}>
                  <Translate contentKey="thermographyApiApp.thermogram.maxTempRoi">Max Temp Roi</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('maxTempRoi')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="thermographyApiApp.thermogram.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('latitude')}>
                  <Translate contentKey="thermographyApiApp.thermogram.latitude">Latitude</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('latitude')} />
                </th>
                <th className="hand" onClick={sort('longitude')}>
                  <Translate contentKey="thermographyApiApp.thermogram.longitude">Longitude</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('longitude')} />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermogram.equipment">Equipment</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="thermographyApiApp.thermogram.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {thermogramList.map((thermogram, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/thermogram/${thermogram.id}`} color="link" size="sm">
                      {thermogram.id}
                    </Button>
                  </td>
                  <td>{thermogram.imagePath}</td>
                  <td>{thermogram.audioPath}</td>
                  <td>{thermogram.imageRefPath}</td>
                  <td>{thermogram.minTemp}</td>
                  <td>{thermogram.avgTemp}</td>
                  <td>{thermogram.maxTemp}</td>
                  <td>{thermogram.emissivity}</td>
                  <td>{thermogram.subjectDistance}</td>
                  <td>{thermogram.atmosphericTemp}</td>
                  <td>{thermogram.reflectedTemp}</td>
                  <td>{thermogram.relativeHumidity}</td>
                  <td>{thermogram.cameraLens}</td>
                  <td>{thermogram.cameraModel}</td>
                  <td>{thermogram.imageResolution}</td>
                  <td>{thermogram.selectedRoiId}</td>
                  <td>{thermogram.maxTempRoi}</td>
                  <td>{thermogram.createdAt ? <TextFormat type="date" value={thermogram.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{thermogram.latitude}</td>
                  <td>{thermogram.longitude}</td>
                  <td>{thermogram.equipment ? <Link to={`/equipment/${thermogram.equipment.id}`}>{thermogram.equipment.id}</Link> : ''}</td>
                  <td>{thermogram.createdBy ? <Link to={`/user-info/${thermogram.createdBy.id}`}>{thermogram.createdBy.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/thermogram/${thermogram.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/thermogram/${thermogram.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/thermogram/${thermogram.id}/delete`)}
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
              <Translate contentKey="thermographyApiApp.thermogram.home.notFound">No Thermograms found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Thermogram;
