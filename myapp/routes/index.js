var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  const context = {
    title: 'Houses',
    houses: [
      {
        houseName: `Gordon's`,
        members: [{member: 'johngordon'}, {member: 'laragordon'}]
      },
      {
        houseName: `Smith's`,
        members: [{member: 'johngordon'}, {member: 'laragordon'}]
      },
      {
        houseName: `Gordon's HolidayInn`,
        members: [{member: 'johngordon'}, {member: 'laragordon'}]
      }
    ]
  }
  res.render('houses', context);
});

router.get('/login', function(req, res, next) {
  const context = {
    title: 'Login', 
    layout: '/layouts/simpleLayout',
    headerBtn2Action: '/signup',
    headerBtn2: `Don't have an account ?`
  }
  res.render('login', context);
});

router.get('/signup', function(req, res, next) {
  const context = {
    title: 'Register', 
    layout: '/layouts/simpleLayout',
    headerBtn2Action: '/login',
    headerBtn2: `Already have an account ?`
  }
  res.render('register', context);
});


module.exports = router;
