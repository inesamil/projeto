/* export function mapJsonHomeToIndex (json) {
  const userResource = json.resources['/rel/user']
  const hrefTempl = userResource.hrefTemplate
  const hints = userResource.hints
  // const authSchema = hints.authSchemes.find(schema => schema.scheme === 'Basic')
  const index = {
    title: json.api.title,
    authorLink: json.api.links.author,
    hrefTempl
    // authSchema: authSchema.scheme
  }
  return index
} */

// index
export function mapJsonHomeToIndex (json) {
  const houseResource = json.resources['/rel/house']
  const usersSearchResource = json.resources['/rel/users/search']
  const usersResource = json.resources['/rel/users']
  const userResource = json.resources['/rel/user']
  const categoriesResource = json.resources['/rel/categories']
  const allergiesResource = json.resources['/rel/allergies']
  const housesResource = json.resources['/rel/houses']
  const listsResource = json.resources['/rel/lists']
  const invitationsResource = json.resources['/rel/invitations']

  const houseHref = houseResource.href
  const usersHref = usersResource.href
  const usersSearchHrefTempl = usersSearchResource.hrefTemplate
  const userHrefTempl = userResource.hrefTemplate
  const categoriesHref = categoriesResource.href
  const allergiesHref = allergiesResource.href
  const housesHrefTempl = housesResource.hrefTemplate
  const listsHrefTempl = listsResource.hrefTemplate
  const invitationsHrefTempl = invitationsResource.hrefTemplate

  const index = {
    title: json.api.title,
    authorLink: json.api.links.author,
    houseHref,
    usersHref,
    usersSearchHrefTempl,
    userHrefTempl,
    categoriesHref,
    allergiesHref,
    housesHrefTempl,
    listsHrefTempl,
    invitationsHrefTempl
  }
  return index
}

//Allergies
export function mapSirenToAllergies (json) {
  const allergiesLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const allergies = {
    allergies: [],
    selfHref: allergiesLink ? allergiesLink.href : undefined
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const allergy = {
      allergen: properties['allergy-allergen']
    }
    allergies.allergies.push(allergy)
  })
  return allergies
}

//HouseAllergies
export function mapSirenToHouseAllergies (json) {
  const houseAllergiesLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const houseLink = josn.links.find(link => link.rel.find(rel => rel === 'related'))
  const houseAllergies = {
    houseAllergies: [],
    selfHref: houseAllergiesLink ? houseAllergiesLink.href : undefined,
    houseHref: houseLink ? houseLink.href : undefined,
    actions: {
      putHouseAllergies: json.actions.find(action => action.name === 'update-house-allergies'),
      deleteHouseAllergies: json.actions.find(action => action.name === 'delete-house-allergies')
    }
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const houseAllergy = {
      houseId: properties['house-id'],
      allergicsNumber: properties['allergics-number'],
      allergen: properties['allergy-allergen'],
      actions: {
        putHouseAllergy: json.actions.find(action => action.name === 'update-house-allergy'),
        deleteHouseAllergy: json.actions.find(action => action.name === 'delete-house-allergy')
      },
      href: entity.links.find(link => link.rel.find(rel => rel === 'related'))
    }
    houseAllergies.houseAllergies.push(houseAllergy)
  })
  return houseAllergies
}

//StockItemAllergen
export function mapSirenToStockItemsAllergen (json) {
  const stockItemsAllergenLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const houseAllergiesLink = josn.links.find(link => link.rel.find(rel => rel === 'related'))
  const stockItemsAllergen = {
    stockItemsAllergen: [],
    selfHref: stockItemsAllergenLink ? stockItemsAllergenLink.href : undefined,
    houseAllergiesLink: houseAllergiesLink ? houseAllergiesLink.href : undefined
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const stockItem = {
      houseId: properties['house-id'],
      allergen: properties['allergy-allergen'],
      stockItemId: properties['stock-item-id'],
      productId: properties['product-id'],
      brand: properties['stock-item-brand'],
      conservationStorage: properties['stock-item-conservation-storage'],
      description: properties['stock-item-description'],
      quantity: properties['stock-item-quantity'],
      segment: properties['stock-item-segment'],
      variety: properties['stock-item-variety']
    }
    stockItemsAllergen.stockItemsAllergen.push(stockItem)
  })
  return stockItemsAllergen
}

