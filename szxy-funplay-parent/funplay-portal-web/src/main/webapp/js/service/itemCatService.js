app.service('itemCatService' ,function($http){
    //根据parentid查询商品分类
    this.findByParentId=function(parentid){
        return $http.get('itemCat/findByParentId.do?parentid='+parentid);
    }
});