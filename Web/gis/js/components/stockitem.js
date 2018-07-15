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
              <h2 align='center'>{stockitem.productName} {stockitem.variety}</h2>
              <h3 align='center'>{stockitem.brand}</h3>
              <h5 align='center'>Qtd. {stockitem.quantity} unidades</h5>
              <div>
                <div className='card'>
                  <details>
                    <summary style={{fontSize: '22px'}}>Alergias</summary>
                    {stockitem.allergens.map((allergen, idx) => (
                      <p key={idx} style={{textIndent: '5%', color: 'black'}}>{allergen}</p>
                    ))}
                  </details>
                </div>
                <div className='card'>
                  <details>
                    <summary style={{fontSize: '22px'}}>Datas de Validade</summary>
                    {stockitem.expirationDates.map((expiration, idx) => (
                      <p key={idx} style={{textIndent: '5%', color: 'black'}}>{expiration.date} - {expiration.quantity} unidades</p>
                    ))}
                  </details>
                </div>
                <div className='card'>
                  <details>
                    <summary style={{fontSize: '22px'}}>Armazenamento</summary>
                    {stockitem.storages.map((storage, idx) => (
                      <p key={idx} style={{textIndent: '5%', color: 'black'}}>{storage}</p>
                    ))}
                  </details>
                </div>
                <div className='card'>
                  <details>
                    <summary style={{fontSize: '22px'}}>Descrição</summary>
                    <p style={{textIndent: '5%', color: 'black'}}>{stockitem.description}</p>
                  </details>
                </div>
                <div className='card'>
                  <details>
                    <summary style={{fontSize: '22px'}}>Movimentos</summary>
                    {stockitem.movements.map((movement, idx) => (
                      <p key={idx} style={{textIndent: '5%', color: 'black'}}>{movement.dateTime}  {movement.type ? 'Input' : 'Output'}  {movement.quantity}  unidades</p>
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
