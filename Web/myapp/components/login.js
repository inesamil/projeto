import React from 'react'
import { Redirect } from 'react-router-dom'
import URITemplate from 'urijs/src/URITemplate'
import Error from './error'
import { request } from '../utils/request'

export default class Login extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      urlTempl: new URITemplate(this.props.urlTempl),
      redirectToReferrer: false
    }
    this.login = this.login.bind(this)
    this.handleChange = this.handleChange.bind(this)
  }

  login () {
    if (this.promise) {
      this.promise.cancel()
    }
    this.props.onLogin(this.state.username, this.state.password)
    const authorization = this.props.getAuthorization()
    const options = {
      method: 'GET',
      headers: {
        'Authorization': authorization
      }
    }
    const url = this.state.urlTempl.expand({ username: this.state.username })
    this.promise = request(url, options)
      .then(([resp, json]) => {
        if (resp.status === 200) {
          this.setState({
            redirectToReferrer: true,
            url,
            error: undefined
          })
          this.promise = undefined
          return null
        }
        this.props.signout()
        throw new Error('Cannot verify your credentials.')
      })
      .catch(error => {
        this.props.signout()
        this.setState({ error })
        this.promise = undefined
        return null
      })
  }

  handleChange (event) {
    this.setState({
      [event.target.name]: event.target.value
    })
  }

  componentDidUpdate () {
    if (this.state.redirectToReferrer) {
      this.props.onUserUrl(this.state.url)
    }
  }

  componentWillUnmount () {
    if (this.promise) {
      this.promise.cancel()
    }
  }

  render () {
    const from = this.props.previousLocation || '/'

    if (this.state.redirectToReferrer) {
      return <Redirect to={from} />
    }

    return (
      <div>
        {this.state.error ? <Error error={this.state.error} /> : ''}
        <p />
        <div>
          Username <input type='text' name='username' onChange={this.handleChange} />
        </div>
        <p />
        <div>
          Password <input type='password' name='password' onChange={this.handleChange} />
        </div>
        <p />
        <div>
          <input type='submit' value='Log in' onClick={this.login} />
        </div>
      </div>
    )
  }
}
