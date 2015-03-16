angular.module('starter')

    .controller('RegisterCtrl', [ '$scope', '$http', '$state', 'auth', 'API', function($scope,$http,$state,auth,API){
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
                    API.URL + API.REGISTER_ENDPOINT,
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