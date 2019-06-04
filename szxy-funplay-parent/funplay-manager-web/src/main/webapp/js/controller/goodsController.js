 //控制层 
app.controller('goodsController' ,function($scope,$controller ,$location  ,goodsService,itemCatService,typeTemplateService){

    $controller('baseController',{$scope:$scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        goodsService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }

    //分页
    $scope.findPage=function(page,rows){
        goodsService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne=function(){
        var id=$location.search()['id'];
        if(id==null){//id为空是新增
            return;
        }
        goodsService.findOne(id).success(
            function(response){
                $scope.entity= response;
                editor.html(response.goodsDesc.introduction);
                $scope.entity.goodsDesc.itemimages=JSON.parse($scope.entity.goodsDesc.itemimages);
                $scope.entity.goodsDesc.customattributeitems=JSON.parse($scope.entity.goodsDesc.customattributeitems);
                $scope.entity.goodsDesc.specificationitems=JSON.parse($scope.entity.goodsDesc.specificationitems);
                //因为规格列表前面的内容存入的是json字符串的形式 所以要转成json以用来显示数据
                //sku列表的转换
                for (var i=0;i<$scope.entity.itemList.length;i++){
                    $scope.entity.itemList[i].spec = JSON.parse($scope.entity.itemList[i].spec);
                }
            }
        );
    }

    //添加
    $scope.save=function(){
        //获取富文本内容
        $scope.entity.goodsDesc.introduction=editor.html();
        var obj=null;
        if($scope.entity.goods.id==null){
            //新增
            obj=goodsService.add($scope.entity);
        }else{
            //更新
            obj=goodsService.update($scope.entity)
        }
        obj.success(
            function(response){
                if(response.success){
                    //重新查询
                    location.href="goods.html"
                }else{
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele=function(){
        //获取选中的复选框
        goodsService.dele( $scope.selectIds ).success(
            function(response){
                if(response.success){
                    $scope.reloadList();//刷新列表
                    $scope.selectIds=[];
                }
            }
        );
    }

    $scope.searchEntity={};//定义搜索对象

    //搜索
    $scope.search=function(page,rows){
        goodsService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
                $scope.selectIds=[];//因为不清，会导致商品上架有问题
            }
        );
    }
    //文件上传uplod
    $scope.uploadFile=function () {
        uploadService.uploadFile().success(
            function (response) {
                if(response.success){
                    $scope.image_entity.url=response.message;
                }else{
                    alert(response.message);
                }
            }
        )
    }

    //初始化$scope.entity
    $scope.entity={goods:{},goodsDesc:{itemimages:[],specificationitems:[]}};//定义页面实体结
    $scope.add_image_entity=function () {
        $scope.entity.goodsDesc.itemimages.push($scope.image_entity);
    }

    //删除列表
    $scope.remove_image_entity=function(index){
        $scope.entity.goodsDesc.itemimages.splice(index,1);
    }

    //读取一级分类
    $scope.selectItemCat1List=function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.itemCat1List=response;
            }
        )
    }

    //读取二级分类
    $scope.$watch('entity.goods.category1id',function (newValue,oldValue) {
        //根据选择的值，查询二级分类
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat2List=response;
            }
        )
    })
    //读取三级分类
    $scope.$watch('entity.goods.category2id',function (newValue,oldValue) {
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat3List=response;
            }
        )
    })

    //显示模板id
    $scope.$watch("entity.goods.category3id",function (newValue,oldValue) {
        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.entity.goods.typetemplateid=response.typeid;
            }
        )
    })

    //模板 ID 选择后  更新品牌列表 扩展属性 规格
    $scope.$watch("entity.goods.typetemplateid",function (newValue,oldValue) {

        typeTemplateService.findOne(newValue).success(
            function (response) {
                $scope.typetemplate = response;
                $scope.typetemplate.brandids = JSON.parse($scope.typetemplate.brandids);
                //更新扩展属性

                if($location.search()['id']==null) {
                    $scope.entity.goodsDesc.customattributeitems = JSON.parse($scope.typetemplate.customattributeitems);
                }

            }
        )

        typeTemplateService.findSpecList(newValue).success(
            function (response) {
                $scope.speclist=response;
            }
        )
    })
    //对规格选项进行勾选
    $scope.updateSpecAttribute=function ($event,name,value) {
        var obj = $scope.searchObjbyKey($scope.entity.goodsDesc.specificationitems,'attributename',name);
        if(obj!=null){
            if($event.target.checked){
                //说明数组中已经有该规格了，只需要push进去
                obj.attributevalue.push(value);
            }else{
                obj.attributevalue.splice(obj.attributevalue.indexOf(value),1);
                if(obj.attributevalue.length==0){
                    //说明没有选该规格
                    $scope.entity.goodsDesc.specificationitems.splice($scope.entity.goodsDesc.specificationitems.indexOf(obj),1);
                }
            }
        }else{
            $scope.entity.goodsDesc.specificationitems.push({"attributename":name,"attributevalue":[value]});
        }

    }
    //创建sku列表
    $scope.createItemList=function () {
        //初始化
        $scope.entity.itemList =[{spec: {}, price: '0', num: '9999', status: '0', isDefault: '0'}];
        var item = $scope.entity.goodsDesc.specificationitems;
        for (var i = 0; i < item.length; i++) {
            $scope.entity.itemList = addColumn($scope.entity.itemList, item[i].attributename, item[i].attributevalue);
        }
    }
    addColumn=function (list,columnName,columnValue) {
        var newList=[];//创建一个新的集合
        for(var i=0;i<list.length;i++){
            var oldRow=list[i];
            for(var j=0;j<columnValue.length;j++){
                var newRow=JSON.parse(JSON.stringify(oldRow));
                newRow.spec[columnName]=columnValue[j];
                newList.push(newRow);
            }
        }
        return newList;
    }
    //商品状态
    $scope.status=['未审核','已审核','审核未通过','关闭'];

    //初始化一个接受分类的数组
    $scope.itemCatList=[];
    //分类列表
    $scope.findItemCatList=function () {
        itemCatService.findAll().success(
            function (response) {
                for(var i=0;i<response.length;i++){
                    $scope.itemCatList[response[i].id]=response[i].name;//相当于将ItemCat这张表中的id和name一一对应，只是将其视为下标，节省资源
                }

            }
        )
    }
    //配合ng-checked指令，根据规格名称和选项名称返回是否被勾选
    $scope.checkAttributeValue=function (specname,optionname) {
        var item=$scope.entity.goodsDesc.specificationitems;
        var obj=$scope.searchObjbyKey(item,'attributename',specname);
        if(obj==null){//说明item中不包含该属性中的值
            return false;
        }else{
            if(obj['attributevalue'].indexOf(optionname)>=0){//如果数组中出有该值返回值会>=0
                return true;
            }else{
                return false;
            }
        }
    }
    //审核商品
    $scope.updateStatus=function (status) {
            var ids=$scope.selectIds;
            goodsService.updateStatus(ids,status).success(
                function (response) {
                    if(response.success){
                        $scope.reloadList();
                        $scope.selectIds=[];
                    }else{
                        alert(response.message);
                    }
                }
            )
    }


});	
