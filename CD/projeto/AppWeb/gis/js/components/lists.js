import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Error from './error'
import { mapSirenToLists } from '../utils/mapHypermedia'

class Lists extends React.Component {
  constructor (props) {
    super(props)
    this.state = {

    }
  }

  render () {
    return (
      <div>
        <h2 align='center'>Listas</h2>
        <div id='myUL'>
          {this.props.lists.map((list, idx) => (
            <div key={idx}>
              <div id='divCard' className='card' onClick={() => this.props.redirectToList(list.href)}>
                <div style={{textAlign: 'center'}}>
                  <h4 style={{fontSize: '30px', color: 'black', paddingLeft: '10px'}}> {list.name}</h4>
                  <span className='glyphicon glyphicon-home' style={{'font-size': '15px', display: 'inline-block', marginRight: '10px', color: 'rgba(253, 73, 82, 0.8)'}} />
                  <p style={{display: 'inline-block', align: 'left'}}> {list.houseName}</p>
                </div>
              </div>
              <br />
            </div>))}
        </div>
      </div>
    )
  }
}

export default ({ url, getAuthorization, redirectToList }) => (
  <HttpGet
    url={url}
    getAuthorization={getAuthorization}
    redirectToList={redirectToList}
    render={result => (
      <HttpGetSwitch
        result={result}
        onError={error => (
          <Error
            error={error} />
        )}
        onJson={json => (
          <Lists
            lists={mapSirenToLists(json).lists}
            redirectToList={redirectToList} />
        )}
      />
    )}
  />
)
