angular.module('starter')

    .controller('FollowersCtrl',['$scope', '$state', '$stateParams', '$location', '$http', 'auth', 'API', function($scope,$state,$stateParams,$location,$http,auth,API){
        $scope.loggedUserId = auth.identity().userid;
        $scope.user_id = $stateParams._userid;
        $scope.hidden = true;

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $scope.view_name = "Followers";
        $scope.user = {};

        $http.get(API.URL + API.PROFILE_ENDPOINT + $scope.user_id)
            .success(function(data){
                console.log(data);
                $scope.user = data;
            }).error(function(data){
                console.log("error");
            });
        
        $http.get(API.URL + API.FOLLOWERS_ENDPOINT + $scope.user_id)
            .success(function(data){
                console.log(data);
            });

        $scope.publishers = [
            {
                name: "noderp"
            }
        ];
    }]);