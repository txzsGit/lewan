 
    //品牌控制
    app.controller('brandController' ,function($scope,$controller,brandService){    
    	//继承baseController，{$scope:$scope}用于公用$scope的东西
    	$controller('baseController',{$scope:$scope});
    	//读取列表数据绑定到表单中   
     $scope.findAll=function(){ 
      brandService.findAll().success( 
       function(response){ 
        $scope.list=response; 
       }    
      ); 
     } 
           
  
           
   		//分页 
		$scope.findPage=function(page,size){
			brandService.findPage(page,size).success(
				function(response){
					$scope.list=response.rows;//显示当前页数据 	
					$scope.paginationConf.totalItems=response.total;//更新总记录数 
				}		
			);				
		}
   		$scope.save=function(){
   			var obj=null;//默认是空，update和add方法有太多相似处，唯一不同是id的有无
   			if($scope.entity.id==null){
   				obj=brandService.add($scope.entity);
   			}else{
   				obj=brandService.update($scope.entity);
   			}
   			obj.success(
   					function(response){
   					if(response.success){
   						$scope.reloadList();//刷新页面
   					}else{
   						alert(response.message);
   					}
   					
   				})
   			
   		}
   		//通过id查找品牌，是更新品牌信息最重要的一步
   		$scope.findOne=function(id){
   			brandService.findOne(id).success(
   				function(response){
   					$scope.entity=response;
   				}		
   			)
   		}
   		//（1）数组的 push 方法：向数组中添加元素 
   		//（2）数组的 splice 方法：从数组的指定位置移除指定个数的元素 ，参数 1 为位置  ，参数 2 位移除的个数  
   		//（3）复选框的 checked 属性：用于判断是否被选中 
   		

   		
   		 //删除操作
   		 $scope.del=function(){
   			 brandService.del($scope.selectIds).success(
   					function(response){
   						if(response.success){
   							//删除成功
   							//刷新
   							$scope.reloadList();
   						}else{
   							alert(response.message);
   						}
   					}		 
   			 )
   		 }
   		 
   		 $scope.searchEntity={};
   		//条件查询
   		$scope.search=function(page,size){
   			brandService.search(page,size,$scope.searchEntity).success(
   					function(response){
   						$scope.list=response.rows;//显示当前页数据 	
   						$scope.paginationConf.totalItems=response.total;//更新总记录数 
   					})
   			}
    });  