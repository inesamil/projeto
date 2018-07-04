import React from 'react'

export default ({ onHomeClick, isAuthenticated, onLogin, onLogout, userUrl }) => (
  <header className='header'>
    {isAuthenticated()
      ? (
        <div>
          <nav className='nav'>
            <div className='navbar-header'>
              <img style={{width: '45px'}} src='../static/images/logo.png' />
              <p className='navbar-brand' style={{inline: 'true'}}> Smart Stocks </p>
            </div>
            <div className='right-ctn'>
              <div><a className='menuBar-btn2' href={userUrl}><span className='glyphicon glyphicon-user' /> Profile</a></div>
              <div><a className='menuBar-btn2' onClick={() => onLogout()}><span className='glyphicon glyphicon-log-out' /> Logout</a></div>
            </div>
          </nav>
        </div>
      )
      : (
        <div>
          <nav className='nav'>
            <div className='navbar-header'>
              <p className='navbar-brand'>Smart Stocks</p>
            </div>
            <div className='right-ctn'>
              <div><a className='menuBar-btn2' onClick={() => onLogin()}><span className='glyphicon glyphicon-log-in' /> Login</a></div>
            </div>
          </nav>
        </div>
      )
    }
  </header>
)
