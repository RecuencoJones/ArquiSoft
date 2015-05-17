angular.module('starter')

    .controller('ProfileEditCtrl',['$scope', '$state', '$stateParams', '$location', '$http', 'auth', 'API', function($scope,$state,$stateParams,$location,$http,auth,API){
        $scope.loggedUserId = auth.identity().userid;
        $scope.hidden = true;

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $scope.name = "";
        $scope.phone = "";
        $scope.avatar = "";
        $scope.bio = "";
        $scope.password = "";
        $scope.repassword = "";
        $scope.skills = [];

        $scope.errors = {};
        $scope.hiddenErrorMessage = true;
        $scope.newskill = "";
        $scope.showSkillInput = false;

        $http.get(API.URL + API.PROFILE_ENDPOINT + $scope.loggedUserId)
            .success(function(data){
                console.log(data);
                if(data.type){
                    $state.go("error");
                }else{
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
            $scope.errors = {};
            $scope.hiddenErrorMessage = true;

            if(!($scope.phone.trim().length==0) && !$scope.phone.match(/[0-9]{9}/)){
                $scope.errors.phone = true;
                $scope.hiddenErrorMessage = false;
            }
            if(!($scope.avatar.trim().length==0) && !$scope.avatar.match(/\.(jpeg|jpg|gif|png)$/)){
                $scope.errors.avatar = true;
                $scope.hiddenErrorMessage = false;
            }
            if($scope.password != $scope.repassword){
                $scope.errors.password = true;
                $scope.hiddenErrorMessage = false;
            }
            if(!$scope.hiddenErrorMessage){
                return;
            }
            
            var tmp = {
                id: $scope.loggedUserId,
                nombre: $scope.name.trim().length==0 ? null : $scope.name,
                telefono: $scope.phone,
                avatar: $scope.avatar,
                descripcion: $scope.bio,
                password: $scope.password,
                repassword: $scope.repassword,
                type: false
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
                        $state.go('profile', {_userid: $scope.loggedUserId});
                    }
                }).error(function () {
                    $scope.hiddenErrorMessage = false;
                });
            
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