angular.module('starter')

    .controller('LoginCtrl', [ '$scope', '$http', '$state', 'auth', 'API', function($scope,$http,$state,auth,API){
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
                url: API.URL + API.AUTH_ENDPOINT,
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
    }]);
