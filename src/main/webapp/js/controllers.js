var API_URL = "http://localhost:8080/myusick/api";

angular.module('starter')
    
    .controller('MainCtrl', [ '$scope', '$state', 'auth', function($scope,$state,auth){
        $scope.user = auth.identity().user;
    }])

    .controller('ProfileCtrl', [ '$scope', '$state', 'auth', function($scope,$state,auth){
        $scope.user = auth.identity().user;
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

    .controller('RegisterCtrl', [ '$scope', function($scope){
        
        $scope.user = {};
        
        $scope.register = function(){
            
            console.log($scope.user);
            
        };
    }])

    .controller('AboutCtrl', [ '$scope', function($scope){

    }]);