import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { deleteEntity, getEntity } from './equipment-component-temperature-limits.reducer';

export const EquipmentComponentTemperatureLimitsDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const equipmentComponentTemperatureLimitsEntity = useAppSelector(state => state.equipmentComponentTemperatureLimits.entity);
  const updateSuccess = useAppSelector(state => state.equipmentComponentTemperatureLimits.updateSuccess);

  const handleClose = () => {
    navigate('/equipment-component-temperature-limits');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(equipmentComponentTemperatureLimitsEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="equipmentComponentTemperatureLimitsDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="thermographyApiApp.equipmentComponentTemperatureLimits.delete.question">
        <Translate
          contentKey="thermographyApiApp.equipmentComponentTemperatureLimits.delete.question"
          interpolate={{ id: equipmentComponentTemperatureLimitsEntity.id }}
        >
          Are you sure you want to delete this EquipmentComponentTemperatureLimits?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button
          id="jhi-confirm-delete-equipmentComponentTemperatureLimits"
          data-cy="entityConfirmDeleteButton"
          color="danger"
          onClick={confirmDelete}
        >
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default EquipmentComponentTemperatureLimitsDeleteDialog;
