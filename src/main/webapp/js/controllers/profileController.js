angular.module('starter')

    .controller('ProfileCtrl', [ '$scope', '$state', '$stateParams', '$location', '$http', 'auth', 'API', function($scope,$state,$stateParams,$location,$http,auth,API){
        $scope.loggedUserId = auth.identity().userid;
        $scope.message = "";
        $scope.hidden = true;
        $scope.user = {};
        
        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
        
        $http.get(API.URL + API.PROFILE_ENDPOINT + $stateParams._userid)
            .success(function(data){
                console.log(data);
                $scope.user = data;
            }).error(function(data){
                console.log("error");
            });
        
        $scope.sendMessage = function() {
            if ($scope.message.trim() != "") {
                var newPub = {
                    id: "999",
                    date: Date.now(),
                    user: $scope.user.name,
                    user_id: $scope.loggedUserId,
                    content: $scope.message
                };
                //$http.post()
                $scope.user.publications.push(newPub);
                $scope.message = "";
            }
        }
        
        /*$scope.user = {
            name: "David",
            description: "Cute retarded unicorn",
            avatar: "img/placeholder.JPG",
            skills: ["Guitarra","Bajo","Retrasado"],
            groups: [
                {
                    id: 123,
                    name: "Ceporrín"                    
                },
                {
                    id: 456,
                    name: "Ceporrón"
                }
            ],
            publications: [
                {
                    id: "2",
                    date: Date.now(),
                    user: "anon@not.need",
                    user_id: "2",
                    content: "Foo bar"
                },
                {
                    id: "1",
                    date: Date.now(),
                    user: "foo@bar.com",
                    user_id: "2",
                    content: "LOL"
                }
            ]
        };*/
    }]);
