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
  res.render('index', context);
});

/* GET Categories' Products */
router.get('/categories/:name', function(req, res, next) {
  const context = {
    title: req.params.name,
    products: [
      {
        productName: "Milk"
      },
      {
        productName: "Yogurt"
      },
      {
        productName: "Cheese"
      }
    ]
  }
  res.render('categoriesProducts', context);
});

/* GET Invitations */
router.get('/invitations', function(req, res, next) {
  const context = {
    invitations: [
      {
        username: "larasmith",
        houseName: "Smith's"
      }
    ]
  }
  res.render('invitations', context);
});

/* GET Login Form */
router.get('/login', function(req, res, next) {
  const context = {
    title: 'Login', 
    layout: '/layouts/simpleLayout',
    headerBtn2Action: '/signup',
    headerBtn2: `Don't have an account ?`
  }
  res.render('login', context);
});

/* GET Sign Up Form */
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
