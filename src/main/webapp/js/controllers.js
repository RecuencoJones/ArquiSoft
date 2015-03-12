var API_URL = "http://localhost:8080/myusick/api";

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

    .controller('RegisterCtrl', [ '$scope', function($scope){
        
        $scope.user = {};
        
        $scope.register = function(){
            
            console.log($scope.user);
            
        };
    }]);