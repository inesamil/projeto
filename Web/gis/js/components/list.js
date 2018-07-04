import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Error from './error'
import { mapSirenToList } from '../utils/mapHypermedia'

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
          const list = mapSirenToList(json)
          return (
            <div>
              <h4>{list.listName}</h4>
              {list.listType === 'system'
                ? <p>System List</p>
                : <div>
                  <p>{list.username}</p>
                  {console.log(list.shareable)}
                  <p>Private List: {list.shareable
                    ? <input type='checkbox' disabled='true' unchecked='true' />
                    : <input type='checkbox' disabled='true' checked='true' />}
                  </p>
                </div>
              }
              <p>{list.houseName}</p>
              <table className='center'>
                <tbody>
                  <tr>
                    <th>Item</th>
                    <th style={{width: '1vw', whiteSpace: 'nowrap', textAlign: 'right'}}>Quantity</th>
                  </tr>
                  {list.listProducts.map((product, idx) => (
                    <tr>
                      <td>{product.productName}</td>
                      <td style={{width: '1vw', whiteSpace: 'nowrap', textAlign: 'right'}}>{product.quantity}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )
        }}
      />
    )}
  />
)
