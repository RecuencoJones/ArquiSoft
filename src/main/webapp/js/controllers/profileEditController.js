angular.module('starter')

    .controller('ProfileEditCtrl',['$scope', '$state', '$stateParams', '$location', '$http', 'auth', 'API', function($scope,$state,$stateParams,$location,$http,auth,API){
        $scope.loggedUserId = auth.identity().userid;
        $scope.user_id = $stateParams._userid;
        $scope.hidden = true;

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $scope.name = "";
        $scope.email = "";
        $scope.phone = "";
        $scope.avatar = "";
        $scope.bio = "";
        $scope.password = "";
        $scope.repassword = "";
        $scope.skills = [];

        $scope.errors = [];
        $scope.hiddenErrorMessage = true;
        $scope.newskill = "";
        $scope.showSkillInput = false;

        $http.get(API.URL + API.PROFILE_ENDPOINT + $scope.user_id)
            .success(function(data){
                console.log(data);
                if(data.type){
                    $state.go("error");
                }else{
                    $scope.name = data.name;
                    $scope.email = data.email;
                    $scope.phone = data.phone;
                    $scope.avatar = data.avatar;
                    $scope.bio = data.description;
                    console.log(data.skills);
                    $scope.skills = data.skills;
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
            
            if($scope.password != $scope.repassword){
                $scope.errors.password = true;
                $scope.hiddenErrorMessage = false;
                return;
            }
            
            var tmp = {
                name: $scope.name,
                email: $scope.email,
                avatar: $scope.avatar,
                description: $scope.bio,
                password: $scope.password,
                repassword: $scope.repassword
            }
            
        };
        
        $scope.submitSkill = function(){

            if ($scope.newskill.trim() != "") {
                var skill = {
                    nombre: $scope.newskill,
                    publicante: $scope.user_id
                };
                $http.post(API.URL + API.SKILL_ENDPOINT,
                    JSON.stringify(skill),
                    {
                        'Content-Type': 'application/json'
                    })

                    .success(function(data){
                        if(data.ok){
                            $scope.skills.push($scope.newskill);
                        }else{
                            alert("JAJAJA");
                        }
                        $scope.newskill = "";
                        $scope.showSkillInput = false;
                    }).error(function(data){
                        console.log(data);
                        $scope.newskill = "";
                        $scope.showSkillInput = false;
                    });
            }
        };
    }]);