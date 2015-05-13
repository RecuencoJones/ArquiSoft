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

        $scope.results_0 = "";
        $scope.results_1 = "";
        $scope.results_2 = "";
        $scope.results_3 = "";

        $scope.results_array = [$scope.results_0,$scope.results_1,$scope.results_2,$scope.results_3];
        $scope.results = $scope.results_array[$scope.category];
        
        $scope.changeCategory = function(int){
            $scope.category = int;
            $scope.results = $scope.results_array[$scope.category];
        };

        $http.get(API.URL + API.SEARCH_PERSON_ENDPOINT + $scope.term)
            .success(function(data){
                $scope.results_array[0] = data;
                $scope.results = $scope.results_array[$scope.category];
                console.log(data);
            });
        $http.get(API.URL + API.SEARCH_GROUP_ENDPOINT + $scope.term)
            .success(function(data){
                $scope.results_array[1] = data;
                $scope.results = $scope.results_array[$scope.category];
                console.log(data);
            });
        $http.get(API.URL + API.SEARCH_TAG_ENDPOINT + $scope.term)
            .success(function(data){
                $scope.results_array[2] = data;
                $scope.results = $scope.results_array[$scope.category];
                console.log(data);
            });
        $http.get(API.URL + API.SEARCH_SKILL_ENDPOINT + $scope.term)
            .success(function(data){
                $scope.results_array[3] = data;
                $scope.results = $scope.results_array[$scope.category];
                console.log(data);
            });

        $scope.goTo = function(id,type){
            if(type){
                $state.go('group', { _groupid: id});
            }else{
                $state.go('profile', { _userid: id});
            }
        };
    }]);
