import React from 'react'

export default ({ onProfileClick, onStockItemClick, onHousesClick, onListsClick }) => (

  <div className='container center'>
    <div className='row section-box'>
      <div className='col-sm-3 text-center' id='stockItemTile' onClick={onStockItemClick}>
        <div className='ib'>
          <img src='../static/images/kitchen_web_hi_res_512.png' style={{width: '60px'}} />
          <br />
          <h2>Stock Items</h2>
        </div>
      </div>
      <div className='col-sm-6' id='housesTile' onClick={onHousesClick}>
        <div className='ib'>
          <img src='../static/images/home_web_hi_res_512.png' style={{width: '60px'}} />
          <br />
          <h2>Houses</h2>
        </div>
      </div>
    </div>
    <div className='row section-box'>
      <div className='col-sm-6' id='profileTile' onClick={onProfileClick}>
        <div className='ib'>
          <img src='../static/images/person_web_hi_res_512.png' style={{width: '60px'}} />
          <br />
          <h2>Profile</h2>
        </div>
      </div>
      <div className='col-sm-3 text-center' id='listsTile' onClick={onListsClick}>
        <div className='ib'>
          <img src='../static/images/list_web_hi_res_512.png' style={{width: '60px'}} />
          <br />
          <h2>Lists</h2>
        </div>
      </div>
    </div>
  </div>
)
