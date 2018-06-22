import React from 'react'
import Hyperlink from './hyperlink'

export default ({ home, isAuthenticated, onProfileClick }) => (
  <div>
    <div>
      <h2>{home.title}</h2>
      <strong>Created by: </strong><Hyperlink name={home.authorLink} onClick={() => window.open(home.authorLink)} />
    </div>
    <p />
    <div>
      {isAuthenticated()
        ? <button onClick={onProfileClick}>Go to user</button>
        : ''}
    </div>
  </div>
)
