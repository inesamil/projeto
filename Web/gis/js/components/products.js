import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Error from './error'
import { mapSirenToCategoryProducts } from '../utils/mapHypermedia'

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
          const products = mapSirenToCategoryProducts(json).categoryProducts
          return (
            <div>
              <div id='myUL'>
                {products.map((product, idx) => (
                  <li key={idx} id='divCard' className='card' style={{listStyleType: 'none'}}>
                    <h3 class='container'>{product.name}</h3>
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
