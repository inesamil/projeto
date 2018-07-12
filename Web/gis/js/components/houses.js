import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Error from './error'
import { mapSirenToUserHouses } from '../utils/mapHypermedia'

export default ({ url, getAuthorization, redirectToStorages, redirectToAllergies }) => (
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
          const userHouses = mapSirenToUserHouses(json)
          return (
            <div>
              <h2 align='center'>Houses</h2>
              {userHouses.userHouses.map((house, idx) => (
                <div className='card text-center' key={idx}>
                  <div style={{'text-align': 'center'}}>
                    <br />
                    <span className='glyphicon glyphicon-home' style={{'font-size': '100px', color: 'rgba(253, 73, 82, 0.8)'}} />
                    <h3 style={{'font-size': '30px;'}}> {house.houseName}</h3>
                    <button className='buttonsHouse' onClick={() => redirectToStorages(house.storagesLink.href)}>
                      <h4> Storages</h4>
                    </button>

                    <button className='buttonsHouse' onClick={() => redirectToAllergies(house.houseAllergiesLink.href)}>
                      <h4> Allergies</h4>
                    </button>
                  </div>
                  <div>
                    <h4 className='text-center'>Characteristcs:</h4>
                    <span display='inline'>
                      <div className='inner'>
                        <p> Babies: </p>
                        {house.babiesNumber}
                      </div>
                      <div className='inner'>
                        <p> Children: </p>
                        {house.childrenNumber}
                      </div>
                      <div className='inner'>
                        <p> Adults: </p>
                        {house.adultsNumber}
                      </div>
                      <span className='inner'>
                        <p> Seniors: </p>
                        {house.seniorsNumber}
                      </span>
                    </span>
                  </div>
                  <div className='container'>
                    <h4 align='left'>Members:</h4>
                  </div>
                  <div>
                    {house.members.map((m, i) => (
                      <div style={{'text-indent': '20px'}} key={i}>
                        <p align='left'>{m.username} {m.administrator ? <icon className='glyphicon glyphicon-user' /> : ''} </p>
                      </div>
                    ))}
                    <br />
                  </div>
                </div>
              ))}
            </div>
          )
        }}
      />
    )}
  />
)
