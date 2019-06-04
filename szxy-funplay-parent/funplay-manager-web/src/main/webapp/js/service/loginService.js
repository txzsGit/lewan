app.service('loginService',function ($http) {
    //显示用户名
    this.loginName=function () {
        return $http.post('../login/name.do');
    }
});