//Categories
export function mapSirenToCategories (json) {
  const categoriesLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const indexLink = json.links.find(link => link.rel.find(rel => rel === 'index'))
  const categories = {
    categories: [],
    selfHref: categoriesLink ? categoriesLink.href : undefined,
    indexLink: indexLink ? indexLink.href : undefined
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const category = {
      id: properties['category-id'],
      name: properties['category-name'],
      href: entity.links.find(link => link.rel.find(rel => rel === 'self')),
      productsCategoryLink: entity.links.find(link => link.rel.find(rel => rel === 'related'))
    }
    categories.categories.push(category)
  })
  return categories
}

//Category
export function mapSirenToCategory (json) {
  const categoriesEntity = json.entities.find(entity => entity.class.find(elem => elem === 'categories'))
  const categoryLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  return {
    id: json.properties['category-id'],
    name: json.properties['category-name'],
    categoriesHref: categoriesEntity ? categoriesEntity.href : undefined,
    selfHref: categoryLink ? categoryLink.href : undefined
  }
}

//House
export function mapSirenToHouse (json) {
  const categoriesEntity = json.entities.find(entity => entity.class.find(elem => elem === 'categories'))
  const houseLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const indexLink = json.links.find(link => link.rel.find(rel => rel === 'index'))
  const housesLink = josn.links.find(link => link.class.find(c => c === 'houses'))
  const itemsLink = itemsLink.link.find(link => link.class.find(c => c === 'items'))
  const movementsLink = movementsLink.link.find(link => link.class.find(c => c === 'movements'))
  const allergiesLink = allergiesLink.link.find(link => link.class.find(c => c === 'house-allergies'))
  const listsLink = listsLink.link.find(link => link.class.find(c => c === 'lists'))
  const storagesLink = storagesLink.link.find(link => link.class.find(c => c === 'storages'))
  return {
    id: json.properties['house-id'],
    name: json.properties['house-name'],
    characteristics: json.properties['house-characteristics'],
    members: json.properties['house-members'],
    actions: {
      deleteHouse: actions.find(action => action.name === 'delete-house'),
      putHouse: actions.find(action => action.name === 'update-house')
    },
    selfHref: houseLink ? houseLink.href : undefined,
    indexLink: indexLink ? indexLink.href : undefined,
    housesLink: housesLink ?  housesLink.href : undefined,
    itemsLink: itemsLink ? itemsLink.href : undefined,
    movementsLink: movementsLink ? movementsLink.href : undefined,
    allergiesLink: allergiesLink ? allergiesLink.href : undefined,
    listsLink: listsLink ? listsLink.href : undefined,
    storagesLink: storagesLink ? storagesLink.href : undefined
  }
}

//HouseMembers
export function mapSirenToHouseMembers (json) {
  const houseMembersLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const houseLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  const houseMembers = {
    members: [],
    selfHref: houseMembersLink ? houseMembersLink.href : undefined,
    houseLink: houseLink ? houseLink.href :undefined
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const member = {
      houseId: properties['house-id'],
      username: properties['user-username'],
      administrator: properties['household-administrator'],
      href: entity.links.find(link => link.rel.find(rel => rel === 'related')).href
    }
    houseMembers.members.push(member)
  })
  return houseMembers
}

//Invitations
export function mapSirenToInvitations (json) {
  const invitationsLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const indexLink = json.links.find(link => link.rel.find(rel => rel === 'index'))
  const housesLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  const invitations = {
    invitations: [],
    selfHref: invitationsLink ? invitationsLink.href : undefined,
    indexLink: indexLink ? indexLink.href : undefined,
    housesLink: housesLink ? housesLink.href : undefined,    
    actions: {
      postInvitation: json.actions.find(action => action.name === 'invite-user')
    }
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const invitation = {
      houseId: properties['house-id'],
      houseName: properties['house-name'],
      username: properties['user-username'],
      actions: {
        putInvitation: entity.actions.find(action => action.name === 'update-invitation')
      }
    }
    invitations.invitations.push(invitation)
  })
  return invitations
}

