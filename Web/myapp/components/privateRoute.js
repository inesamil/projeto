import React from 'react'
import { Route, Redirect } from 'react-router-dom'

export default ({ isAuthenticated, component: Component, ...rest, componentProps }) => (
  <Route
    {...rest}
    render={props =>
      isAuthenticated() ? (
        <Component {...componentProps(props)} />
      ) : (
        <Redirect
          to={{
            pathname: '/login',
            state: props.location
          }}
        />
      )
    }
  />
)
