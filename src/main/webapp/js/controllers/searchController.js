angular.module('starter')

    .controller('SearchCtrl',['$scope', '$state', '$stateParams', '$location', '$http', 'auth', 'API', function($scope,$state,$stateParams,$location,$http,auth,API){
        $scope.loggedUserId = auth.identity().userid;
        $scope.term = $stateParams._term;
        $scope.hidden = true;

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
        
        $scope.category = 0;
        $scope.search_value = $stateParams._term;
        
        $scope.search = function(){
            $state.go('search', {_term: $scope.search_value});
        };

        $scope.results_0 = [
            {
                name: 'Derp1'
            },
            {
                name: 'Derp2'
            },
            {
                name: 'Derp3'
            },
            {
                name: 'asdf'
            }
        ];
        $scope.results_1 = [
            {
                name: 'Defgafrp'
            },
            {
                name: '1337'
            },
            {
                name: 'foobar'
            },
            {
                name: 'gfdkjlghdfña'
            }
        ];
        $scope.results_2 = [
            {
                name: 'caca'
            },
            {
                name: 'pene'
            },
            {
                name: 'uma'
            },
            {
                name: 'ahahahaha'
            }
        ];
        $scope.results_3 = [
            {
                name: 'not even'
            },
            {
                name: 'close'
            },
            {
                name: 'Derp3'
            },
            {
                name: 'bitch'
            }
        ];
        
        $scope.results_array = [$scope.results_0,$scope.results_1,$scope.results_2,$scope.results_3];
        $scope.results = $scope.results_array[0];
        
        $scope.changeCategory = function(int){
            $scope.category = int;
            $scope.results = $scope.results_array[$scope.category];
        };

        $http.get(API.URL + API.SEARCH_PERSON_ENDPOINT + $scope.term)
            .success(function(data){
                console.log(data);
            });
        $http.get(API.URL + API.SEARCH_GROUP_ENDPOINT + $scope.term)
            .success(function(data){
                console.log(data);
            });
        $http.get(API.URL + API.SEARCH_TAG_ENDPOINT + $scope.term)
            .success(function(data){
                console.log(data);
            });
        $http.get(API.URL + API.SEARCH_SKILL_ENDPOINT + $scope.term)
            .success(function(data){
                console.log(data);
            });
    }]);
