var express = require('express')
var router = express.Router()

/* GET Lists */
router.get('', function(req, res, next) {
    const context = {
        lists: [
            {
                listName: `Groceries List`,
            },
            {
                listName: `Dark List`,
            },
            {
                listName: `Groceries List`,
            }
          ]
    }
    res.render('lists', context)
})

router.get('/:listname', function(req, res, next) {
    const items = [
        {item: 'Milk', units: '2'}, 
        {item: 'Sugar', units: '1'},
        {item: 'Egss', units: '12'},
        {item: 'Yogurt', units: '5'}
    ]
    const context = {
        title: 'Groceries List',
        'items': items
    }
    res.render('list', context)
})

module.exports = router;