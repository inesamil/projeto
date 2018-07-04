import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Error from './error'
import { mapSirenToUserHouses } from '../utils/mapHypermedia'

export default ({ url, getAuthorization, storagesUrltempl, allergiesUrlTempl }) => (
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
              {userHouses.userHouses.map((house, idx) => (
                <div className='card text-center' key={idx}>
                  <div style={{'text-align': 'center'}}>
                    <br />
                    <span className='glyphicon glyphicon-home' style={{'font-size': '100px', color: '#ff9800'}} />
                    <h3 style={{'font-size': '30px;'}}> {house.houseName}</h3>
                    <br />

                    <button className='button' href={storagesUrltempl.expand({ url: house.storagesLink.href })}>
                      <h4> Storages</h4>
                    </button>
                    <button className='button' href={allergiesUrlTempl.expand({ url: house.houseAllergiesLink.href })}>
                      <h4> Allergies</h4>
                    </button>
                  </div>
                  <div>
                    <h4 className='text-center'>Characteristcs:</h4>
                    <span display='inline'>
                      <span display='inline-block'>
                        <h4> Babies: </h4>
                        {house.babiesNumber}
                      </span>
                      <div className='d-inline-block'>
                        <p> Children: </p>
                        {house.childrenNumber}
                      </div>
                      <div className='d-inline-block'>
                        <p> Adults: </p>
                        {house.adultsNumber}
                      </div>
                      <span className='d-inline-block'>
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
