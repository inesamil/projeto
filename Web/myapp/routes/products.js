var express = require('express')
var router = express.Router()

/* GET Lists */
router.get('/:name', function(req, res, next) {
    const context = {
        product: req.params.name,
        products: [
            {
                productName: `Leite UHT Magro`,
                marca: 'Mimosa',
                total: 5,
                locals: [
                    {
                        localName:'Fridge',
                        units: 2
                    },
                    {
                        localName:'Cabinet1',
                        units: 2
                    },
                    {
                        localName:'Cabinet2',
                        units: 1
                    }
                    
                ]
            },
            {
                productName: `Leite UHT Meio Gordo`,
                marca: 'Mimosa',
                total:1,
                locals: [
                    {
                        localName:'Cabinet2',
                        units: 1
                    }
                    
                ]
            }
          ]
    }
    res.render('products', context)
})
module.exports = router;