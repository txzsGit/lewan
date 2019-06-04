app.controller('cartController',function ($scope,$controller,cartService,loginService) {
    //查询购物车列表
    $scope.findCartList=function () {
        cartService.findCartList().success(
            function (response) {
            $scope.cartList=response;
            $scope.totalValue=cartService.sum($scope.cartList);
        });
    }
    //添加商品入购物车
    $scope.addGoodsToCartList=function (itemId,num) {
        cartService.addGoodsToCartList(itemId,num).success(
            function (response) {
                if(response.success){
                    $scope.findCartList();//刷新列表
                }else{
                    alert(response.message);//弹出错误信息
                }
            }
        )
    }
    //查寻收货地址
    $scope.findAddressList=function () {
        cartService.findAddressList().success(
            function (response) {
                $scope.addressList=response;
                for(var i=0;i<$scope.addressList.length;i++){
                    if($scope.addressList[i].isDefault=='1'){
                        $scope.address=$scope.addressList[i];
                        break;
                    }
                }
            }
        )
    }
    //选中的联系人
    $scope.selectAddress=function (address) {
        $scope.address=address;
    }
    //判断选中的联系人
    $scope.isSeletedAddress=function (address) {
        if($scope.address==address){
            return true;
        }else{
            return false;
        }
    }
    //默认是货到付款支付
    $scope.order={paymentType:'2'};

    //选择支付方式
    $scope.selectPayType=function (type) {
        $scope.order.paymenttype=type;
    }

    $scope.submitOrder=function () {
        $scope.order.receiverareaname=$scope.address.address;//地址
        $scope.order.receivermobile=$scope.address.mobile;//手机
        $scope.order.receiver=$scope.address.contact;//联系人
        cartService.submitOrder($scope.order).success(
            function (response) {
                if(response.success){
                    //页面跳转
                    if($scope.order.paymenttype=='1'){//如果是微信支付，跳转到支付页面
                        location.href="pay.html";
                    }else{//如果货到付款，跳转到提示页面
                        location.href="paysuccess.html#?money="+$scope.totalValue.totalMoney+"&paymenttype="+$scope.order.paymenttype;
                    }
                }else{
                    alert(response.message)
                }
        });
    }

    $scope.save=function(){
        var obj=null;//默认是空，update和add方法有太多相似处，唯一不同是id的有无
        if($scope.entity.id==null){
            obj=cartService.add($scope.entity);
        }else{
            obj=cartService.update($scope.entity);
        }
        obj.success(
            function(response){
                if(response.success){
                    $scope.findAddressList();//重新加载
                }else{
                    alert(response.message);
                }

            })

    }
    $scope.findOne=function(id){
        cartService.findOne(id).success(
            function(response){
                $scope.entity=response;
            }
        )
    }
    $scope.dele=function(id){
        cartService.dele(id).success(
            function(response){
                if(response.success){
                    $scope.findAddressList();//重新加载
                }else{
                    alert(response.message);
                }
            }
        )
    }
    //显示登陆名
    $scope.showName=function () {
        loginService.showName().success(
            function (response) {
                $scope.loginName=response.loginName;
            }
        )
    }

    //购物车的搜索传递
    $scope.search=function () {
        location.href="http://localhost:9104/search.html#?keywords="+$scope.keywords;
    }


});