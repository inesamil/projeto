import React from 'react'

export default ({ onHomeClick, isAuthenticated, onLogin, onLogout }) => (
  <header className='header'>
    <button onClick={onHomeClick}>Home</button>
    &nbsp;
    {isAuthenticated()
      ? (
        <div>
          <div id='mySidenav' class='sidenav'>
            <a href='/' id='home'>Home</a>
            <a href='/lists' id='lists'>Lists</a>
            <a href='/categories' id='categories'>Categories</a>
            <a href='#' id='recipes'>Recipes</a>
          </div>

          <nav className='nav'>
            <h1 className='header-title'>Gestão Inteligente de Stocks</h1>
            <div className='right-ctn'>
              <div><a class='menuBar-btn2' href='/users/{{username}}'><span class='glyphicon glyphicon-user' /> Profile</a></div>
              <div><a class='menuBar-btn2' href='/logout'><span class='glyphicon glyphicon-log-out' /> Logout</a></div>
            </div>
          </nav>
        </div>
      )
      : (
        <div>
          <nav className='nav'>
            <div className='navbar-header'>
              <p className='navbar-brand'>Gestão Inteligente de Stocks</p>
            </div>
            <div class='right-ctn'>
              <div><a class='menuBar-btn2' href='/login'><span className='glyphicon glyphicon-log-in' /> Login</a></div>
            </div>
          </nav>
          <div id='mySidenav' className='sidenav'>
            <a href='/' id='home'>Home</a>
          </div>
        </div>
      )
    }
  </header>
)
