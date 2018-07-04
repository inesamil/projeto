import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Error from './error'
import { mapSirenToStorages } from '../utils/mapHypermedia'

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
          const storages = mapSirenToStorages(json).storages
          return (
            <div>
              <div id='myUL'>
                {storages.map((storage, idx) => (
                  <li key={idx} id='divCard' className='card' style={{listStyleType: 'none'}}>
                    <h3 className='container'>{storage.storageName}</h3>
                    <p>{`[${storage.temperature.minimum},${storage.temperature.maximum}]`}</p>
                  </li>
                ))}
              </div>
            </div>
          )
        }}
      />
    )}
  />
)
