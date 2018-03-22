var express = require('express');
var router = express.Router();

/* GET user with username 'username' */ 
router.get('/:username', function(req, res, next) {
  res.render('profile', )
})
module.exports = router;
