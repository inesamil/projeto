var express = require('express');
var router = express.Router();

/* GET user with username 'username' */ 
router.get('/:username', function(req, res, next) {
  const context = {
    title: 'Profile',
    userInfo: {
      basicInfo: {
        fullname: 'Lara Smith',
        email: 'lara@gmail.com',
        username: 'larasmith',
        birthday: '2000-01-01'
      }
    }
  }
  res.render('profile', context)
})
module.exports = router;
