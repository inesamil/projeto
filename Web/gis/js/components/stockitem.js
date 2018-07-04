import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Error from './error'
import { mapSirenToStockItem } from '../utils/mapHypermedia'

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
          const stockitem = mapSirenToStockItem(json)
          return (
            <div>
              <h1>{stockitem.productName} {stockitem.variety}</h1>
              <h3>{stockitem.brand}</h3>
              <h5>Qty. {stockitem.quantity} units</h5>
              <div>
                <div className='card'>
                  <details>
                    <summary style={{fontSize: '22px'}}>Allergens</summary>
                    {stockitem.allergens.map((allergen, idx) => (
                      <p key={idx} style={{textIndent: '5%', color: 'black'}}>{allergen}</p>
                    ))}
                  </details>
                </div>
                <div className='card'>
                  <details>
                    <summary style={{fonSize: '22px'}}>Expiration Dates</summary>
                    {stockitem.expirationDates.map((expiration, idx) => (
                      <p key={idx} style={{textIndent: '5%', color: 'black'}}>{expiration.date} - {expiration.quantity} units</p>
                    ))}
                  </details>
                </div>
                <div className='card'>
                  <details>
                    <summary style={{fontSize: '22px'}}>Storages</summary>
                    {stockitem.storages.map((storage, idx) => (
                      <p key={idx} style={{textIndent: '5%', color: 'black'}}>storage</p>
                    ))}
                  </details>
                </div>
                <div className='card'>
                  <details>
                    <summary style={{fontSize: '22px'}}>Description</summary>
                    <p style={{textIndent: '5%', color: 'black'}}>{stockitem.description}</p>
                  </details>
                </div>
                <div className='card'>
                  <details>
                    <summary style={{fontSize: '22px'}}>Movements</summary>
                    {stockitem.movements.map((movement, idx) => (
                      <p key={idx} style={{textIndent: '5%', color: 'black'}}>{movement.dateTime}  {movement.type ? 'Input' : 'Output'}  {movement.quantity}  units</p>
                    ))}
                  </details>
                </div>
                <br />
              </div>
            </div>
          )
        }}
      />
    )}
  />
)
