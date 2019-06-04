app.controller('payController',function ($scope,$location,payService,loginService) {
    $scope.createNative=function () {
        payService.createNative().success(
            function (response) {
                $scope.totalMoney=(response.total_fee/100).toFixed(2)//金额
                $scope.out_trade_no= response.out_trade_no;//订单号
                //本地生成二维码
                var qr=new QRious({
                    element:document.getElementById('qrious'),
                    size:250,
                    level:'H',
                    value:response.code_url
                });
                queryPayStatus();
            });
    }
    /**
     * 查询订单状态
     */
    queryPayStatus=function () {
        payService.queryPayStatus($scope.out_trade_no).success(
            function (response) {
                if(response.success){
                    location.href="paysuccess.html#?money="+$scope.totalMoney+"&paymenttype=1";
                }else{
                    if(response.message=='二维码超时'){
                        $scope.createNative();//重新生成二维码
                    }else{
                        location.href="payfail.html";
                    }
                }
            }
        );
    }
    $scope.getMoney=function () {
        return $location.search()['money'];
    }
    $scope.getPayType=function () {
        return $location.search()['paymenttype']
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