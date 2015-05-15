angular.module('starter')

    .controller('GroupProfileEditCtrl',['$scope', '$state', '$stateParams', '$location', '$http', 'auth', 'API', function($scope,$state,$stateParams,$location,$http,auth,API){
        $scope.loggedUserId = auth.identity().userid;
        $scope.bandId = $stateParams._groupid;
        $scope.hidden = true;

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $scope.name = "";
        $scope.avatar = "";
        $scope.description = "";

        $scope.errors = [];
        $scope.hiddenErrorMessage = true;

        $http.get(API.URL + API.PROFILE_ENDPOINT + $scope.bandId)
            .success(function(data){
                console.log(data);
                if(data.type){
                    $scope.name = data.name;
                    $scope.avatar = data.avatar;
                    $scope.description = data.description;
                }else{
                    $state.go("error");
                }
            }).error(function(data){
                console.log("error");
            });

        $scope.hideErrorMessage = function () {
            $scope.hiddenErrorMessage = true;
        };

        $scope.update = function(){
            $scope.errors = [];
            $scope.hiddenErrorMessage = true;

            var tmp = {
                name: $scope.name,
                avatar: $scope.avatar,
                bio: $scope.description
            }

            console.log(tmp);
        };
    }]);
