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
              <div>
                <h2 className='profile-title'>Perfil</h2>
              </div>

              <div className='box'>
                <div>
                  <p />
                  <fieldset>
                    <legend>Informação Básica</legend>
                    <div>
                      <label>Nome completo: </label>
                      <span> {user.name}</span>
                    </div>
                    <div>
                      <label>Email: </label>
                      <span> {user.email}</span>
                    </div>
                    <div>
                      <label>Username: </label>
                      <span> {user.username}</span>
                    </div>
                    <div>
                      <label>Idade: </label>
                      <span> {user.age}</span>
                    </div>
                  </fieldset>
                </div>
              </div>
            </div>
          )
        }}
      />
    )}
  />
)
