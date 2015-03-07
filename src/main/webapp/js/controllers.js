angular.module('starter')

    .controller('NavbarCtrl', [ '$scope', '$location', function($scope,$location){

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
    }])

    .controller('LoginCtrl', [ '$scope', function($scope){

    }])

    .controller('RegisterCtrl', [ '$scope', function($scope){

    }])

    .controller('AboutCtrl', [ '$scope', function($scope){

    }]);