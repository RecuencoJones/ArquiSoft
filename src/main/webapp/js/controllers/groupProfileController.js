angular.module('starter')

    .controller('GroupProfileCtrl', [ '$scope', '$state', '$stateParams', '$location', '$http', 'auth', 'API', function($scope,$state,$stateParams,$location,$http,auth,API){
        $scope.loggedUserId = auth.identity().userid;
        $scope.message = "";
        $scope.newtag = "";
        $scope.hidden = true;
        $scope.showTagInput = false;

        $scope.band = {};

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $http.get(API.URL + API.PROFILE_ENDPOINT + $stateParams._groupid)
            .success(function(data){
                console.log(data);
                $scope.band = data;
            }).error(function(data){
                console.log("error");
            });

        $scope.sendMessage = function() {
            if ($scope.message.trim() != "") {
                var newPub = {
                    id: "999",
                    date: Date.now(),
                    user: $scope.band.name,
                    user_id: $scope.loggedUserId,
                    content: $scope.message
                };
                //$http.post()
                $scope.band.publications.push(newPub);
                $scope.message = "";
            }
        };

        $scope.submitTag = function() {
            if ($scope.newtag.trim() != "") {
                var tag = {
                    nombre: $scope.newtag,
                    publicante: $stateParams._groupid
                };
                $http.post(API.URL + API.TAG_ENDPOINT,
                    JSON.stringify(tag),
                    {
                        'Content-Type': 'application/json'
                    })
                    
                    .success(function(data){
                        if(data.ok){
                            $scope.band.tags.push($scope.newtag);
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

        /*$scope.band = {
         name: "Metallica",
         description: "Cute metal band",
         avatar: "img/metallica.jpg",
         members: [
             {
                 id: 1,
                 name: "Lars"
             },
             {
                 id: 2,
                 name: "Kirk"
             },
             {
                 id: 3,
                 name: "James"
             },
             {
                 id: 666,
                 name: "Cliff"
             },
         ],
         publications: [
             {
             id: "4",
             date: Date.now(),
             user: "Metallica",
             user_id: "2",
             content: "MASTEEEEER MWAHAHAHAHA"
             },
             {
             id: "3",
             date: Date.now(),
             user: "Metallica",
             user_id: "2",
             content: "Sad but true"
             }
         ],
         tags: [
             "Los Angeles",
             "California",
             "Thrash metal",
             "Speed metal",
             "BEST"
         ]};*/
    }]);

