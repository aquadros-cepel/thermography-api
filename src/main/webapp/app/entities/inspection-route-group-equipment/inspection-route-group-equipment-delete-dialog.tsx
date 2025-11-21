import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { deleteEntity, getEntity } from './inspection-route-group-equipment.reducer';

export const InspectionRouteGroupEquipmentDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const inspectionRouteGroupEquipmentEntity = useAppSelector(state => state.inspectionRouteGroupEquipment.entity);
  const updateSuccess = useAppSelector(state => state.inspectionRouteGroupEquipment.updateSuccess);

  const handleClose = () => {
    navigate('/inspection-route-group-equipment');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(inspectionRouteGroupEquipmentEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="inspectionRouteGroupEquipmentDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="thermographyApiApp.inspectionRouteGroupEquipment.delete.question">
        <Translate
          contentKey="thermographyApiApp.inspectionRouteGroupEquipment.delete.question"
          interpolate={{ id: inspectionRouteGroupEquipmentEntity.id }}
        >
          Are you sure you want to delete this InspectionRouteGroupEquipment?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button
          id="jhi-confirm-delete-inspectionRouteGroupEquipment"
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

export default InspectionRouteGroupEquipmentDeleteDialog;
