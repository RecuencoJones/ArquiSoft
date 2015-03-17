angular.module('starter')

    .controller('ProfileCtrl', [ '$scope', '$state', '$location', 'auth', function($scope,$state,$location,auth){
        $scope.user = auth.identity().user;

        $scope.hidden = true;

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
    }]);
