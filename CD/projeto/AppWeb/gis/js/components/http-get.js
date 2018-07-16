import React from 'react'
import { request } from '../utils/request'

export default class extends React.Component {
  constructor (props) {
    super(props)
    this.setUrl = this.setUrl.bind(this)
    this.setQuery = this.setQuery.bind(this)
    this.state = {
      loading: true,
      url: this.props.url
    }
  }

  render () {
    const result = {...this.state, setUrl: this.setUrl, setQuery: this.setQuery}
    return this.props.render(result)
  }

  static getDerivedStateFromProps (nextProps, prevState) {
    if (nextProps.url === prevState.url) return null
    return {
      loading: true,
      url: nextProps.url,
      response: undefined,
      error: undefined,
      json: undefined
    }
  }

  componentDidMount () {
    this.load(this.props.url)
  }

  componentDidUpdate () {
    if (this.state.loading) this.load(this.state.url)
  }

  componentWillUnmount () {
    if (this.promise) {
      this.promise.cancel()
    }
  }

  setQuery (query) {
    this.setState({
      url: this.props.url + '?' + query,
      loading: true
    })
  }

  setUrl (url) {
    this.setState({
      url: url,
      loading: true
    })
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
          loading: false,
          json: json,
          response: resp,
          error: undefined
        })
        this.promise = undefined
        return null
      })
      .catch(error => {
        this.setState({
          loading: false,
          error: error,
          json: undefined,
          response: undefined
        })
        this.promise = undefined
        return null
      })
  }
}
