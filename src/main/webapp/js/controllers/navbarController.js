angular.module('starter')

    .controller('NavbarCtrl', [ '$scope', '$location', '$state', 'auth', function($scope,$location,$state,auth){
        
        $scope.principal = auth;
        $scope.loggedUserId = "";
        
        if(auth.isAuthenticated()) {
            $scope.loggedUserId = auth.identity().userid;
        }

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $scope.logout = function (){
            auth.authenticate(null);
            $state.go('login');
        }
    }]);
