import React from 'react'
import HttpGet from './http-get'
import HttpGetSwitch from './http-get-switch'
import Error from './error'
import { mapSirenToCategories } from '../utils/mapHypermedia'

export default ({ url, getAuthorization, productsUrltempl }) => (
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
        onJson={json => {
          const categories = mapSirenToCategories(json)
          return (
            <div class='grid-container'>
              {categories.categories.map((category, idx) => (
                <div class='grid-item' key={idx}>
                  <a class='category-btn' href={productsUrltempl.expand({ url: category.productsCategoryLink.href })}>{category.name}</a>
                </div>
              ))}
            </div>
          )
        }}
      />
    )}
  />
)
