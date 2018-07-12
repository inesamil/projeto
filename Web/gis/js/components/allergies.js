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
              <h2 align='center'>Allergies</h2>
              <br />
              <div align='center'>
                <h4>
                        Are there members with allergies in the house ?
                </h4>
                <label className='containerLabel'> Yes
                  {allergies.houseAllergies ? (<input type='radio' checked='checked' name='radio' />) : (<input type='radio' name='radio' />) }
                </label>
                <label className='containerLabel'> No 
                  {allergies.houseAllergies ? (<input type='radio' name='radio' />) : (<input type='radio' checked='checked' name='radio' />) }
                </label>
              </div>
              <p><br /><h3 style={{'text-indent': '400px'}}>Allergens :</h3><br /></p>
              {allergies.houseAllergies
                ? (<div>
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
              </div>) : ('')}
            </div>
          )
        }}
      />
    )}
  />
)
