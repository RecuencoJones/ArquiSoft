angular.module('starter')

    .controller('ProfileCtrl', [ '$scope', '$state', '$location', 'auth', function($scope,$state,$location,auth){
        $scope.user = auth.identity().user;
        $scope.message = "";
        $scope.hidden = true;

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
        
        $scope.sendMessage = function() {
            if ($scope.message.trim() != "") {
                var newPub = {
                    id: "999",
                    date: Date.now(),
                    user: $scope.user,
                    user_id: "3",
                    content: $scope.message
                };
                $scope.publications.push(newPub);
                $scope.message = "";
            }
        }
        
        $scope.publications = [
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
    }]);
