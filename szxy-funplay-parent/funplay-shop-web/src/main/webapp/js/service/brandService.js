 //品牌服务
    app.service("brandService",function($http){
    	//findAll
    	this.findAll=function(){
    		return $http.get('../brand/findAll.do');
    	}
    	
    	//findPage
    	this.findPage=function(page,size){
    		return $http.get('../brand/findPage.do?page='+page +'&size='+size);
    	}
    	
    	//add
    	this.add=function(entity){
    		return $http.post('../brand/add.do',entity);
    	}
    	
    	//update
    	this.update=function(entity){
    		return $http.post('../brand/update.do',entity);
    	}
    	
    	//findOne
    	this.findOne=function(id){
    		return $http.get("../brand/findOne.do?id="+id);
    	}
    	
    	//del
    	this.del=function(ids){
    		return $http.get("../brand/delete.do?ids="+ids);
    	}
    	//search
    	this.search=function(page,size,searchEntity){
    		return $http.post('../brand/search.do?page='+page+'&rows='+size,searchEntity);
    	}

    	//selectOptionList
		this.selectOptionList=function () {
			return $http.get('../brand/selectOptionList.do');
        }
    })