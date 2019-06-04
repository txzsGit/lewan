app.service('cartService',function ($http) {
    //购物车列表
    this.findCartList=function(){
        return $http.get("cart/findCartList.do");
    }
    this.addGoodsToCartList=function (itemId,num) {
        return $http.get("cart/addGoodsToCartList.do?itemId="+itemId+"&num="+num);
    }
    this.sum=function (cartList) {
        var totalValue={totalMoney:0,totalNum:0};//合计实体
        for(var i=0;i<cartList.length;i++){
            var cart=cartList[i];
            for(var j=0;j<cart.orderItemList.length;j++){
                var orderItem=cart.orderItemList[j];
                totalValue.totalNum+=orderItem.num;
                totalValue.totalMoney+=orderItem.totalfee;
            }
        }
        return totalValue;
    }
    this.findAddressList=function (userId) {
        return $http.get("address/findListByLoginUser.do");
    }

    this.submitOrder=function (order) {
        return $http.post('order/add.do',order);
    }

    //add
    this.add=function(entity){
        return $http.post('../address/add.do',entity);
    }

    //update
    this.update=function(entity){
        return $http.post('../address/update.do',entity);
    }

    //findOne
    this.findOne=function(id){
        return $http.get("../address/findOne.do?id="+id);
    }

    //delete
    this.dele=function (id) {
        return $http.get("../address/delete.do?id="+id);

    }
});