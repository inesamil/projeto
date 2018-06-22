import React from 'react'

export default ({ onHomeClick, isAuthenticated, onLogin, onLogout }) => (
  <div>
    <button onClick={onHomeClick}>Home</button>
    &nbsp;
    {isAuthenticated()
      ? <button onClick={onLogout}>Logout</button>
      : <button onClick={onLogin}>Login</button>
    }
  </div>
)
