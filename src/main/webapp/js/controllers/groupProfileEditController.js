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

        $scope.hideErrorMessage = function () {
            $scope.hiddenErrorMessage = true;
        };

        $scope.update = function(){
            $scope.errors = {};
            $scope.hiddenErrorMessage = true;

            if(!($scope.avatar.trim().length==0) && !$scope.avatar.match(/\.(jpeg|jpg|gif|png)$/)){
                $scope.errors.avatar = true;
                $scope.hiddenErrorMessage = false;
                return;
            }

            var tmp = {
                id: $scope.bandId,
                nombre: $scope.name.trim().length==0 ? null : $scope.name,
                avatar: $scope.avatar,
                descripcion: $scope.description,
                type: true
            };

            $http.post(API.URL + API.PROFILE_EDIT_ENDPOINT,
                JSON.stringify(tmp),
                {
                    'Content-Type': 'application/json'
                }).success(function(data){
                    if(!data.ok){
                        $scope.hiddenErrorMessage = false;
                        $scope.errors = data;
                    }else{
                        $state.go('group', {_groupid: $scope.bandId});
                    }
                }).error(function () {
                    $scope.hiddenErrorMessage = false;
                });

        };
    }]);
