import React from 'react'
import { Redirect } from 'react-router-dom'
import URITemplate from 'urijs/src/URITemplate'
import Error from './error'
import { request } from '../utils/request'
import '../../static/style.css'

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
    console.log('login()')
    if (this.promise) {
      this.promise.cancel()
    }
    const tokenType = 'Basic'
    const token = `${Buffer.from(`${this.state.username}:${this.state.password}`).toString('base64')}`
    const options = {
      method: 'GET',
      headers: {
        'Authorization': `${tokenType} ${token}`
      }
    }
    const url = this.state.urlTempl.expand({ username: this.state.username })
    console.log(url)
    console.log(options)
    this.promise = request(url, options)
      .then(([resp, json]) => {
        if (resp.status === 200) {
          this.setState({
            redirectToReferrer: true,
            user: {
              token_type: tokenType,
              token,
              username: this.state.username
            },
            error: undefined
          })
          this.promise = undefined
          return null
        }
        throw new Error('Cannot verify your credentials.')
      })
      .catch(error => {
        this.setState({
          redirectToReferrer: false,
          user: undefined,
          error
        })
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
      this.props.onLogin(this.state.user)
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
      console.log('Redirecting..')
      return <Redirect to={from} />
    }

    if (this.state.error) {
      return (
        <Error error={this.state.error} />
      )
    }

    return (
      <div className='login-row'>
        <div className='login-column'>
          <h5>LOGIN</h5>
          <div className='form-group'>
            <label htmlFor='username'>Username</label>
            <input type='text' onChange={this.handleChange} placeholder='Enter Username' name='username' className='form-control' required />
          </div>
          <div className='form-group'>
            <label htmlFor='password'>Password:</label>
            <input type='password' onChange={this.handleChange} placeholder='Enter Password' name='password' className='form-control' required />
          </div>
          <div className='form-group'>
            <input type='submit' onClick={this.login} value='SIGN IN' className='form-control' />
          </div>
        </div>
      </div>
    )
  }
}
