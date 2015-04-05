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

        /*$http.get(API.URL + API.PROFILE_ENDPOINT + $stateParams._userid)
            .success(function(data){
                console.log(data);
                $scope.user = data;
            }).error(function(data){
                console.log("error");
            });*/

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
                //$http.post()
                $scope.user.tags.push($scope.newtag);
                $scope.newtag = "";
                $scope.showTagInput = false;
            }
        }

        $scope.band = {
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
         ]};
    }]);