//Lists
export function mapSirenToLists (json) {
  const listsLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const houseLink = json.links.find(link => link.rel.find(rel => rel === 'related'))

  const lists = {
    lists: [],
    selfHref: listsLink ? listsLink.href : undefined,
    houseLink: houseLink ? houseLink.href : undefined,
    actions: {
      postList: json.actions.find(action => action.name === 'add-list')
    }
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const list = {
      houseId: properties['house-id'],
      houseName: properties['house-name'],
      id: properties['list-id'],
      name: properties['list-name'],
      type: properties['list-type'],
      username: properties['user-username'] ? properties['user-username'] : undefined,
      shareable: properties['list-shareable'] ? properties['list-shareable'] : undefined,
      actions: {
        putListProduct: entity.actions.find(action => action.name === 'update-list-product')
      },
      href: entity.links.find(link => link.rel.find(rel => rel === 'self')).href
    }
    lists.lists.push(list)
  })
  return lists
}

//List
export function mapSirenToList (json) {
  const listLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const listsLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  const list = {
    houseId: json.properties['house-id'],
    houseName: json.properties['house-name'],
    listId: json.properties['list-id'],
    listName: json.properties['list-name'],
    listType: json.properties['list-type'],
    username: json.properties['user-username'] !== undefined ? json.properties['user-username'] : undefined,
    shareable: json.properties['list-shareable'] !== undefined ? json.properties['list-shareable'] : undefined,
    listProducts: [],
    actions: {
      addListProduct: json.actions.find(action => action.name === 'add-list-product'),
      putList: json.actions.find(action => action.name === 'update-list'),
      deleteList: json.actions.find(action => action.name === 'delete-list')
    },
    listsLink: listsLink ? listsLink.href : undefined,
    selfHref: listLink ? listLink.href : undefined
  }

  json.entities.forEach(entity => {
    const properties = entity.properties
    const listProduct = {
      houseId: properties['house-id'],
      listId: properties['list-id'],
      productId: properties['product-id'],
      productName: properties['product-name'],
      brand: properties['list-product-brand'],
      quantity: properties['list-product-quantity'],
      actions: {
        putListProduct: entity.actions.find(action => action.name === 'update-list-product'),
        deleteListProduct: entity.actions.find(action => action.name === 'delete-list-product')
      }
    }
    list.listProducts.push(listProduct)
  })
  return list
}

//ListProducts
export function mapSirenToListProducts (json) {
  const listProductsLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const listLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  const listProducts = {
    listProducts: [],
    selfHref: listProductsLink ? listProductsLink.href : undefined,
    listLink: listLink ? listLink.href : undefined,
    actions: {
      putListProduct: json.actions.find(action => action.name === 'update-product'),
      deleteListProducts: json.actions.find(action => action.name === 'delete-list-products')
    }
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const listProduct = {
      houseId: properties['house-id'],
      listId: properties['list-id'],
      categoryId: properties['category-id'],
      productId: properties['product-id'],
      productName: properties['product-name'],
      brand: properties['list-product-brand'],
      quantity: properties['list-product-quantity'],
      href: entity.links.find(link => link.rel.find(rel => rel === 'related'))
    }
    listProducts.listProducts.push(listProduct)
  })
  return listProducts
}

//Product
export function mapSirenToProduct (json) {
  const productLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const productsCategoryLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  return {
    categoryId: json.properties['category-id'],
    id: json.properties['product-id'],
    name: json.properties['product-name'],
    edible: json.properties['product-edible'],
    shelflifetime: json.properties['product-shelflifetime'],
    categoriesHref: categoriesEntity ? categoriesEntity.href : undefined,
    selfHref: productLink ? productLink.href : undefined,
    productsCategoryLink: productsCategoryLink ? productsCategoryLink.href : undefined
  }
}

//ProductsCategory
export function mapSirenToProductsCategory (json) {
  const productsCategoryLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const categoryLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  const productsCategory = {
    productsCategory: [],
    selfHref: productsCategoryLink ? productsCategoryLink.href : undefined,
    categoryLink: categoryLink ? categoryLink.href : undefined
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const productCategory = {
      categoryId: properties['category-id'],
      id: properties['product-id'],
      name: properties['product-name'],
      edible: properties['product-edible'],
      shelflifetime: properties['product-shelflifetime'],
      href: entity.links.find(link => link.rel.find(rel => rel === 'self'))
    }
    productsCategory.productsCategory.push(productCategory)
  })
  return productsCategory
}

