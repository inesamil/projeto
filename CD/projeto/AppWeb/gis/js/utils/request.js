import fetch from 'isomorphic-fetch'
import { makeCancellable } from './promises'

export function request (url, options) {
  return makeCancellable(fetch(url, options))
    .then(resp => {
      const ct = resp.headers.get('content-type') || ''
      if (resp.status >= 200 && resp.status < 300) {
        if (ct === 'application/vnd.siren+json' || ct === 'application/home+json') {
          return resp.json().then(json => [resp, json])
        }
      } else if (ct === 'application/problem+json') {
        return resp.json().then(json => {
          throw new Error(json.detail)
        })
      }
      throw new Error(`Unexpected content type ${ct}`)
    })
}
