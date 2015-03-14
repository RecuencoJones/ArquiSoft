var API_URL = "http://"+window.location.host+"/myusick/api";

angular.module('starter')
    
    .controller('MainCtrl', [ '$scope', '$state', '$location', 'auth', function($scope,$state,$location,auth){
        $scope.user = auth.identity().user;

        $scope.hidden = true;

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

    }])

    .controller('ProfileCtrl', [ '$scope', '$state', '$location', 'auth', function($scope,$state,$location,auth){
        $scope.user = auth.identity().user;

        $scope.hidden = true;

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
    }])

    .controller('NavbarCtrl', [ '$scope', '$location', '$state', 'auth', function($scope,$location,$state,auth){

        $scope.principal = auth;
        
        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
        
        $scope.logout = function (){
            auth.authenticate(null);
            $state.go('login');
        }
    }])
    
    .controller('SidebarCtrl', ['$scope', '$location', function($scope,$location){
        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
    }])

    .controller('LoginCtrl', [ '$scope', '$http', '$state', 'auth', function($scope,$http,$state,auth){
        $scope.user = "";
        $scope.password = "";
        $scope.hiddenErrorMessage = true;

        $scope.hideErrorMessage = function () {
            $scope.hiddenErrorMessage = true;
        };
        
        $scope.login = function(){
            
            var user = $scope.user;
            var password = $scope.password;
            $http({
                method: 'POST',
                url: API_URL + '/auth',
                data: 
                    'user=' + user + '&' +
                    'password=' + password,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).success(function(data){
                console.log(data);
                if(data.token=="no"){
                    $scope.hiddenErrorMessage = false;
                }else{
                    auth.authenticate(data);
                    $state.go('home');
                }
            }).error(function(){
                $scope.hiddenErrorMessage = false;
            });
        }
    }])

    .controller('RegisterCtrl', [ '$scope', '$http', '$state', 'auth', function($scope,$http,$state,auth){
        $scope.user = {};
        $scope.hiddenErrorMessage = true;
        $scope.errors = {};

        $scope.hideErrorMessage = function () {
            $scope.hiddenErrorMessage = true;
        };
        
        $scope.register = function(){
            
            $scope.errors = {};
            $scope.hiddenErrorMessage = true;
            console.log($scope.user);
            
            if($scope.user.password==undefined 
                || $scope.user.password.trim()=="" 
                || $scope.user.password!=$scope.user.repassword){
                $scope.errors.password = true;
                $scope.hiddenErrorMessage = false;
            }else {
                $http.post(
                    API_URL + '/register',
                    JSON.stringify($scope.user),
                    {
                        'Content-Type': 'application/json'
                    })

                    .success(function (data) {
                        console.log(data);
                        if (data.token == "no") {
                            $scope.hiddenErrorMessage = false;
                            $scope.errors = data.errors;
                        } else {
                            auth.authenticate(data);
                            $state.go('home');
                        }
                    })
                    .error(function () {
                        $scope.hiddenErrorMessage = false;
                    });
            }
        };
    }]);