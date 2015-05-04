angular.module('starter')

    .controller('NewGroupCtrl', [ '$scope', '$http', '$state', 'auth', 'API', function($scope,$http,$state,auth,API){
        $scope.band = {};
        $scope.hiddenErrorMessage = true;
        $scope.errors = {};

        $scope.hideErrorMessage = function () {
            $scope.hiddenErrorMessage = true;
        };

        $scope.register = function(){

            $scope.errors = {};
            $scope.hiddenErrorMessage = true;
            $scope.band.creator = auth.identity().userid;
            console.log($scope.band);

            $http.post(
                API.URL + API.CREATE_BAND_ENDPOINT,
                JSON.stringify($scope.band),
                {
                    'Content-Type': 'application/json'
                })

                .success(function (data) {
                    console.log(data);
                    if (!data.ok) {
                        $scope.hiddenErrorMessage = false;
                        $scope.errors = data;
                    } else {
                        //auth.authenticate(data);
                        auth.addGroup(data.id);
                        $state.go('profile', { _userid: auth.identity().userid});
                    }
                })
                .error(function () {
                    $scope.hiddenErrorMessage = false;
                });
        };
    }]);
