import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Relatie from './relatie';
import Rol from './rol';
import Adres from './adres';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}relatie`} component={Relatie} />
      <ErrorBoundaryRoute path={`${match.url}rol`} component={Rol} />
      <ErrorBoundaryRoute path={`${match.url}adres`} component={Adres} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
