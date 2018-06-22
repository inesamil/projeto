import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Error from './error'
import { mapSirenToUser } from '../utils/mapHypermedia'

export default ({ url, getAuthorization }) => (
  <HttpGet
    url={url}
    getAuthorization={getAuthorization}
    render={result => (
      <HttpGetSwitch
        result={result}
        onError={error => (
          <Error
            error={error} />
        )}
        onJson={json => {
          const user = mapSirenToUser(json)
          return (
            <div>
              <h3>User:</h3>
              <div>
                <span><strong>Username:</strong> {user.username}</span>
              </div>
            </div>
          )
        }}
      />
    )}
  />
)
