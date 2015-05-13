angular.module('starter')

    .controller('SidebarCtrl', ['$scope', '$state', '$stateParams', '$location', 'auth', function($scope,$state,$stateParams,$location,auth){

        $scope.loggedUserId = "";
        $scope.search_term = "";
        
        if(auth.isAuthenticated()) {
            $scope.loggedUserId = auth.identity().userid;
        }
        
        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
        
        $scope.sidebar_search = function(){
            $state.go('search', {_term: $scope.search_term});
            $scope.search_term = "";
        };
    }]);
