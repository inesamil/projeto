import React from 'react'

export default ({ onHomeClick, isAuthenticated, onLogin, onLogout }) => (
  <header class='header'>
    <button onClick={onHomeClick}>Home</button>
    &nbsp;
    {isAuthenticated()
      ? <button onClick={onLogout}>Logout</button>
      : (
        <div>
          <button onClick={onLogin}>Login</button>
          <h1 class='header-title'>Gestão Inteligente de Stocks</h1>
        </div>
      )
    }
  </header>
)
