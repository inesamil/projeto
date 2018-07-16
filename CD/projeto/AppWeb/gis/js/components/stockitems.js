import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Error from './error'
import { request } from '../utils/request'
import { mapSirenToUserHouses, mapSirenToStockItems } from '../utils/mapHypermedia'

class StockItems extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      selectedValue: 0,
      stockitems: undefined,
      error: undefined
    }
    this.load = this.load.bind(this)
    this.onChange = this.onChange.bind(this)
    this.load(this.props.houses[0].itemsHref)
  }

  load (url) {
    if (this.promise) {
      this.promise.cancel()
    }
    const options = {
      method: 'GET',
      headers: {
        'Authorization': this.props.getAuthorization ? this.props.getAuthorization() : undefined
      }
    }
    this.promise = request(url, options)
      .then(([resp, json]) => {
        this.setState({
          stockitems: mapSirenToStockItems(json).stockItems,
          error: undefined
        })
        this.promise = undefined
        return null
      })
      .catch(error => {
        this.setState({
          stockitems: undefined,
          error
        })
        this.promise = undefined
        return null
      })
  }

  onChange (event) {
    const value = event.target.value
    this.setState({ selectedValue: value })
    this.load(this.props.houses[value].itemsHref)
  }

  componentWillUnmount () {
    if (this.promise) {
      this.promise.cancel()
    }
  }

  render () {
    return (
      <div>
        <h2 align='center'>Itens</h2>
        <select className='select' onChange={this.onChange} value={this.state.selectedValue}>
          {this.props.houses.map((house, idx) => (
            <option key={idx} value={idx}>{house.houseName}</option>
          ))}
        </select>
        {this.state.stockitems !== undefined
          ? <div id='myUL'>
            {this.state.stockitems.map((stockitem, idx) => (
              <li key={idx} id='divCard' className='card' style={{listStyleType: 'none'}}>
                <a onClick={() => this.props.redirectToStockItem(stockitem.href)} style={{color: 'black'}} >
                  <h3 className='container'>{stockitem.productName} {stockitem.variety}</h3>
                  <p style={{textIndent: '4%'}}>{stockitem.brand}</p>
                  <h5 style={{textAlign: 'right', 'padding-bottom': '10px', 'padding-right': '10px'}}>Qtd. {stockitem.quantity}</h5>
                </a>
              </li>
            ))}
          </div>
          : ''
        }
      </div>
    )
  }
}

export default ({ url, getAuthorization, redirectToStockItem }) => (
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
        onJson={json => (
          <StockItems
            getAuthorization={getAuthorization}
            houses={mapSirenToUserHouses(json).userHouses}
            redirectToStockItem={redirectToStockItem} />
        )}
      />
    )}
  />
)
