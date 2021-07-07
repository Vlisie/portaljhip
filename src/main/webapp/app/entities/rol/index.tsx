import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Rol from './rol';
import RolDetail from './rol-detail';
import RolUpdate from './rol-update';
import RolDeleteDialog from './rol-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RolUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RolUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RolDetail} />
      <ErrorBoundaryRoute path={match.url} component={Rol} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RolDeleteDialog} />
  </>
);

export default Routes;
