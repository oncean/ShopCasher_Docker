import $http from '../js/axios'

let shopService = {
  catogeryRelations: null,
  catogeryNameMap: null,
  // 根据code 获取商品信息
  getGoodByCode: function (shopId, goodCode) {
    return $http.get('/good/view/' + shopId + '/' + goodCode)
  },
  // 根据code 获取商品信息
  querySettlement: function (req) {
    return $http.post('/settlement/query', req)
  },
  initCatogery: function () {
    $http.get('/catogery/getStructure').then(result => {
      sessionStorage.setItem('catogeryRelations', JSON.stringify(result.result))
    })
    $http.get('/catogery/getAll').then(result => {
      let catogeryNameMap = new Map()
      result = result.result
      for (let i in result) {
        catogeryNameMap.set(result[i].id, result[i])
      }
      sessionStorage.setItem('catogeryNameMap', JSON.stringify(catogeryNameMap))
    })
  },
  // 查询后台商品分类
  queryCatogery: function () {
    if (this.catogeryRelations === null) {
      let storge = sessionStorage.getItem('catogeryRelations')
      this.catogeryRelations = JSON.parse(storge)
      if(!this.catogeryRelations || this.catogeryRelations.length <= 0){
        this.initCatogery()
        this.catogeryRelations = this.queryCatogery()
      }
    }
    return this.catogeryRelations
  },
  // 获取商品分类名称
  getCatogeryName: function (id) {
    return this.getCatogeryById(id).name
  },
  // 获取商品分类
  getCatogeryById: function (id) {
    if (this.catogeryNameMap === null) {
      this.catogeryNameMap = new Map()
      let storge = sessionStorage.getItem('catogeryNameMap')
      let list = JSON.parse(storge)
      for (var i in list) {
        this.catogeryNameMap.set(list[i][0], list[i][1])
      }
    }
    return this.catogeryNameMap.get(id)
  },
  /* 拼装级联商品分类 */
  buildCatogeryForCascader: function () {
    let result = this.queryCatogery()
    let categoryOptions = []
    for (var i in result) {
      var catogeryValue = result[i]
      var categoryOption = {
        value: catogeryValue.catogery.id,
        label: catogeryValue.catogery.name,
        children: []
      }
      for (var j in catogeryValue.children) {
        var sonInfo = catogeryValue.children[j]
        var chidren = {
          value: sonInfo.id,
          label: sonInfo.name
        }
        categoryOption.children.push(chidren)
      }
      categoryOptions.push(categoryOption)
    }
    return categoryOptions
  },
  // 根据id 删除商品
  deleteGoodById: function (shopId, goodId) {
    return $http.get('/good/delete/' + shopId + '/' + goodId)
  }
}

export default shopService
