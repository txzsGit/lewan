var app=angular.module('funplay',[]);//定义品优购模块，不带分页
/*设置过滤器，$sce 服务写成过滤*/
app.filter('trustHtml',['$sce',function($sce){
    return function(data){
        return $sce.trustAsHtml(data);
    }
}]);