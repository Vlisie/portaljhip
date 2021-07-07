import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Relatie from './relatie';
import RelatieDetail from './relatie-detail';
import RelatieUpdate from './relatie-update';
import RelatieDeleteDialog from './relatie-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RelatieUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RelatieUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RelatieDetail} />
      <ErrorBoundaryRoute path={match.url} component={Relatie} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RelatieDeleteDialog} />
  </>
);

export default Routes;
