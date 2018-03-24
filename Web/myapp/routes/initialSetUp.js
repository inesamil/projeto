var express = require('express');
var router = express.Router();

/* GET initial setup - family members */ 
router.get('/familyMembers', function(req, res, next){
  const context = {
    title: 'Family Members',
    familyMembers: [
        {memberName: 'Babies (0-3 years)'},
        {memberName: 'Children (4-14 years)'},
        {memberName: 'Adults (15-65 years)'},
        {memberName: 'Seniors (65+ years)'}
    ]
  }
  res.render('initialSetUp_familyMembers', context)
})

/* GET initial setup - allergies */ 
router.get('/allergies', function(req, res, next){
  const context = {
    allergens: [
        {allergenName: 'Shelfish'},
        {allergenName: 'Soy'}
    ]
  }
  res.render('initialSetUp_allergies', context)
})

/* GET initial setup - house */ 
router.get('/house', function(req, res, next){
  const context = {
    allergens: [
        {allergenName: 'Shelfish'},
        {allergenName: 'Soy'}
    ]
  }
  res.render('initialSetUp_house', context)
})
module.exports = router;