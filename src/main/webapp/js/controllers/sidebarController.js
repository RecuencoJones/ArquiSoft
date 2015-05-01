angular.module('starter')

    .controller('SidebarCtrl', ['$scope', '$location', 'auth', function($scope,$location,auth){

        $scope.loggedUserId = "";
        
        if(auth.isAuthenticated()) {
            $scope.loggedUserId = auth.identity().userid;
        }
        
        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
    }]);
