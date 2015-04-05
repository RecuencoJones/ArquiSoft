angular.module('starter')

    .controller('PostCtrl', [ '$scope', '$state', '$stateParams', '$location', '$http', 'auth', 'API', function($scope,$state,$stateParams,$location,$http,auth,API){
        $scope.loggedUserId = auth.identity().userid;
        $scope.hidden = true;
        $scope.post = {};

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        /*$http.get(API.URL + API.POST_ENDPOINT + $stateParams._postid)
            .success(function(data){
                console.log(data);
                $scope.post = data;
            }).error(function(data){
                console.log("error");
            });*/

        $scope.post = {
            id: "2",
            avatar: "img/placeholder.jpg",
            date: Date.now(),
            user: "anon@not.need",
            user_id: "2",
            content: "Foo bar"
        };
    }]);

