import React from 'react'

export default ({result, onJson, onLoading, onError}) => {
  if (result.loading) {
    if (onLoading) {
      return onLoading(result.loading)
    } else {
      return <div > <i class='fa fa-spinner fa-spin' style={{'font-size': '50px'}} /> </div>
    }
  } else if (result.error) {
    if (onError) {
      return onError(result.error)
    } else {
      return <div> ERROR: {result.error.message}</div>
    }
  } else if (result.json) {
    return onJson(result.json)
  } else {
    return <div> Oops, invalid state </div>
  }
}
