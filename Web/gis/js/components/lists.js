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
        <h1>Lists</h1>
        <div id='myUL'>
          {this.props.lists.map((list, idx) => (
            <div key={idx}>
              <li id='divCard' className='card' onClick={() => this.props.redirectToList(list.href)}>
                <div style={{textAlign: 'center'}}>
                  <h4 style={{fontSize: '30px', display: 'inline-block', color: 'black'}}> {list.name}</h4>
                  <p style={{display: 'inline-block', align: 'left'}}>{list.houseName}</p>
                </div>
              </li>
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
