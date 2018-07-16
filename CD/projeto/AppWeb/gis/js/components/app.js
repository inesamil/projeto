import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Routes from './routes'
import Error from './error'
import { mapJsonHomeToIndex } from '../utils/mapHypermedia'

export default () => (
  <HttpGet
    url='http://localhost:8081/v1'
    render={result => (
      <HttpGetSwitch
        result={result}
        onError={error => (
          <Error
            error={error} />
        )}
        onJson={json => (
          <Routes
            home={mapJsonHomeToIndex(json)} />
        )} />
    )} />
)