//AllergiesStockItem
export function mapSirenToAllergiesStockItem (json) {
  const allergiesStockItemLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const stockItemLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  const allergiesStockItem = {
    allergiesStockItem: [],
    selfHref: allergiesStockItemLink ? allergiesStockItemLink.href : undefined,
    stockItemLink: stockItemLink ? stockItemLink.href : undefined
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const allergyStockItem = {
      houseId: properties['house-id'],
      stockItemId: properties['stock-item-id'],
      allergen: properties['allergy-allergen']
    }
    allergiesStockItem.allergiesStockItem.push(allergyStockItem)
  })
  return allergiesStockItem
}

//StockItem
export function mapSirenToStockItem (json) {
  const stockItemLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const stockItemsLink = json.links.find(link => link.class.find(c => c === 'stock-items'))
  const itemAllergiesLink = json.links.find(link => link.class.find(c => c === 'stock-item-allergens'))
  return {
    houseId: json.properties['house-id'],
    stockItemSku: json.properties['stock-item-sku'],
    productId: json.properties['product-id'],
    productName: json.properties['product-name'],
    brand: json.properties['stock-item-brand'],
    conservationStorage: json.properties['stock-item-conservation-storage'],
    description: json.properties['stock-item-description'],
    quantity: json.properties['stock-item-quantity'],
    segment: json.properties['stock-item-segment'],
    variety: json.properties['stock-item-variety'],
    allergensEntity: {
      elements: json.entities.find(entity => entity.class.find(c => c === 'allergens'))
    },
    expirationDatesEntity: {
      elements: json.entities.find(entity => entity.class.find(c => c === 'expiration-dates'))
    },
    storagesEntity: {
      elements: json.entities.find(entity => entity.class.find(c => c === 'storages'))
    },
    movementsEntity: {
      elements: json.entities.find(entity => entity.class.find(c => c === 'movements'))
    },
    selfHref: stockItemLink ? stockItemLink.href : undefined,
    stockItemsLink: stockItemsLink ? stockItemsLink.href : undefined,
    itemAllergiesLink: itemAllergiesLink ? itemAllergiesLink.href : undefined
  }
}

//StockItems
export function mapSirenToStockItems (json) {
  const StockItemsLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const houseLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  const stockItems = {
    stockItems: [],
    selfHref: StockItemsLink ? StockItemsLink.href : undefined,
    houseLink: houseLink ? houseLink.href : undefined,
    actions: {
      postStockItems: json.actions.find(action => action.name === 'add-stock-item')
    }
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const stockItem = {
      houseId: properties['house-id'],
      stockItemSku: properties['stock-item-sku'],
      productId: properties['product-id'],
      productName: properties['product-name'],
      brand: properties['stock-item-brand'],
      conservationStorage: properties['stock-item--conservation-storage'],
      description: properties['stock-item-brand'],
      quantity: properties['stock-item-quantity'],
      segment: properties['stock-item-segment'],
      variety: properties['stock-item-variety'],
      href: entity.links.find(link => link.rel.find(rel => rel === 'self'))
    }
    stockItems.stockItems.push(stockItem)
  })
  return stockItems
}

//Movements
export function mapSirenToMovements (json) {
  const movementsLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const houseLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  const movements = {
    movements: [],
    selfHref: movementsLink ? movementsLink.href : undefined,
    houseLink: houseLink ? houseLink.href : undefined,
    actions: {
      addMovement: json.actions.find(action => action.name === 'add-movement')
    }
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const movement = {
      houseId: properties['house-id'],
      stockItemSku: properties['stock-item-sku'],
      storageId: properties['storage-id'],
      datetime: properties['movement-datetime'],
      type: properties['movement-type'],
      quantity: properties['movement-quantity']
    }
    movements.movements.push(movement)
  })
  return movements
}

//Storages
export function mapSirenToStorages (json) {
  const storagesLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const houseLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  const storages = {
    storages: [],
    selfHref: storagesLink ? storagesLink.href : undefined,
    houseLink: houseLink ? houseLink.href : undefined,
    actions: {
      postStorage: json.actions.find(action => action.name === 'add-storage')
    }
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const storage = {
      houseId: properties['house-id'],
      stockItemSku: properties['stock-item-sku'],
      storageId: properties['storage-id'],
      storageName: properties['storage-name'],
      temperature: properties['storage-temperature'],
      href: entity.links.find(link => link.rel.find(rel => rel === 'self'))
    }
    storages.storages.push(storage)
  })
  return storages
}

