app.controller('searchController',function ($scope,$location,$http,searchService,loginService) {
    //初始化searchMap
    $scope.searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':20,'sortValue':'','sortField':''};
    $scope.search=function () {
        $scope.searchMap.pageNo=parseInt($scope.searchMap.pageNo);
        searchService.search($scope.searchMap).success(
            function (response) {
                $scope.resultMap=response;
                buildPageLabel();
            }
        )
    }

    buildPageLabel=function () {
        $scope.pageLabel=[];//添加分页栏
        var firstPage=1;//起始页
        var lastPage=$scope.resultMap.totalPages;//截至页
        $scope.firstDot=true;//前置点
        $scope.lastDot=true;//后置点
        if($scope.resultMap.totalPages>5){
            if($scope.searchMap.pageNo<=3){
                firstPage=1;
                lastPage=5;
                $scope.firstDot=false;
            }else if($scope.searchMap.pageNo+2>=$scope.resultMap.totalPages){
                firstPage=$scope.resultMap.totalPages-4;
                lastPage=$scope.resultMap.totalPages;
                $scope.lastDot=false;
            }else{
                firstPage=$scope.searchMap.pageNo-2;
                lastPage=$scope.searchMap.pageNo+2;
            }
        }else{
            //小于五条
            $scope.firstDot=false;//前置点
            $scope.lastDot=false;//后置点
        }

        //构建页码
        for(var i=firstPage;i<=lastPage;i++){
            $scope.pageLabel.push(i);
        }
    }
    //分页查询
    $scope.queryByPage=function (pageNo) {
        if(pageNo<1||pageNo>$scope.resultMap.totalPages){
            return;
        }
        $scope.searchMap.pageNo=pageNo;
        $scope.search();
    }
    //添加搜索项
    $scope.addSearchItem=function(key,value){
        //分类和品牌
        if(key=='category'||key=='brand'||key=='price'){
            $scope.searchMap[key]=value;
        }else{//规格
            $scope.searchMap.spec[key]=value;
        }
        $scope.search();//执行搜索
    }
    //撤销搜索项
    $scope.removeSearchItem=function (key) {
        //分类和品牌
        if(key=='category'||key=='brand'||key=='price'){
            $scope.searchMap[key]='';
        }else{//规格
            delete $scope.searchMap.spec[key];
        }
        $scope.search();//执行搜索
    }
    //排序
    $scope.sortSearch=function (sortValue,sortFiled) {
        $scope.searchMap.sortValue=sortValue;
        $scope.searchMap.sortFiled=sortFiled;
        $scope.search();
    }
    //隐藏品牌
    $scope.keywordsIsBrand=function () {
            for(var i=0;i<$scope.resultMap.brandList.length;i++){
                if($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[i]['text'])>=0){//如果关键字包含品牌，将品牌隐藏,大于等于0就是包含
                    return true;
                }
            }
            return false;
    }

    ////加载查询字符串
    $scope.loadkeywords=function () {
        $scope.searchMap.keywords= $location.search()['keywords'];
        $scope.search();
    }
    //显示登陆名
    $scope.showName=function () {
        loginService.showName().success(
            function (response) {
                $scope.loginName=response.loginName;
            }
        )
    }

    $scope.addToCart=function(itemid,num){
        //alert("SKUID"+$scope.sku.id);
        $http.get('http://localhost:9107/cart/addGoodsToCartList.do?itemId='
            + itemid+'&num='+num,{'withCredentials':true}).success(
            function (response) {
                if(response.success){
                    location.href="http://localhost:9107/cart.html";
                }
            }
        )
    }
});