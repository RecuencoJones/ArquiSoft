angular.module('starter')

    .controller('GroupProfileCtrl', [ '$scope', '$state', '$stateParams', '$location', '$http', 'auth', 'API', function($scope,$state,$stateParams,$location,$http,auth,API){
        $scope.loggedUserId = auth.identity().userid;
        $scope.loggedUserBands = auth.identity().groupsIds;
        $scope.bandId = $stateParams._groupid;
        $scope.message = "";
        $scope.newtag = "";
        $scope.hidden = true;
        $scope.showTagInput = false;
        $scope.following = false;
        $scope.userIsMember = false;
        $scope.showSuccessApplianceMessage = false;

        $scope.band = {};
        $scope.applicants = "";

        $scope.toggleMenu = function() {
            $scope.hidden = !$scope.hidden;
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
        
        $http.get(API.URL + API.PROFILE_ENDPOINT + $scope.bandId)
            .success(function(data){
                console.log(data);
                if(data.type){
                    $scope.band = data;       
                }else{
                    $state.go("error");
                }
            }).error(function(data){
                console.log("error");
            });
        
        $http.get(API.URL + API.USER_GROUPS_ENDPOINT + $scope.loggedUserId)
            .success(function(data){
                $scope.loggedUserBands = data;
                auth.setGroups(data);
                $scope.userIsMember = $scope.isMember();
            });

        $scope.isMember = function(){
            return ($scope.loggedUserBands.indexOf(Number($stateParams._groupid))>-1);
        };
        $scope.userIsMember = $scope.isMember();
        
        if($scope.isMember()){
            $http.get(API.URL + API.BAND_APPLICANTS_ENDPOINT + $scope.bandId)
                .success(function(data){
                    console.log(data);
                    $scope.applicants = data;
                });
        }

        $http.get(API.URL+API.ISFOLLOW_ENDPOINT+$scope.loggedUserId+"/"+$scope.bandId)
            .success(function(data){
                $scope.following = data.res;
            });

        $scope.sendMessage = function() {
            if ($scope.message.trim() != "") {
                var newPub = {
                    date: Date.now(),
                    id: $scope.bandId,
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
                            user_id: $scope.bandId,
                            content: data.content
                        };
                        $scope.band.publications.push(temp);
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
                    publicante: $scope.bandId
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
                            alert("Error");
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
            $http.put(API.URL+API.FOLLOW_ENDPOINT+$scope.loggedUserId+"/"+$scope.bandId)
                .success(function(data){
                    $scope.following = true;
                });
        };

        $scope.unfollow = function(){
            $http.delete(API.URL+API.UNFOLLOW_ENDPOINT+$scope.loggedUserId+"/"+$scope.bandId)
                .success(function(data){
                    $scope.following = false;
                });
        };
        
        $scope.apply = function(){
            $http.put(API.URL+API.BAND_APPLY_ENDPOINT+$scope.bandId+"/"+$scope.loggedUserId)
                .success(function(data){
                    console.log(data);
                    $scope.showSuccessApplianceMessage = true;
                });
        };
        
        $scope.leave = function(){
            if(confirm("Are you sure?")) {
                $http.delete(API.URL + API.BAND_LEAVE_ENDPOINT + $scope.bandId + "/" + $scope.loggedUserId)
                    .success(function (data) {
                        $state.go('profile', {_userid: $scope.loggedUserId});
                    });
            }
        };
        
        $scope.accept = function(id,name,index){
            $http.put(API.URL+API.ACCEPT_APPLICANT_ENDPOINT+$scope.bandId+"/"+id)
                .success(function(data){
                    console.log(data);
                    if(data.res){
                        $scope.band.members.push({id: id, name: name});
                        $scope.applicants.splice(index,1);
                    }
                });
        };

        $scope.reject = function(id,index){
            $http.delete(API.URL+API.REJECT_APPLICANT_ENDPOINT+$scope.bandId+"/"+id)
                .success(function(data){
                    console.log(data);
                    if(data.res) {
                        $scope.applicants.splice(index,1);
                    }
                });
        };
        
        $scope.hideSuccessMessage = function(){
            $scope.showSuccessApplianceMessage = false;
        };
    }]);

