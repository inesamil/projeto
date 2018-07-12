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
              <h2 align='center'>{list.listName}</h2>
              {list.listType === 'system'
                ? <h3 style={{paddingLeft: '350px'}}>System List</h3>
                : <div>
                  <h3 style={{paddingLeft: '350px'}}>{list.username}</h3>
                  <p>Private List: {list.shareable
                    ? <input type='checkbox' disabled='true' unchecked='true' />
                    : <input type='checkbox' disabled='true' checked='true' />}
                  </p>
                </div>
              }
              <span className='glyphicon glyphicon-home' style={{'font-size': '15px', display: 'inline-block', marginRight: '10px', color: 'rgba(253, 73, 82, 0.8)', paddingLeft: '350px'}} />
              <h4 style={{display: 'inline-block', align: 'left'}}> {list.houseName}</h4>
              <table className='card'>
                <tbody>
                  <tr>
                    <th style={{paddingLeft: '20px'}}>Item</th>
                    <th style={{width: '1vw', whiteSpace: 'nowrap', textAlign: 'right', paddingRight: '20px'}}>Quantity</th>
                  </tr>
                  {list.listProducts.map((product, idx) => (
                    <tr>
                      <td style={{paddingLeft: '20px'}}>{product.productName}</td>
                      <td style={{width: '1vw', whiteSpace: 'nowrap', textAlign: 'right', paddingRight: '42px'}}>{product.quantity}</td>
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
