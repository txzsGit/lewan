app.controller('contentController',function($scope,contentService,itemCatService,loginService){
	
	$scope.contentList=[];//广告列表
	
	$scope.findByCategoryId=function(categoryId){
		contentService.findByCategoryId(categoryId).success(
			function(response){
				$scope.contentList[categoryId]=response;
			}
		);		
	}
    //门户的搜索传递
    $scope.search=function () {
        location.href="http://localhost:9104/search.html#?keywords="+$scope.keywords;
    }

    $scope.itemCatList=[];//分类列表
	//分类列表展示
    $scope.findByParentId=function(parentid){
        itemCatService.findByParentId(parentid).success(
            function(response){
                $scope.itemCatList[parentid]=response;
            }
        );
    }
    //显示登陆名
    $scope.showName=function () {
        loginService.showName().success(
            function (response) {
                $scope.loginName=response.loginName;
            }
        )
    }
});