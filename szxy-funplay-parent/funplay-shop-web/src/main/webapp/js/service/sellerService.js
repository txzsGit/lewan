//服务层
app.service('sellerService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../seller/findAll.do');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../seller/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../seller/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../seller/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../seller/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../seller/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../seller/search.do?page='+page+"&rows="+rows, searchEntity);
	}
    //商家资料
    this.findSeller=function(){
        return $http.get('../seller/findSeller.do');
    }
    //修改资料
    this.submit=function(entity){
        return $http.post('../seller/update.do',entity);
    }
    //设置登陆时间
    this.updateTime=function(){
        return $http.get('../seller/updateTime.do');
    }
    //读取登陆时间
	this.readTime=function () {
		return  $http.get('../seller/readTime.do');
    }
    //修改密码
    this.updatePassword=function (fpassword,spassword) {
        return  $http.get('../seller/updatePassword.do?fpassword='+fpassword+'&spassword='+spassword);
    }
});
