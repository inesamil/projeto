import React from 'react'
import { BrowserRouter, Route, Redirect, Switch } from 'react-router-dom'
import URI from 'urijs'
import URITemplate from 'urijs/src/URITemplate'

import Home from './home'
import Navigation from './navigation'
import Login from './login'
import PrivateRoute from './privateRoute'
import User from './user'

const homePath = '/'
const userTempl = new URITemplate('/user/{url}')

// Keys
const authKey = 'auth'
const userUrlKey = 'userUrl'

export default class extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      home: this.props.home
    }
    this.setAuthorization = this.setAuthorization.bind(this)
  }

  setAuthorization (user) {
    const authorization = `${user.token_type} ${user.token}`
    window.sessionStorage.setItem(authKey, authorization)
    this.setUserUrl(new URITemplate(this.state.home.hrefTempl).expand({ username: user.username }))
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

  removeUserUrl () {
    window.sessionStorage.removeItem(userUrlKey)
  }

  render () {
    return (
      <BrowserRouter>
        <div>
          <Navigation
            onHomeClick={() => window.location.assign(homePath)}
            isAuthenticated={this.isAuthenticated}
            onLogin={() => window.location.assign('/login')}
            onLogout={() => {
              this.signout()
              this.removeUserUrl()
              window.location.replace(homePath)
            }} />
          <Switch>
            <Route exact path='/login' render={({ history }) => {
              if (!this.isAuthenticated()) {
                return (
                  <Login
                    urlTempl={this.state.home.hrefTempl}
                    previousLocation={history.location.state}
                    onLogin={this.setAuthorization} />
                )
              }
              return (
                <Redirect
                  to={homePath} />
              )
            }} />
            <PrivateRoute exact path='/user/:url' isAuthenticated={this.isAuthenticated} component={User} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization
                // TODO meter os listeners para os links relacionados com o user
              }
            }} />
            <Route exact path='/' render={({ history }) =>
              <Home
                home={this.state.home}
                isAuthenticated={this.isAuthenticated}
                onProfileClick={() => history.push(userTempl.expand({ url: this.getUserUrl() }))} />
            } />
            <Route path='/' render={({ history }) =>
              <div>
                <h2>Route not found</h2>
                <button onClick={() => history.push(homePath)}>Home</button>
              </div>
            } />
          </Switch>
        </div>
      </BrowserRouter>
    )
  }
}
