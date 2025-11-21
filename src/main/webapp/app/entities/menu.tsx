import React from 'react';
import { Translate } from 'react-jhipster'; // eslint-disable-line

import MenuItem from 'app/shared/layout/menus/menu-item'; // eslint-disable-line

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/company">
        <Translate contentKey="global.menu.entities.company" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/plant">
        <Translate contentKey="global.menu.entities.plant" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/business-unit">
        <Translate contentKey="global.menu.entities.businessUnit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipment">
        <Translate contentKey="global.menu.entities.equipment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipment-group">
        <Translate contentKey="global.menu.entities.equipmentGroup" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/inspection-route">
        <Translate contentKey="global.menu.entities.inspectionRoute" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/inspection-route-group">
        <Translate contentKey="global.menu.entities.inspectionRouteGroup" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipment-type-translation">
        <Translate contentKey="global.menu.entities.equipmentTypeTranslation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipment-component">
        <Translate contentKey="global.menu.entities.equipmentComponent" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/equipment-component-temperature-limits">
        <Translate contentKey="global.menu.entities.equipmentComponentTemperatureLimits" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/risk-periodicity-deadline">
        <Translate contentKey="global.menu.entities.riskPeriodicityDeadline" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/risk-recommendation-translation">
        <Translate contentKey="global.menu.entities.riskRecommendationTranslation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/thermogram">
        <Translate contentKey="global.menu.entities.thermogram" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/roi">
        <Translate contentKey="global.menu.entities.roi" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/thermographic-inspection-record">
        <Translate contentKey="global.menu.entities.thermographicInspectionRecord" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-info">
        <Translate contentKey="global.menu.entities.userInfo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/inspection-route-group-equipment">
        <Translate contentKey="global.menu.entities.inspectionRouteGroupEquipment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/inspection-route-record">
        <Translate contentKey="global.menu.entities.inspectionRouteRecord" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
