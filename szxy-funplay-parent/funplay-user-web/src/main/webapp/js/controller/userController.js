app.controller('userController',function ($scope,$location,$controller,userService) {
    $scope.entity={};

                //注册
    $scope.reg=function(){
        if($scope.entity.username==null||$scope.entity.username==''){
            alert("请输入用户名");
            return;
        }
        if($scope.entity.password==null||$scope.entity.password==''){
            alert("请输入密码");
            return;
        }
        if($scope.entity.phone==null||$scope.entity.phone==''){
            alert("请输入手机号码");
            return;
        }
        if($scope.sms==null||$scope.sms==''){
            alert("请输入验证码");
            return;
        }
        if($scope.entity.password!=$scope.password){
            alert("两次密码不一致请重新输入");
            $scope.entity.password='';
            $scope.password='';
            return;//俩次密码不一致
        }
       userService.add($scope.entity,$scope.sms).success(
           function (response) {
               if(response.success){
                   alert(response.message);
                   $scope.entity={};
                   $scope.password='';
                   $scope.sms='';
               }else{
                   alert(response.message);
               }

           }
       )
    }
    //发送验证码
    $scope.sendCode=function () {
        if($scope.entity.phone==null||$scope.entity.phone==''){
            alert("请输入手机号");
            return;
        }
        userService.sendCode($scope.entity.phone).success(
            function (response) {
                alert(response.message);
            }
        )
    }
});