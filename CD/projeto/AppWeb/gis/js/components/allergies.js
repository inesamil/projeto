import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Error from './error'
import { mapSirenToHouseAllergies } from '../utils/mapHypermedia'

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
          const allergies = mapSirenToHouseAllergies(json)
          return (
            <div>
              <h2 align='center'>Alergias</h2>
              <br />
              <p><h3 style={{'text-indent': '400px'}}>Alergénios :</h3></p>
              <div>
                <table className='table card' >
                    {allergies.houseAllergies.map((allergy, idx) =>
                      <tr key={idx}>
                        <td>
                          <h3 style={{'text-indent': '40px'}}>{allergy.allergen}</h3>
                        </td>
                        <td>
                          <div class='number-input'>
                            <h3>{allergy.allergicsNumber}</h3>
                          </div>
                        </td>
                      </tr>
                    )}
                  </table>
              </div>
            </div>
          )
        }}
      />
    )}
  />
)
