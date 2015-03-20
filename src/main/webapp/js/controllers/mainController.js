angular.module('starter')

    .controller('MainCtrl', [ '$scope', '$state', '$location', 'auth', function($scope,$state,$location,auth){
        $scope.loggedUserId = auth.identity().userid;

        $scope.hidden = true;

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

    }]);
