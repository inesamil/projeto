var express = require('express')
var router = express.Router()

/* GET product name */
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

/* GET product name specify*/
router.get('/:name/:specifyproduct', function(req, res, next) {
    const context = {
        product: req.params.name,
        productName: req.params.specifyproduct,
        units: 5,
        quantity: '1L',
        allergens: {
            title: 'Allergens',
            pdtAllergens: [
                {
                    allergen:'milk'
                },
                {
                    allergen:'milk protein'
                }
            ]
        },
        expirationDate: {
            title: 'Expiration Date',
            pdtexpdate: [
                {
                    units: 2,
                    text: 'units expire at',
                    date:'23/03/2018'
                },
                {
                    units: 1,
                    text: 'units expire at',
                    date:'27/03/2018'
                }
            ]
        },
        storages: {
            title: 'Storage',
            pdtstorages: [
                {
                    units: 2,
                    text: 'units in the',
                    local:'fridge'
                },
                {
                    units: 1,
                    text: 'units in the',
                    date:'cabinet1'
                }
            ]
        },
        description: {
            title: 'Description',
            text: 'milk é bue bom, têm de experimentar!!!'
        },
        movements: {
            title: 'Movements',
            text: 'saída do frigorifico dia 13/03/2018 ás 18h00'
        }
    }
    res.render('product', context)
})
module.exports = router;