//Storage
export function mapSirenToStorage (json) {
  const storageLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const storagesLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  return {
    categoryId: json.properties['category-id'],
    id: json.properties['product-id'],
    name: json.properties['product-name'],
    edible: json.properties['product-edible'],
    shelflifetime: json.properties['product-shelflifetime'],
    actions: {
      putStorage: json.actions.find(action => action.name === 'update-storage'),
      deleteStorage: json.actions.find(action => action.name === 'delete-storage'),
    },
    selfHref: storageLink ? storageLink.href : undefined,
    storagesLink: storagesLink ? storagesLink.href : undefined
  }
}

//UserLists
export function mapSirenToUserLists (json) {
  const userListsLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const userLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  const userLists = {
    userLists: [],
    selfHref: userListsLink ? userListsLink.href : undefined,
    userLink: userLink ? userLink.href : undefined,
    actions: {
      postUserList: json.actions.find(action => action.name === 'add-list')
    }
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const userList = {
      houseId: properties['house-id'],
      houseName: properties['house-name'],
      listId: properties['list-id'],
      listName: properties['list-name'],
      listType: properties['list-type'],
      username: properties['user-username'] ? properties['user-username'] : undefined,
      shareable: properties['list-shareable'] ? properties['list-shareable'] : undefined
    }
    userLists.userLists.push(userList)
  })
  return userLists
}

//UserHouses
export function mapSirenToUserHouses (json) {
  const userHousesLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const userLink = json.links.find(link => link.rel.find(rel => rel === 'related'))
  const userHouses = {
    userHouses: [],
    selfHref: userHousesLink ? userHousesLink.href : undefined,
    userLink: userLink ? userLink.href : undefined,
    actions: {
      addHouse: json.actions.find(action => action.name === 'add-house')
    }
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const userHouse = {
      houseId: properties['house-id'],
      houseName: properties['house-name'],
      characteristics: properties['house-characteristics'],
      members: properties['house-members'],
      href: entity.links.find(link => link.rel.find(rel => rel === 'self')),
      itemsLink: entity.links.find(link => link.class.find(c => c === 'items')),
      movementsLink: entity.links.find(link => link.class.find(c => c === 'movements')),
      houseAllergiesLink: entity.links.find(link => link.class.find(c => c === 'house-allergies')),
      listsLink: entity.links.find(link => link.class.find(c => c === 'lists')),
      storagesLink: entity.links.find(link => link.class.find(c => c === 'storages'))
    }
    userHouses.userHouses.push(userHouse)
  })
  return userHouses
}

//Users
export function mapSirenToUsers (json) {
  const usersLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const indexLink = json.links.find(link => link.rel.find(rel => rel === 'index'))
  const users = {
    users: [],
    selfHref: usersLink ? usersLink.href : undefined,
    indexLink: indexLink ? indexLink.href : undefined
  }
  json.entities.forEach(entity => {
    const properties = entity.properties
    const user = {
      username: properties['user-username'],
      name: properties['user-name'],
      email: properties['user-email'],
      age: properties['user-age'],
      actions: {
        putUser: entity.actions.find(action => action.name === 'update-user'),
        deleteUser: entity.actions.find(action => action.name === 'delete-user')
      },
      href: entity.links.find(link => link.rel.find(rel => rel === 'self'))
    }
    users.users.push(user)
  })
  return users
}

//User
export function mapSirenToUser (json) {
  const userLink = json.links.find(link => link.rel.find(rel => rel === 'self'))
  const indexLink = json.links.find(link => link.rel.find(rel => rel === 'index'))
  const userHousesEntity = json.entities.find(entity => entity.class.find(c => c === 'user-houses'))
  return {
    username: json.properties['user-username'],
    name: json.properties['user-name'],
    email: json.properties['user-email'],
    age: json.properties['user-age'],
    actions: {
      putUser: json.actions.find(action => action.name === 'update-user'),
      deleteUser: json.actions.find(action => action.name === 'delete-user'),
    },
    userHousesHref: userHousesEntity.links.find(links => links.rel.find(rel => rel === 'self')),
    selfHref: userLink ? userLink.href : undefined,
    indexLink: indexLink ? indexLink.href : undefined
  }
}