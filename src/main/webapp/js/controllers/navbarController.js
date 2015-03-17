angular.module('starter')

    .controller('NavbarCtrl', [ '$scope', '$location', '$state', 'auth', function($scope,$location,$state,auth){

        $scope.principal = auth;

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $scope.logout = function (){
            auth.authenticate(null);
            $state.go('login');
        }
    }]);
