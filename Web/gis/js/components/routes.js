import React from 'react'
import { BrowserRouter, Route, Redirect, Switch } from 'react-router-dom'
import URI from 'urijs'
import URITemplate from 'urijs/src/URITemplate'

import Home from './home'
import Navigation from './navigation'
import Login from './login'
import PrivateRoute from './privateRoute'
import User from './user'
import Categories from './categories'
import Products from './products'
import Lists from './lists'
import List from './list'
import Houses from './houses'
import Storages from './storages'
import Allergies from './allergies'
import StockItem from './stockItem'

/* import StockItems from './stockItems' */

const home = '/'
const loginTempl = new URITemplate('/login/{url}')
const stockItemsTempl = new URITemplate('/stock-items/{url}')
const stockItemTempl = new URITemplate('/stock-item/{url}')
const userHousesTempl = new URITemplate('/houses/{url}')
const houseAllergiesTempl = new URITemplate('/allergies/{url}')
const storagesTempl = new URITemplate('/storages/{url}')
const userTempl = new URITemplate('/user/{url}')
const userListsTempl = new URITemplate('/lists/{url}')
const listTempl = new URITemplate('/list/{url}')
const categoriesTempl = new URITemplate('/categories/{url}')
const productsTempl = new URITemplate('/products/{url}')

// Keys
const authKey = 'auth'
const userUrlKey = 'userUrl'
const userUsernameKey = 'userUsername'

export default class extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      index: this.props.home
    }
    this.setAuthorization = this.setAuthorization.bind(this)
  }

  setAuthorization (user) {
    const authorization = `${user.token_type} ${user.token}`
    window.sessionStorage.setItem(authKey, authorization)
    this.setUserUrl(new URITemplate(this.state.index.userHrefTempl).expand({ username: user.username }))
    this.setUserUsername(user.username)
  }

  signout () {
    window.sessionStorage.removeItem(authKey)
  }

  getAuthorization () {
    return window.sessionStorage.getItem(authKey)
  }

  isAuthenticated () {
    return window.sessionStorage.getItem(authKey) !== null
  }

  setUserUrl (url) {
    window.sessionStorage.setItem(userUrlKey, url)
  }

  getUserUrl () {
    return window.sessionStorage.getItem(userUrlKey)
  }

  setUserUsername (username) {
    window.sessionStorage.setItem(userUsernameKey, username)
  }

  getUserUsername () {
    return window.sessionStorage.getItem(userUsernameKey)
  }

  removeUserUrl () {
    window.sessionStorage.removeItem(userUrlKey)
  }

  removeUserUsername () {
    window.sessionStorage.removeItem(userUsernameKey)
  }

  render () {
    return (
      <BrowserRouter>
        <div>
          <Navigation
            onHomeClick={() => window.location.assign(home)}
            isAuthenticated={this.isAuthenticated}
            onLogin={() => window.location.assign('/login')}
            onLogout={() => {
              this.signout()
              this.removeUserUrl()
              window.location.replace(home)
            }}
            userUrl={userTempl.expand({ url: this.getUserUrl() })} />
          <Switch>
            <Route exact path='/login' render={({ history }) => {
              if (!this.isAuthenticated()) {
                return (
                  <Login
                    urlTempl={this.state.index.userHrefTempl}
                    previousLocation={history.location.state}
                    getAuthorization={this.getAuthorization}
                    onLogin={this.setAuthorization} />
                )
              }
              return (
                <Redirect
                  to={home} />
              )
            }} />
            <PrivateRoute exact path='/allergies/:url' isAuthenticated={this.isAuthenticated} component={Allergies} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization
              }
            }} />
            <PrivateRoute exact path='/houses/:url' isAuthenticated={this.isAuthenticated} component={Houses} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                redirectToStorages: url => history.push(storagesTempl.expand({ url })),
                redirectToAllergies: url => history.push(houseAllergiesTempl.expand({ url }))
              }
            }} />
            <PrivateRoute exact path='/categories/:url' isAuthenticated={this.isAuthenticated} component={Categories} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                productsUrltempl: productsTempl
              }
            }} />
            <PrivateRoute exact path='/products/:url' isAuthenticated={this.isAuthenticated} component={Products} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization
              }
            }} />
            <PrivateRoute exact path='/user/:url' isAuthenticated={this.isAuthenticated} component={User} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization
              }
            }} />
            <PrivateRoute exact path='/lists/:url' isAuthenticated={this.isAuthenticated} component={Lists} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                redirectToList: url => history.push(listTempl.expand({ url }))
              }
            }} />
            <PrivateRoute exact path='/list/:url' isAuthenticated={this.isAuthenticated} component={List} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization
              }
            }} />
            <PrivateRoute exact path='/storages/:url' isAuthenticated={this.isAuthenticated} component={Storages} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization
              }
            }} />
            <PrivateRoute exact path='/stock-item/:url' isAuthenticated={this.isAuthenticated} component={StockItem} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization
              }
            }} />
            <Route exact path='/' render={({ history }) => {
              if (this.isAuthenticated()) {
                return (
                  <Home
                    onProfileClick={() => history.push(userTempl.expand({ url: this.getUserUrl() }))}
                    onStockItemClick={() => history.push(stockItemsTempl.expand({ url: new URITemplate(this.state.index.housesHrefTempl).expand({ username: this.getUserUsername() }) }))}
                    onHousesClick={() => history.push(userHousesTempl.expand({ url: new URITemplate(this.state.index.housesHrefTempl).expand({ username: this.getUserUsername() }) }))}
                    onListsClick={() => history.push(userListsTempl.expand({ url: new URITemplate(this.state.index.listsHrefTempl).expand({ username: this.getUserUsername() }) }))} />
                )
              }
              return (
                <Redirect
                  to='/login' />
              )
            }} />
            <Route path='/' render={({ history }) =>
              <div>
                <h2>Route not found</h2>
                <button onClick={() => history.push(home)}>Home</button>
              </div>
            } />
          </Switch>
        </div>
      </BrowserRouter>
    )
  }
}
