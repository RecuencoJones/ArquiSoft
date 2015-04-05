angular.module('starter')

    .controller('RegisterCtrl', [ '$scope', '$http', '$state', 'auth', 'API', function($scope,$http,$state,auth,API){
        $scope.hiddenErrorMessage = true;
        $scope.errors = {};

        $scope.hideErrorMessage = function () {
            $scope.hiddenErrorMessage = true;
        };
        
        $scope.register = function(){
            
            $scope.errors = {};
            $scope.hiddenErrorMessage = true;
            
            if($scope.password==undefined
                || $scope.password.trim()==""
                || $scope.password!=$scope.repassword){
                $scope.errors.password = true;
                $scope.hiddenErrorMessage = false;
            }else {
                $scope.user = {
                    name: $scope.name,
                    lastname: $scope.lastname,
                    birthdate: Date.parse($scope.birthdate),
                    city: $scope.city,
                    country: $scope.country,
                    phone: $scope.phone,
                    email: $scope.email,
                    password: $scope.password,
                    repassword: $scope.repassword
                };
                console.log($scope.user);
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