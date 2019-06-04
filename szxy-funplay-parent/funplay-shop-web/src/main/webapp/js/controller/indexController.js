app.controller('indexController',function ($scope,$controller,loginService,sellerService) {
    $scope.showLoginName=function () {
        loginService.loginName().success(
            function (response) {
              $scope.loginName=response.loginName;
        }
        )
    }
    $scope.showTime=function () {
        sellerService.readTime().success(
            function (response) {
                $scope.loginTime=response;
            }
        )
    }
    $scope.updateTime=function () {
        sellerService.updateTime().success(
            function (response) {
                if(response.success){
                    location.href="/logout"
                }
            }
        )
    }
});