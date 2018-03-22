var express = require('express')
var router = express.Router()

/* GET Categories */
router.get('/', function(req, res, next) {
  res.render('categories', { title: 'Categories', categories: [
      {name: 'diary'}, {name: 'drinks'}, {name: 'sauces'}, {name: 'frozen'}, {name: 'fruits'}, {name: 'spices'}]})
})


module.exports = router;