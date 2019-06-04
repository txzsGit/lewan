
app.controller('baseController',function($scope){//抽取共性部分，用于继承
	 //分页控件配置currentPage:当前页   totalItems :总记录数  itemsPerPage:每页记录数  perPageOptions :分页选项  onChange:当页码变更后自动触发的方法 
	$scope.paginationConf = {
		currentPage: 1,
		totalItems: 10,
		itemsPerPage: 10,
		perPageOptions: [10, 20, 30, 40, 50],
		onChange: function(){
			$scope.reloadList();
		}
	};
	//刷新列表
	$scope.reloadList=function(){
		//配置了search就不走findPage,$scope.searchEntity为空，就是分页查询
		$scope.search( $scope.paginationConf.currentPage ,  $scope.paginationConf.itemsPerPage );
	}

    $scope.selectIds=[];//代表选中的id数组
		//批量删除 通过$event获取选中的复选框
		//更新复选 
	$scope.updateSelection=function($event,id){
			 if($event.target.checked){//代表勾选了
				 $scope.selectIds.push(id);
			 }else{//取消勾选
				 var index=$scope.selectIds.indexOf(id);
			 	$scope.selectIds.splice(index,1);
			 }
		 }
});