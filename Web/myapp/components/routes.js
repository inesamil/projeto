import React from 'react'
import { BrowserRouter, Route, Redirect, Switch } from 'react-router-dom'
import URI from 'urijs'
import URITemplate from 'urijs/src/URITemplate'

import Home from './home'
import Login from './login'
import Navigation from './navigation'
import PrivateRoute from './privateRoute'

const home = '/'
const loginTempl = new URITemplate('/login/{url}')
const allergiesTempl = new URITemplate('/allergies/{url}')
const houseAllergiesTempl = new URITemplate('/houseAllergies/{url}')
const houseAllergiesItemsTempl = new URITemplate('/houseAllergiesItems/{url}')
const categoriesTempl = new URITemplate('/categories/{url}')
const categoryTempl = new URITemplate('/category/{url}')
const houseTempl = new URITemplate('/house/{url}')
const houseMembersTempl = new URITemplate('/house-members/{url}')
const invitationsTempl = new URITemplate('/invitations/{url}')
const listsTempl = new URITemplate('/lists/{url}')
const listTempl = new URITemplate('/list/{url}')
const listsProductsTempl = new URITemplate('/list-products/{url}')
const productsTempl = new URITemplate('/products/{url}')
const productTempl = new URITemplate('/product/{url}')
const stockItemsTempl = new URITemplate('/stock-items/{url}')
const stockItemTempl = new URITemplate('/stock-item/{url}')
const stockItemAllergiesTempl = new URITemplate('/stock-item-allergies/{url}')
const movementsTempl = new URITemplate('/movements/{url}')
const storagesTempl = new URITemplate('/storages/{url}')
const storageTempl = new URITemplate('/storage/{url}')
const stockItemsTempl = new URITemplate('/stock-items/{url}')
const usersTempl = new URITemplate('/users/{url}')
const userTempl = new URITemplate('/user/{url}')
const userHousesTempl = new URITemplate('/user-houses/{url}')
const userListsTempl = new URITemplate('/user-lists/{url}')

// Keys
const authKey = 'auth'
const userUrlKey = 'userUrl'

//TODO : Alterar
export default class extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      home: this.props.home
    }
    this.signin = this.signin.bind(this)
  }

  signin (username, password) {
    const authorization = `${this.state.home.authSchema} ${Buffer.from(`${username}:${password}`).toString('base64')}`
    window.sessionStorage.setItem(authKey, authorization)
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
            onHomeClick={() => window.location.assign(home)}
            isAuthenticated={this.isAuthenticated}
            onLogin={() => window.location.assign(loginTempl.expand({ url: this.state.userUrlTempl }))}
            onLogout={() => {
              this.signout()
              this.removeUserUrl()
              window.location.replace(home)
            }} />
          <Switch>
            <Route exact path='/login' render={({ match, history }) => {
              if (!this.isAuthenticated()) {
                return (
                  <Login
                    urlTempl={this.state.home.hrefTempl}
                    previousLocation={history.location.state}
                    onLogin={this.signin}
                    getAuthorization={this.getAuthorization}
                    signout={this.signout}
                    onUserUrl={this.setUserUrl} />
                )
              } else {
                return (
                  <Redirect
                    to={home} />)
              }
            }} />
            <PrivateRoute exact path='/user/:url' isAuthenticated={this.isAuthenticated} component={User} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                onUserDelete: () => {
                  this.signout()
                  window.location.replace(home)
                },
                onPasswordUpdate: ({ username, password }) => {
                  this.signout()
                  this.signin(username, password)
                },
                onChecklistsClick: url => history.push(checklistsTempl.expand({ url })),
                onTemplatesClick: url => history.push(templatesTempl.expand({ url }))
              }
            }} />
            <PrivateRoute exact path='/templates/:url' isAuthenticated={this.isAuthenticated} component={Templates} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                redirectToTemplate: url => history.push(templateTempl.expand({ url })),
                redirectToUser: url => window.location.replace(userTempl.expand({ url }))
              }
            }} />
            <PrivateRoute exact path='/template/:url' isAuthenticated={this.isAuthenticated} component={Template} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                redirectToTemplates: url => window.location.replace(templatesTempl.expand({ url })),
                redirectToTemplateItem: url => history.push(templateItemTempl.expand({ url })),
                redirectToTemplateItems: url => history.push(templateItemsTempl.expand({ url })),
                redirectToChecklist: url => history.push(checklistTempl.expand({ url }))
              }
            }} />
            <PrivateRoute exact path='/template-items/:url' isAuthenticated={this.isAuthenticated} component={TemplateItems} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                onTemplateItemClick: url => history.push(templateItemTempl.expand({ url })),
                redirectToTemplateItem: url => history.push(templateItemTempl.expand({ url })),
                redirectToTemplate: url => window.location.replace(templateTempl.expand({ url }))
              }
            }} />
            <PrivateRoute exact path='/template-item/:url' isAuthenticated={this.isAuthenticated} component={TemplateItem} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                redirectToTemplateItems: url => history.push(templateItemsTempl.expand({ url }))
              }
            }} />
            <PrivateRoute exact path='/checklists/:url' isAuthenticated={this.isAuthenticated} component={Checklists} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                onChecklistClick: url => history.push(checklistTempl.expand({ url })),
                redirectToUser: url => window.location.replace(userTempl.expand({ url }))
              }
            }} />
            <PrivateRoute exact path='/checklist/:url' isAuthenticated={this.isAuthenticated} component={Checklist} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                onTemplateClick: url => history.push(templateTempl.expand({ url })),
                onChecklistItemClick: url => history.push(checklistItemTempl.expand({ url })),
                onChecklistItemsClick: url => history.push(checklistItemsTempl.expand({ url })),
                redirectToChecklists: url => window.location.replace(checklistsTempl.expand({ url }))
              }
            }} />
            <PrivateRoute exact path='/checklistitems/:url' isAuthenticated={this.isAuthenticated} component={ChecklistItems} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                onChecklistItemClick: url => history.push(checklistItemTempl.expand({ url })),
                redirectToChecklist: url => window.location.replace(checklistTempl.expand({ url }))
              }
            }} />
            <PrivateRoute exact path='/checklistitem/:url' isAuthenticated={this.isAuthenticated} component={ChecklistItem} componentProps={({ match, history }) => {
              return {
                url: URI.decode(match.params.url),
                getAuthorization: this.getAuthorization,
                redirectToChecklistItems: url => window.location.replace(checklistItemsTempl.expand({ url }))
              }
            }} />
            <Route exact path='/' render={({ match, history }) =>
              <Home
                home={this.state.home}
                isAuthenticated={this.isAuthenticated}
                onProfileClick={() => history.push(userTempl.expand({ url: this.getUserUrl() }))} />
            } />
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
