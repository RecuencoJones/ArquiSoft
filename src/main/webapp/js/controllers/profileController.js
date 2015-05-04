angular.module('starter')

    .controller('ProfileCtrl', [ '$scope', '$state', '$stateParams', '$location', '$http', 'auth', 'API', function($scope,$state,$stateParams,$location,$http,auth,API){
        $scope.loggedUserId = auth.identity().userid;
        $scope.user_id = $stateParams._userid;
        $scope.message = "";
        $scope.newtag = "";
        $scope.hidden = true;
        $scope.showTagInput = false;
        $scope.following = false;
        
        $scope.user = {};
        
        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
        
        $scope.isOwner = function(){
            return $scope.loggedUserId == $stateParams._userid;
        };
        
        $http.get(API.URL + API.PROFILE_ENDPOINT + $stateParams._userid)
            .success(function(data){
                console.log(data);
                if(data.type){
                    $state.go("error");
                }else{
                    $scope.user = data;
                }
            }).error(function(data){
                console.log("error");
            });

        $http.get(API.URL+API.ISFOLLOW_ENDPOINT+$scope.loggedUserId+"/"+$stateParams._userid)
            .success(function(data){
                $scope.following = data.res;
            });
        
        $scope.sendMessage = function() {
            if ($scope.message.trim() != "") {
                var newPub = {
                    date: Date.now(),
                    id: $scope.loggedUserId,
                    content: $scope.message
                };
                $http.post(API.URL + API.POST_ENDPOINT,
                    JSON.stringify(newPub),
                    {
                        'Content-Type': 'application/json'
                    })
                    
                    .success(function(data){
                        var temp = {
                            date: data.date,
                            id: data.id,
                            user_id: $scope.loggedUserId,
                            content: data.content
                        };
                        $scope.user.publications.push(temp);
                    }).error(function(data){
                        console.log(data);
                    });
                $scope.message = "";
            }
        };

        $scope.submitTag = function() {
            if ($scope.newtag.trim() != "") {
                var tag = {
                    nombre: $scope.newtag,
                    publicante: $stateParams._userid
                };
                $http.post(API.URL + API.TAG_ENDPOINT,
                    JSON.stringify(tag),
                    {
                        'Content-Type': 'application/json'
                    })

                    .success(function(data){
                        if(data.ok){
                            $scope.user.tags.push($scope.newtag);
                        }else{
                            alert("JAJAJA");
                        }
                        $scope.newtag = "";
                        $scope.showTagInput = false;
                    }).error(function(data){
                        console.log(data);
                        $scope.newtag = "";
                        $scope.showTagInput = false;
                    });
            }
        };
        
        $scope.follow = function(){
            $http.get(API.URL+API.FOLLOW_ENDPOINT+$scope.loggedUserId+"/"+$stateParams._userid)
                .success(function(data){
                    $scope.following = true;
                });
        };

        $scope.unfollow = function(){
            $http.get(API.URL+API.UNFOLLOW_ENDPOINT+$scope.loggedUserId+"/"+$stateParams._userid)
                .success(function(data){
                    $scope.following = false;
                });
        };
        
        /*$scope.user = {
            name: "David",
            description: "Cute retarded unicorn",
            avatar: "img/placeholder.jpg",
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
